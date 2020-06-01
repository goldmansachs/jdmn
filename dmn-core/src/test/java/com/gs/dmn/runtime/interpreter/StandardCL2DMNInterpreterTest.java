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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.dialect.StandardDMNDialectDefinition;
import org.junit.Test;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class StandardCL2DMNInterpreterTest extends AbstractDMNInterpreterTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected DMNDialectDefinition<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestCases> getDialectDefinition() {
        return new StandardDMNDialectDefinition();
    }

    @Override
    protected String getDMNInputPath() {
        return "tck/cl2/input/";
    }

    @Override
    protected String getTestCasesInputPath() {
        return getDMNInputPath();
    }

    @Test
    public void testInterpret() {
        doSingleModelTest("0004-simpletable-U");
        doSingleModelTest("0005-simpletable-A");
        doSingleModelTest("0006-simpletable-P1");
        doSingleModelTest("0007-simpletable-P2");
        doSingleModelTest("0008-LX-arithmetic");
        doSingleModelTest("0009-invocation-arithmetic");
        doSingleModelTest("0010-multi-output-U");
    }
}
