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
package com.gs.dmn.signavio.dialect;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.feel.synthesis.type.JavaTimeKotlinNativeTypeFactory;
import com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib;
import com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision;
import com.gs.dmn.signavio.runtime.interpreter.SignavioDMNInterpreter;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.SignavioDMNToKotlinTransformer;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToKotlinTransformer;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class JavaTimeKotlinSignavioDMNDialectDefinitionTest extends AbstractSignavioDMNDialectDefinitionTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestLab> makeDialect() {
        return new JavaTimeKotlinSignavioDMNDialectDefinition();
    }

    @Override
    protected String getExpectedDMNInterpreterClass() {
        return SignavioDMNInterpreter.class.getName();
    }

    @Override
    protected String getExpectedDMNToNativeTransformerClass() {
        return SignavioDMNToKotlinTransformer.class.getName();
    }

    @Override
    protected String getBasicTransformerClass() {
        return BasicSignavioDMNToKotlinTransformer.class.getName();
    }

    @Override
    protected String getExpectedNativeTypeFactoryClass() {
        return JavaTimeKotlinNativeTypeFactory.class.getName();
    }

    @Override
    protected String getExpectedFEELLibClass() {
        return JavaTimeSignavioLib.class.getName();
    }

    @Override
    protected String getExpectedDecisionBaseClass() {
        return JavaTimeSignavioBaseDecision.class.getName();
    }
}