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
import com.gs.dmn.feel.synthesis.type.DoubleMixedJavaTimeNativeTypeFactory;
import com.gs.dmn.signavio.feel.lib.DoubleMixedJavaTimeSignavioLib;
import com.gs.dmn.signavio.runtime.DoubleMixedJavaTimeSignavioBaseDecision;
import com.gs.dmn.signavio.runtime.interpreter.SignavioDMNInterpreter;
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.transformation.SignavioDMNToJavaTransformer;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToJavaTransformer;

import javax.xml.datatype.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

public class DoubleMixedJavaTimeSignavioDMNDialectDefinitionTest extends AbstractSignavioDMNDialectDefinitionTest<Double, LocalDate, OffsetTime, ZonedDateTime, Duration> {
    @Override
    protected DMNDialectDefinition<Double, LocalDate, OffsetTime, ZonedDateTime, Duration, TestLab> makeDialect() {
        return new DoubleMixedJavaTimeSignavioDMNDialectDefinition();
    }

    @Override
    protected String getExpectedDMNInterpreterClass() {
        return SignavioDMNInterpreter.class.getName();
    }

    @Override
    protected String getExpectedDMNToNativeTransformerClass() {
        return SignavioDMNToJavaTransformer.class.getName();
    }

    @Override
    protected String getBasicTransformerClass() {
        return BasicSignavioDMNToJavaTransformer.class.getName();
    }

    @Override
    protected String getExpectedNativeTypeFactoryClass() {
        return DoubleMixedJavaTimeNativeTypeFactory.class.getName();
    }

    @Override
    protected String getExpectedFEELLibClass() {
        return DoubleMixedJavaTimeSignavioLib.class.getName();
    }

    @Override
    protected String getExpectedDecisionBaseClass() {
        return DoubleMixedJavaTimeSignavioBaseDecision.class.getName();
    }
}