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
package com.gs.dmn.signavio.feel;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.signavio.dialect.JavaTimeSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.testlab.TestLab;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;

public class SignavioFEELProcessorTest extends AbstractSignavioFEELProcessorTest<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount> {
    @Override
    protected String numberType() {
        return Number.class.getName();
    }

    @Override
    protected DMNDialectDefinition<Number, LocalDate, TemporalAccessor, TemporalAccessor, TemporalAmount, TestLab> makeDialect() {
        return new JavaTimeSignavioDMNDialectDefinition();
    }
}