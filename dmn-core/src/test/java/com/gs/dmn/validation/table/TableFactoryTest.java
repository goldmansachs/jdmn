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
package com.gs.dmn.validation.table;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.ast.TDecisionTable;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.validation.AbstractValidatorTest;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TableFactoryTest extends AbstractValidatorTest {
    private final DMNDialectDefinition<?, ?, ?, ?, ?, ?> dmnDialectDefinition = new StandardDMNDialectDefinition();
    private final InputParameters inputParameters = new InputParameters();
    private final TableFactory factory = new TableFactory();

    @Test
    public void testMakeTableWhenIntervals1() {
        List<String> expectedInputs = Arrays.asList(
                "number", "number"
        );
        List<String> expectedRules = Arrays.asList(
                "[[0, 1000], [0, 1000]]",
                "[[250, 750], [4000, 5000]]",
                "[[500, 1500], [500, 3000]]",
                "[[2000, 2500], [0, 2000]]"
        );
        List<String> expectedMinList = Arrays.asList(
                "[0", "[0"
        );
        List<String> expectedMaxList = Arrays.asList(
                "2500]", "5000]"
        );
        checkTable(resource("dmn/input/1.3/loan-grade-with-intervals-1.dmn"), expectedInputs, expectedRules, expectedMinList, expectedMaxList);
    }

    @Test
    public void testMakeTableWhenIntervals2() {
        List<String> expectedInputs = Arrays.asList(
                "number", "number"
        );
        List<String> expectedRules = Arrays.asList(
                "[[0, 1000], [0, 750]]",
                "[[250, 750], [1200, 1500]]",
                "[[500, 1500], [250, 1000]]",
                "[[1600, 2000], [0, 850]]"
        );
        List<String> expectedMinList = Arrays.asList(
                "[0", "[0"
        );
        List<String> expectedMaxList = Arrays.asList(
                "2000]", "1500]"
        );
        checkTable(resource("dmn/input/1.3/loan-grade-with-intervals-2.dmn"), expectedInputs, expectedRules, expectedMinList, expectedMaxList);
    }

    @Test
    public void testMakeTableWhenIntervals3() {
        List<String> expectedInputs = Arrays.asList(
                "number", "number"
        );
        List<String> expectedRules = Arrays.asList(
                "[[0, 1000], [0, 750]]",
                "[[250, 750], [1200, 1500]]",
                "[[500, 1500], [250, 1000]]",
                "[[1600, 2000], [0, 850]]",
                "[[800, 1200], [200, 300]]",
                "[[800, 1200], [800, 1300]]"
        );
        List<String> expectedMinList = Arrays.asList(
                "[0", "[0"
        );
        List<String> expectedMaxList = Arrays.asList(
                "2000]", "1500]"
        );
        checkTable(resource("dmn/input/1.3/loan-grade-with-intervals-3.dmn"), expectedInputs, expectedRules, expectedMinList, expectedMaxList);
    }

    @Test
    public void testMakeTableWhenRelationalOperators() {
        List<String> expectedInputs = Arrays.asList(
                "number", "number"
        );
        List<String> expectedRules = Arrays.asList(
                "[[-Infinity, 1000), [0, 1000]]",
                "[[-Infinity, 750], [4000, 5000]]",
                "[(500, +Infinity], [500, 3000]]",
                "[[2000, +Infinity], [0, 2000]]",
                "[[200, 200], [1000, 1000]]"
        );
        List<String> expectedMinList = Arrays.asList(
                "[-Infinity", "[0"
        );
        List<String> expectedMaxList = Arrays.asList(
                "+Infinity], 5000]"
        );
        checkTable(resource("dmn/input/1.3/loan-grade-with-relational-operators.dmn"), expectedInputs, expectedRules, expectedMinList, expectedMaxList);
    }

    @Test
    public void testMakeTableWhenAny() {
        List<String> expectedInputs = Arrays.asList(
                "number", "number"
        );
        List<String> expectedRules = Arrays.asList(
                "[[-Infinity, +Infinity], [0, 1000]]",
                "[[250, 750], [4000, 5000]]",
                "[[-Infinity, +Infinity], [500, 3000]]",
                "[[2000, 2500], [0, 2000]]"
        );
        List<String> expectedMinList = Arrays.asList(
                "[-Infinity", "[0"
        );
        List<String> expectedMaxList = Arrays.asList(
                "+Infinity]", "5000]"
        );
        checkTable(resource("dmn/input/1.3/loan-grade-with-any.dmn"), expectedInputs, expectedRules, expectedMinList, expectedMaxList);
    }

    @Test
    public void testMakeTableWhenBoolean() {
        List<String> expectedInputs = Arrays.asList(
                "boolean [false, true]", "boolean [false, true]", "boolean [false, true]"
        );
        List<String> expectedRules = Arrays.asList(
                "[[1, 2), [0, 2), [1, 2)]",
                "[[0, 2), [1, 2), [0, 1)]",
                "[[0, 2), [0, 1), [0, 2)]",
                "[[0, 1), [0, 2), [0, 2)]"
        );
        List<String> expectedMinList = Arrays.asList(
                "[0", "[0", "[0"
        );
        List<String> expectedMaxList = Arrays.asList(
                "2)", "2)", "2)"
        );
        checkTable(resource("dmn/input/1.3/loan-grade-with-boolean.dmn"), expectedInputs, expectedRules, expectedMinList, expectedMaxList);
    }

    @Test
    public void testMakeTableWhenEnumeration() {
        List<String> expectedInputs = Arrays.asList(
                "string [\"E11\", \"E12\", \"E13\"]", "string [\"E21\", \"E22\", \"E23\"]", "string [\"E31\", \"E32\", \"E33\"]"
        );
        List<String> expectedRules = Arrays.asList(
                "[[0, 1), [0, 3), [0, 1)]",
                "[[0, 3), [0, 1), [1, 2)]",
                "[[0, 3), [0, 1), [0, 3)]",
                "[[1, 2), [0, 3), [0, 3)]"
        );
        List<String> expectedMinList = Arrays.asList(
                "[0", "[0", "[0"
        );
        List<String> expectedMaxList = Arrays.asList(
                "3)", "3)", "3)"
        );
        checkTable(resource("dmn/input/1.3/loan-grade-with-enumeration.dmn"), expectedInputs, expectedRules, expectedMinList, expectedMaxList);
    }

    private void checkTable(URI fileURI, List<String> expectedInput, List<String> expectedRules, List<String> expectedMinList, List<String> expectedMaxList) {
        DMNModelRepository repository = makeRepository(fileURI);
        TDecision element = (TDecision) repository.findDRGElementByName("Loan Grade");
        TDecisionTable decisionTable = (TDecisionTable) element.getExpression();
        int totalNumberOfRules = decisionTable.getRule().size();
        int totalNumberOfColumns = decisionTable.getInput().size();
        ELTranslator feelTranslator = this.dmnDialectDefinition.createFEELTranslator(repository, this.inputParameters);
        Table table = factory.makeTable(totalNumberOfRules, totalNumberOfColumns, repository, element, decisionTable, feelTranslator);
        
        assertEquals(expectedInput.toString(), table.getInputs().toString());
        assertEquals(expectedRules.toString(), table.getRules().toString());

        List<Bound> actualMinList = new ArrayList<>();
        List<Bound> actualMaxList = new ArrayList<>();
        for (int columnIndex=0; columnIndex<totalNumberOfColumns; columnIndex++) {
            Bound lowerBoundForColumn = table.getLowerBoundForColumn(columnIndex);
            Bound upperBoundForColumn = table.getUpperBoundForColumn(columnIndex);
            actualMinList.add(lowerBoundForColumn);
            actualMaxList.add(upperBoundForColumn);
        }
        assertEquals(expectedMinList.toString(), actualMinList.toString());
        assertEquals(expectedMaxList.toString(), actualMaxList.toString());
    }
}