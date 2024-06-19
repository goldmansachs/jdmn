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

import com.gs.dmn.feel.lib.JavaTimeFEELLib;
import com.gs.dmn.feel.synthesis.type.JavaTimeNativeTypeFactory;
import com.gs.dmn.runtime.JavaTimeDMNBaseDecision;
import com.gs.dmn.runtime.interpreter.StandardDMNInterpreter;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class JavaTimeDMNDialectDefinitionTest extends AbstractStandardDMNDialectDefinitionTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestCases> makeDialect() {
        return new JavaTimeDMNDialectDefinition();
    }

    @Override
    protected String getExpectedDMNInterpreterClass() {
        return StandardDMNInterpreter.class.getName();
    }

    @Override
    protected String getExpectedDMNToNativeTransformerClass() {
        return DMNToJavaTransformer.class.getName();
    }

    @Override
    protected String getBasicTransformerClass() {
        return BasicDMNToJavaTransformer.class.getName();
    }

    @Override
    protected String getExpectedNativeTypeFactoryClass() {
        return JavaTimeNativeTypeFactory.class.getName();
    }

    @Override
    protected String getExpectedFEELLibClass() {
        return JavaTimeFEELLib.class.getName();
    }

    @Override
    protected String getExpectedDecisionBaseClass() {
        return JavaTimeDMNBaseDecision.class.getName();
    }
}