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
package com.gs.dmn.dialect;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.synthesis.type.StandardNativeTypeToKotlinFactory;
import com.gs.dmn.runtime.DefaultDMNBaseDecision;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.transformation.DMNToKotlinTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToKotlinTransformer;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class KotlinStandardDMNDialectDefinitionTest extends AbstractStandardDMNDialectDefinitionTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected DMNDialectDefinition<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestCases> makeDialect() {
        return new KotlinStandardDMNDialectDefinition();
    }

    @Override
    protected String getExpectedDMNInterpreterClass() {
        return StandardDMNInterpreter.class.getName();
    }

    @Override
    protected String getExpectedDMNToNativeTransformerClass() {
        return DMNToKotlinTransformer.class.getName();
    }

    @Override
    protected String getBasicTransformerClass() {
        return BasicDMNToKotlinTransformer.class.getName();
    }

    @Override
    protected String getExpectedNativeTypeFactoryClass() {
        return StandardNativeTypeToKotlinFactory.class.getName();
    }

    @Override
    protected String getExpectedFEELLibClass() {
        return DefaultFEELLib.class.getName();
    }

    @Override
    protected String getExpectedDecisionBaseClass() {
        return DefaultDMNBaseDecision.class.getName();
    }
}