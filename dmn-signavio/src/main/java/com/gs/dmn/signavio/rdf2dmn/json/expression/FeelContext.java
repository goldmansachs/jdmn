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
package com.gs.dmn.signavio.rdf2dmn.json.expression;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.rdf2dmn.RDFModel;
import com.gs.dmn.signavio.rdf2dmn.json.Context;
import com.gs.dmn.signavio.rdf2dmn.json.EnumItem;
import com.gs.dmn.signavio.rdf2dmn.json.ItemDefinition;
import com.gs.dmn.signavio.rdf2dmn.json.decision.DecisionTable;
import com.gs.dmn.signavio.rdf2dmn.json.decision.InputClause;
import com.gs.dmn.signavio.rdf2dmn.json.decision.OutputClause;
import com.gs.dmn.signavio.rdf2dmn.json.relation.EnumerationProperty;
import com.gs.dmn.signavio.rdf2dmn.json.relation.Relation;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import java.util.List;
import java.util.stream.Collectors;


public class FeelContext extends Context {
    private final Element decision;
    private final DecisionTable decisionTable;
    private final int entryIndex;
    private final boolean isInput;
    private final RDFModel rdfModel;

    public FeelContext(Element decision, RDFModel rdfModel, DecisionTable decisionTable, int entryIndex, boolean isInput) {
        this.decision = decision;
        this.rdfModel = rdfModel;
        this.decisionTable = decisionTable;
        this.entryIndex = entryIndex;
        this.isInput = isInput;
    }

    public String findEnumerator(String enumeratorIndex) {
        return isInput ? findEnumeratorInInputClause(enumeratorIndex) : findEnumeratorInOutputClause(enumeratorIndex);
    }

    private String findEnumeratorInInputClause(String enumeratorIndex) {
        InputClause clause = decisionTable.getInputClauses().get(entryIndex);
        Expression expression = clause.getExpression();
        try {
            if (expression instanceof Reference) {
                String resourceId = ((Reference) expression).getShapeId();
                Element resource = rdfModel.findDescriptionById(resourceId);
                String enumItemsString = rdfModel.getEnumItems(resource);
                String relationsString = rdfModel.getRelations(resource);
                if (!StringUtils.isBlank(enumItemsString)) {
                    List<EnumItem> enumItemList = rdfModel.getEnumItemList(enumItemsString);
                    return findEnumItem(enumItemList, enumeratorIndex);
                } else if (!StringUtils.isBlank(relationsString)) {
                    java.util.List<Relation> relationList = rdfModel.getRelationList(relationsString);
                    String relationIndex = ((Reference) expression).getPathElements().get(0);
                    return findEnumItem(relationList, relationIndex, enumeratorIndex);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find enumerator at index '%s' in inputEntry '%s'", enumeratorIndex, expression.toString()));
                }
            } else {
                throw new UnsupportedOperationException(expression.getClass().getSimpleName() + " not supported");
            }
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot find enumerator at index '%s' in inputEntry '%s'", enumeratorIndex, expression.toString()), e);
        }
    }


    private String findEnumeratorInOutputClause(String enumeratorIndex) {
        OutputClause clause = decisionTable.getOutputClauses().get(entryIndex);
        ItemDefinition itemDefinition = clause.getItemDefinition();
        String itemDefinitionName = itemDefinition.getName();
        try {
            List<EnumItem> enumItemsList = itemDefinition.getEnumItems();
            return findEnumItem(enumItemsList, enumeratorIndex);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot find enumerator at index '%s' in itemDefinition '%s'", enumeratorIndex, itemDefinitionName), e);
        }
    }

    private String findEnumItem(List<EnumItem> enumItemsList, String enumeratorIndex) {
        List<EnumItem> enumItems = enumItemsList.stream().filter(ei -> enumeratorIndex.equals(ei.getId())).collect(Collectors.toList());
        if (enumItems.size() == 1) {
            return String.format("\"%s\"", enumItems.get(0).getTitle().trim());
        } else {
            throw new DMNRuntimeException(String.format("Cannot find enumerator at index '%s'", enumeratorIndex));
        }
    }

    private String findEnumItem(List<Relation> relationList, String relationIndex, String enumeratorIndex) {
        Integer index = Integer.valueOf(relationIndex);
        for(Relation relation: relationList) {
            if (relation.getRelationId() == index) {
                for(EnumItem enumItem: ((EnumerationProperty)relation.getValue()).getEnumItems()) {
                    if (enumItem.getId().equals(enumeratorIndex)) {
                        return String.format("\"%s\"", enumItem.getTitle());
                    }
                }
            }
        }
        throw new DMNRuntimeException(String.format("Cannot find enumerator at index '%s' in relation at index '%s'", enumeratorIndex, relationIndex));
    }
}
