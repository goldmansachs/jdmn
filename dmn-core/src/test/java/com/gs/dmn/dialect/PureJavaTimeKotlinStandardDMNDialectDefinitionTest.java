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

import com.gs.dmn.feel.lib.PureJavaTimeFEELLib;
import com.gs.dmn.feel.synthesis.type.PureJavaTimeKotlinNativeTypeFactory;
import com.gs.dmn.runtime.PureJavaTimeDMNBaseDecision;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.transformation.DMNToKotlinTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToKotlinTransformer;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;

public class PureJavaTimeKotlinStandardDMNDialectDefinitionTest extends AbstractStandardDMNDialectDefinitionTest<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount> {
    @Override
    protected DMNDialectDefinition<BigDecimal, LocalDate, Temporal, Temporal, TemporalAmount, TestCases> makeDialect() {
        return new PureJavaTimeKotlinStandardDMNDialectDefinition();
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
        return PureJavaTimeKotlinNativeTypeFactory.class.getName();
    }

    @Override
    protected String getExpectedFEELLibClass() {
        return PureJavaTimeFEELLib.class.getName();
    }

    @Override
    protected String getExpectedDecisionBaseClass() {
        return PureJavaTimeDMNBaseDecision.class.getName();
    }
}