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

public class ExportedComplexDMNInterpreterTest extends AbstractSignavioDMNInterpreterTest {
    private final DefaultSignavioBaseDecision decision = new DefaultSignavioBaseDecision();

    @Override
    protected String getInputPath() {
        return "dmn/complex";
    }

    @Test
    public void testChildLinked() throws Exception {
        doTest(new DecisionTestConfig(
                        "abc",
                        "ChildLinked",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("num", decision.number("3"))
                        )),
                        decision.number("6")
                )
        );
    }

    @Test
    public void testCompareLists() throws Exception {
        doTest(new DecisionTestConfig(
                        "processL1",
                        "CompareLists",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("l1", Arrays.asList(decision.number("1"), decision.number("2"), decision.number("3"), decision.number("4"))),
                                new Pair<>("l23", Arrays.asList(decision.number("1"), decision.number("1"), decision.number("2"), decision.number("3"), decision.number("5"), decision.number("8")))
                        )),
                        Arrays.asList(decision.number("2"), decision.number("1"), decision.number("1"), decision.number("0"))
                )
        );
    }

    @Test
    public void testDecisionWithAnnotations() throws Exception {
        doTest(new DecisionTestConfig(
                        "decision",
                        "decision-with-annotations",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("numberInput", decision.number("1")),
                                new Pair<>("stringInput", "abc"),
                                new Pair<>("booleanInput", true),
                                new Pair<>("dateInput", decision.date("2017-01-01")),
                                new Pair<>("enumerationInput", "e1"),
                                new Pair<>("person", new Context().add("name", "Peter").add("age", decision.number("10")))
                        )),
                        null
                )
        );
    }

    @Test
    public void testDotProduct() throws Exception {
        doTest(new DecisionTestConfig(
                        "dotProduct",
                        "DotProduct",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("a", decision.asList(decision.number("2"), decision.number("3"))),
                                new Pair<>("b", decision.asList(decision.number("4"), decision.number("2")))
                        )),
                        new Context().add("dotProduct2", decision.number("14")).add("outputMessage", "2D vector dot product == 14")
                )
        );
    }

    @Test
    public void testExampleCreditDecision() throws Exception {
        doTest(new DecisionTestConfig(
                        "generateOutputData",
                        "Example credit decision",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("applicant", new Context()
                                        .add("name", "Amy")
                                        .add("age", decision.number("38"))
                                        .add("creditScore", decision.number("100")).add("priorIssues", Arrays.asList("Late payment"))),
                                new Pair<>("currentRiskAppetite", decision.number("50")),
                                new Pair<>("lendingThreshold", decision.number("25"))
                        )),
                        Arrays.asList(
                                new Context()
                                        .add("Decision", "Accept")
                                        .add("Assessment", decision.number("27.50"))
                                        .add("Issue", decision.numericUnaryMinus(decision.number("7.50"))))
                )
        );
    }

    @Test
    public void testNPEValidation2() throws Exception {
        doTest(new DecisionTestConfig(
                        "zip",
                        "NPEValidation2",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("ages", decision.asList(decision.number("25"), decision.number("40"), decision.number("65"), decision.number("80"), decision.number("105"))),
                                new Pair<>("day", decision.number("25")),
                                new Pair<>("hour", decision.number("8")),
                                new Pair<>("minute", decision.number("5")),
                                new Pair<>("month", decision.number("12")),
                                new Pair<>("names", decision.asList("Fred", "Jim", "Tom", "Sarah", "Kate")),
                                new Pair<>("second", decision.number("10")),
                                new Pair<>("year", decision.number("2016"))
                        )),
                        Arrays.asList(
                                new Context()
                                        .add("names", "Fred").add("ages", decision.number("25")).add("dateDiffs", decision.number("0"))
                                        .add("dateTimeDiffs", decision.number("0")).add("temporalUnits", decision.number("12")).add("agesListDescription", "not exactly 1 to 5"),
                                new Context()
                                        .add("names", "Jim").add("ages", decision.number("40")).add("dateDiffs", decision.number("0"))
                                        .add("dateTimeDiffs", decision.number("0")).add("temporalUnits", decision.number("2016")).add("agesListDescription", "non of the numbers 1 to 5"),
                                new Context()
                                        .add("names", "Tom").add("ages", decision.number("65")).add("dateDiffs", decision.number("0"))
                                        .add("dateTimeDiffs", decision.number("0")).add("temporalUnits", decision.number("7")).add("agesListDescription", null),
                                new Context()
                                        .add("names", "Sarah").add("ages", decision.number("80")).add("dateDiffs", decision.number("-359"))
                                        .add("dateTimeDiffs", decision.number("-8612")).add("temporalUnits", decision.number("25")).add("agesListDescription", null),
                                new Context()
                                        .add("names", "Kate").add("ages", decision.number("105")).add("dateDiffs", null)
                                        .add("dateTimeDiffs", null).add("temporalUnits", decision.number("5")).add("agesListDescription", null)

                        )
                )
        );
    }

    @Test
    public void testNullSafeTests() throws Exception {
        doTest(new DecisionTestConfig(
                        "allTogether",
                        "Null Safe Tests",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("booleanA", true),
                                new Pair<>("booleanB", false),
                                new Pair<>("booleanList", decision.asList(true, false, true)),
                                new Pair<>("date", decision.date("2015-01-01")),
                                new Pair<>("dateTime", decision.dateAndTime("2015-01-01T12:00:00Z")),
                                new Pair<>("numberA", decision.number("12")),
                                new Pair<>("numberB", decision.number("34")),
                                new Pair<>("numberList", Arrays.asList(decision.number("10"), decision.number("20"), decision.number("30"), decision.number("40"))),
                                new Pair<>("string", "00.00"),
                                new Pair<>("stringList", Arrays.asList("Foo", "Bar")),
                                new Pair<>("time", decision.time("13:00:00Z"))
                        )),
                        "NotNull"
                )
        );
    }

    @Test
    public void testParentLinked() throws Exception {
        doTest(new DecisionTestConfig(
                        "somethingElse",
                        "ParentLinked",
                        makeRuntimeEnvironment(Arrays.asList(
                                new Pair<>("num", decision.number("2"))
                        )),
                        decision.number("14")
                )
        );
    }

    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> getLib() {
        return new DefaultSignavioBaseDecision();
    }
}

