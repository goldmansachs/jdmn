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
package com.gs.dmn.signavio.runtime.interpreter;

import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Context;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision;
import org.junit.Test;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.Arrays;

public class ExportedDecisionTableDMNInterpreterTest extends AbstractSignavioDMNInterpreterTest {
    private final DefaultSignavioBaseDecision decision = new DefaultSignavioBaseDecision();

    @Override
    protected String getInputPath() {
        return "dmn/decision-table";
    }

    @Test
    public void testCompoundDecisionPrimitiveTypeInputsSfeelInputEntriesCompoundOutputFirstHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "compoundOutputCompoundDecision",
                        "compound-decision-primitive-type-inputs-sfeel-input-entries-compound-output-first-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("booleanInput", true),
                                new Pair<>("enumerationInput", "e1"),
                                new Pair<>("dd1TextInput", "a"),
                                new Pair<>("dd2NumberInput", decision.number("1"))
                        )),
                        new Context().add("firstOutput", "r11").add("secondOutput", "r12")
                )
        );
    }

    @Test
    public void testSimpleDecisionComplexTypeInputsSfeelInputEntriesSingleOutputCollectHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-complex-type-inputs-sfeel-input-entries-single-output-collect-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("person", new Context("Person")
                                        .add("firstName", "Peter")
                                        .add("lastName", "Sellers")
                                        .add("gender", "male")
                                        .add("id", decision.number("4"))
                                        .add("dateOfBirth", decision.date("2016-10-01"))
                                        .add("timeOfBirth", decision.time("01:00:00Z"))
                                        .add("dateTimeOfBirth", decision.dateAndTime("2016-10-01T00:00:00Z"))
                                        .add("list", Arrays.asList("abc"))
                                        .add("married", false)),
                                new Pair<>("employed", true)
                        )),
                        Arrays.asList("3")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsCompoundOutputOutputOrderHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-compound-output-output-order-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        Arrays.asList(
                                new Context().add("output1", "r5").add("output2", "r6"),
                                new Context().add("output1", "r4").add("output2", "r7"),
                                new Context().add("output1", "r3").add("output2", "r8"),
                                new Context().add("output1", "r2").add("output2", "r9")
                        )
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsCompoundOutputPriorityHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-compound-output-priority-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        new Context().add("output1", "r5").add("output2", "r6")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsFeelInputEntriesSingleOutputFirstHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-feel-input-entries-single-output-first-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("booleanInput", true),
                                new Pair<>("numberInput", decision.number("-1")),
                                new Pair<>("textInput", "abc"),
                                new Pair<>("enumerationInput", "e1"),
                                new Pair<>("dateInput", decision.date("2016-08-01")),
                                new Pair<>("timeInput", decision.time("12:00:00Z")),
                                new Pair<>("dateAndTimeInput", decision.dateAndTime("2016-08-01T11:00:00Z"))
                        )),
                        "r1"
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsFeelInputEntriesSingleOutputUniqueHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-feel-input-entries-single-output-unique-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("booleanInput", true),
                                new Pair<>("numberInput", decision.number("-1")),
                                new Pair<>("textInput", "abc"),
                                new Pair<>("enumerationInput", "e1"),
                                new Pair<>("dateInput", decision.date("2016-08-01")),
                                new Pair<>("timeInput", decision.time("12:00:00Z")),
                                new Pair<>("dateAndTimeInput", decision.dateAndTime("2016-08-01T11:00:00Z"))
                        )),
                        null
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSfeelInputEntriesCompoundOutputFirstHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "compoundOutputDecision",
                        "simple-decision-primitive-type-inputs-sfeel-input-entries-compound-output-first-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("booleanInput", true),
                                new Pair<>("numberInput", decision.number("-1")),
                                new Pair<>("textInput", "abc"),
                                new Pair<>("enumerationInput", "e1"),
                                new Pair<>("dateInput", decision.date("2016-08-01")),
                                new Pair<>("timeInput", decision.time("12:00:00Z")),
                                new Pair<>("dateAndTimeInput", decision.dateAndTime("2016-08-01T11:00:00Z"))
                        )),
                        new Context().add("firstOutput", "r11").add("secondOutput", "r12")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSfeelInputEntriesSingleOutputFirstHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-sfeel-input-entries-single-output-first-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("booleanInput", true),
                                new Pair<>("numberInput", decision.number("-1")),
                                new Pair<>("textInput", "abc"),
                                new Pair<>("enumerationInput", "e1"),
                                new Pair<>("dateInput", decision.date("2016-08-01")),
                                new Pair<>("timeInput", decision.time("12:00:00Z")),
                                new Pair<>("dateAndTimeInput", decision.dateAndTime("2016-08-01T11:00:00Z"))
                        )),
                        "r1"
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputAnyHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-any-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        null
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputCollectCountHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-collect-count-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        decision.number("4")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputCollectHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-collect-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        Arrays.asList("r5", "r4", "r3", "r2")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputCollectMinHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-collect-min-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        decision.number("1")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputCollectSumHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-collect-sum-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        decision.number("10")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputFirstHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-first-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        "r5"
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputOutputOrderHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-output-order-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        Arrays.asList("r5", "r4", "r3", "r2")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputPriorityHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-priority-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        "r5"
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputRuleOrderHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-rule-order-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        Arrays.asList("r5", "r4", "r3", "r2")
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeInputsSingleOutputUniqueHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-inputs-single-output-unique-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                        )),
                        "r2"
                )
        );
    }

    @Test
    public void testSimpleDecisionPrimitiveTypeListInputsFeelInputEntriesSingleOutputUniqueHitPolicy() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "simple-decision-primitive-type-list-inputs-feel-input-entries-single-output-unique-hit-policy",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("textInput", "1"),
                                new Pair<>("numberInput", decision.number("1"))
                        )),
                        null
                )
        );
    }

    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> getLib() {
        return new DefaultSignavioBaseDecision();
    }
}

