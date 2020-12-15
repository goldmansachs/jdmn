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

public class ExportedLiteralDMNInterpreterTest extends AbstractSignavioDMNInterpreterTest {
    private final DefaultSignavioBaseDecision decision = new DefaultSignavioBaseDecision();

    @Override
    protected String getInputPath() {
        return "dmn/literal";
    }

    @Test
    public void testBooleanLiteralExpression() throws Exception {
        doTest(new DecisionTestConfig(
                "decision",
                "simple-decision-feel-boolean-literal-expression",
                makeRuntimeEnvironment(Arrays.asList(
                        new Pair<>("numberInput", decision.number("-1")),
                        new Pair<>("stringInput", "123"),
                        new Pair<>("booleanInput", true),
                        new Pair<>("dateInput", decision.date("2017-01-01")),
                        new Pair<>("enumerationInput", "e1")
                )),
                Boolean.TRUE)
        );
    }

    @Test
    public void testComplexLiteralExpression() throws Exception {
        doTest(new DecisionTestConfig(
                "monthly",
                "simple-decision-feel-complex-literal-expression",
                makeRuntimeEnvironment(Arrays.asList(
                        new Pair<>("loan", new Context().add("principal", decision.number("1024")).add("rate", decision.number("5")).add("term", decision.number("25")))
                )),
                decision.number("1024"))
        );
    }

    @Test
    public void testDateLiteralExpression() throws Exception {
        doTest(new DecisionTestConfig(
                "decision",
                "simple-decision-feel-date-literal-expression",
                makeRuntimeEnvironment(Arrays.asList(
                        new Pair<>("numberInput", decision.number("-1")),
                        new Pair<>("stringInput", "123"),
                        new Pair<>("booleanInput", true),
                        new Pair<>("dateInput", decision.date("2017-01-01")),
                        new Pair<>("enumerationInput", "e1")
                )),
                decision.number("1"))
        );
    }

    @Test
    public void testEnumerationLiteralExpression() throws Exception {
        doTest(new DecisionTestConfig(
                "decision",
                "simple-decision-feel-enumeration-literal-expression",
                makeRuntimeEnvironment(Arrays.asList(
                        new Pair<>("numberInput", decision.number("-1")),
                        new Pair<>("stringInput", "123"),
                        new Pair<>("booleanInput", true),
                        new Pair<>("dateInput", decision.date("2017-01-01")),
                        new Pair<>("enumerationInput", "e1")
                )),
                "e1")
        );
    }

    @Test
    public void testNumericLiteralExpression() throws Exception {
        doTest(new DecisionTestConfig("decision",
                "simple-decision-feel-numeric-literal-expression",
                makeRuntimeEnvironment(Arrays.asList(
                        new Pair<>("numberInput", decision.number("-1")),
                        new Pair<>("stringInput", "123"),
                        new Pair<>("booleanInput", true),
                        new Pair<>("dateInput", decision.date("2017-01-01")),
                        new Pair<>("enumerationInput", "e1")
                )),
                decision.number("5"))
        );
    }

    @Test
    public void testStringLiteralExpression() throws Exception {
        doTest(new DecisionTestConfig("decision",
                "simple-decision-feel-string-literal-expression",
                makeRuntimeEnvironment(Arrays.asList(
                        new Pair<>("numberInput", decision.number("-1")),
                        new Pair<>("stringInput", "123"),
                        new Pair<>("booleanInput", true),
                        new Pair<>("dateInput", decision.date("2017-01-01")),
                        new Pair<>("enumerationInput", "e1")
                )),
                "123abc")
        );
    }

    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> getLib() {
        return new DefaultSignavioBaseDecision();
    }
}
