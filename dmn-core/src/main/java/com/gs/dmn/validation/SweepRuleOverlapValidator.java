/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecisionTable;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.validation.table.Bound;
import com.gs.dmn.validation.table.RuleGroup;
import com.gs.dmn.validation.table.Table;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SweepRuleOverlapValidator extends SweepValidator {
    public SweepRuleOverlapValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public SweepRuleOverlapValidator(BuildLogger logger) {
        super(logger);
    }

    public SweepRuleOverlapValidator(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition) {
        super(dmnDialectDefinition);
    }

    @Override
    protected void validate(TDRGElement element, TDecisionTable decisionTable, SweepValidationContext context) {
        if (element != null) {
            logger.debug(String.format("Validating element '%s'", element.getName()));

            // Find the overlapping rules
            DMNModelRepository repository = context.getRepository();
            ELTranslator<Type, DMNContext> feelTranslator = context.getElTranslator();
            List<Integer> ruleIndexList = new ArrayList<>();
            int totalNumberOfRules = decisionTable.getRule().size();
            for (int i = 0; i< totalNumberOfRules; i++) {
                ruleIndexList.add(i);
            }
            int totalNumberOfColumns = decisionTable.getInput().size();
            Table table = this.factory.makeTable(totalNumberOfRules, totalNumberOfColumns, repository, element, decisionTable, feelTranslator);

            logger.debug(String.format("Table '%s'", table));

            ArrayList<RuleGroup> overlappingRules = new ArrayList<>();
            if (!table.isEmpty()) {
                findOverlappingRules(ruleIndexList, 0, totalNumberOfColumns, overlappingRules, table);
            }

            logger.debug(String.format("Overlapping rules '%s'", overlappingRules));

            // Overlap graph
            Map<Integer, Set<Integer>> neighbor = new LinkedHashMap<>();
            for (RuleGroup ruleGroup: overlappingRules) {
                List<Integer> ruleIndexes = ruleGroup.getRuleIndexes();
                int size = ruleIndexes.size();
                if (size > 1) {
                    for (int i = 0; i<size-1; i++) {
                        int n1 = ruleIndexes.get(i);
                        Set<Integer> n1Neighbor = neighbor.computeIfAbsent(n1, k -> new LinkedHashSet<>());
                        for (int j=i+1; j<size; j++) {
                            int n2 = ruleIndexes.get(j);
                            Set<Integer> n2Neighbor = neighbor.computeIfAbsent(n2, k -> new LinkedHashSet<>());
                            n1Neighbor.add(n2);
                            n2Neighbor.add(n1);
                        }
                    }
                }
            }

            logger.debug(String.format("Overlap graph '%s'", neighbor));

            // Generate the maximal cliques of the overlap graph - Bron-Kerbosch
            List<RuleGroup> maxCliques = new ArrayList<>();
            maxCliquesBronKerbosch(new RuleGroup(), new RuleGroup(ruleIndexList), new RuleGroup(), neighbor, maxCliques);

            logger.debug(String.format("Max cliques '%s'", maxCliques));

            // Make errors
            maxCliques.sort(RuleGroup.COMPARATOR);
            for (RuleGroup ruleGroup: maxCliques) {
                String error = makeError(element, ruleGroup, repository);
                context.addError(error);
            }
        }
    }

    private String makeError(TDRGElement element, RuleGroup group, DMNModelRepository repository) {
        TDefinitions model = repository.getModel(element);
        String message = String.format("Decision table rules '%s' overlap in decision '%s'", group.serialize(), repository.displayName(element));
        return makeError(repository, model, element, message);
    }

    //  From "Semantics and Analysis of DMN Decision Tables.pdf"
    //  Algorithm: findOverlappingRules
    //  Input: ruleList; i; N; overlappingRuleList.
    //      1. ruleList, containing all rules of the input DMN table;
    //      2. i, containing the index of the column under scrutiny;
    //      3. N, representing the total number of columns;
    //      4. OverlappingRuleList, storing the rules that overlap.
    //  if i == N then
    //      define current overlap currentOverlapRules; /* it contains the list of rules that overlap up to the current point */ ;
    //      if !overlappingRuleList.includes(currentOverlapRules) then
    //          overlappingRuleList.put(currentOverlapRules);
    //  else
    //      define the current list of bounds Lxi ;
    //      sortedListAllBounds = ruleList.sort(i);
    //      foreach currentBound in sortedListAllBoundaries do
    //          if !currentBound.isLower() then
    //              findOverlappingRules(Lxi, i + 1, N, overlappingRuleList); /* recursive call */
    //              Lxi.delete(currentBound);
    //          else
    //              Lxi.put(currentBound);
    //  return overlappingRuleList;
    private List<RuleGroup> findOverlappingRules(List<Integer> ruleList, int columnIndex, int inputColumnCount, List<RuleGroup> overlappingRuleList, Table table) {
        String indent = StringUtils.repeat("\t", columnIndex);
        logger.debug(String.format("%sfindOverlappingRules column = '%s' active rules '%s' overlapping rules '%s'", indent, columnIndex, ruleList, overlappingRuleList));

        if(columnIndex == inputColumnCount) {
            RuleGroup group = new RuleGroup(new ArrayList<>(ruleList));
            addGroup(overlappingRuleList, group);
        } else {
            // Define the current list of bounds lxi
            List<Integer> lxi = new ArrayList<>();
            // Project rules on column columnIndex
            List<Bound> sortedListAllBounds = makeBoundList(ruleList, columnIndex, table);
            for (Bound currentBound : sortedListAllBounds) {
                logger.debug(String.format("%sCurrent bound = '%s' active rules %s", indent, currentBound, lxi));

                int ruleIndex = currentBound.getInterval().getRuleIndex();
                if (!currentBound.isLowerBound()) {
                    List<RuleGroup> overlappingRules = findOverlappingRules(lxi, columnIndex + 1, inputColumnCount, overlappingRuleList, table);

                    logger.debug(String.format("%sRemove active rule %s", indent, ruleIndex));
                    lxi.remove((Object) ruleIndex);

                    for (RuleGroup group : overlappingRules) {
                        addGroup(overlappingRuleList, group);
                    }
                } else {
                    logger.debug(String.format("%sAdd active rule %s", indent, ruleIndex));
                    lxi.add(ruleIndex);
                }
            }
        }

        return overlappingRuleList;
    }

    private void addGroup(List<RuleGroup> rules, RuleGroup group) {
        if (group != null && group.getRuleIndexes().size() > 1) {
            if (!rules.contains(group)) {
                rules.add(group);
            }
        }
    }

    // https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm
    //    algorithm BronKerbosch2(R, P, X) is
    //      if P and X are both empty then
    //          report R as a maximal clique
    //      choose a pivot vertex u in P ⋃ X
    //      for each vertex v in P \ N(u) do
    //          BronKerbosch2(R ⋃ {v}, P ⋂ N(v), X ⋂ N(v))
    //          P := P \ {v}
    //          X := X ⋃ {v}
    protected void maxCliquesBronKerbosch(RuleGroup r, RuleGroup p, RuleGroup x, Map<Integer, Set<Integer>> neighbor, List<RuleGroup> maxCliques) {
        if (p.isEmpty() && x.isEmpty()) {
            if (r.getRuleIndexes().size() > 1) {
                maxCliques.add(r.sort());
            }
        } else {
            int u = choosePivot(p, x, neighbor);
            if (u != -1) {
                Set<Integer> uNeighbor = neighbor.get(u);
                List<Integer> ruleIndexes = p.minus(uNeighbor).getRuleIndexes();
                for (int v: ruleIndexes) {
                    Set<Integer> vNeighbor = neighbor.get(v);
                    maxCliquesBronKerbosch(r.union(v), p.intersect(vNeighbor), x.intersect(vNeighbor), neighbor, maxCliques);
                    p = p.minus(v);
                    x = x.union(v);
                }
            }
        }
    }

    private int choosePivot(RuleGroup p, RuleGroup x, Map<Integer, Set<Integer>> neighbor) {
        int max = -1;
        int u = -1;
        for (int i: p.getRuleIndexes()) {
            Set<Integer> nodes = neighbor.get(i);
            if (nodes != null) {
                int size = nodes.size();
                if (size > max) {
                    u = i;
                    max = size;
                }
            }
        }
        for (int i: x.getRuleIndexes()) {
            Set<Integer> nodes = neighbor.get(i);
            if (nodes != null) {
                int size = nodes.size();
                if (size > max) {
                    u = i;
                    max = size;
                }
            }
        }
        return u;
    }
}
