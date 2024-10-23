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
package com.gs.dmn.generated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gs.dmn.runtime.ExecutionContext;
import com.gs.dmn.runtime.ExecutionContextBuilder;
import com.gs.dmn.runtime.annotation.AnnotationSet;
import com.gs.dmn.serialization.JsonSerializer;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class AbstractHandwrittenDecisionTest {
    protected AnnotationSet annotationSet;
    protected ExecutionContext context;

    protected String toJson(Object object) {
        try {
            return JsonSerializer.OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    protected abstract void applyDecision();

    @BeforeEach
    public void setUp() {
        this.context = ExecutionContextBuilder.executionContext().build();
        this.annotationSet = context.getAnnotations();
    }

    protected String round(Number number, int scale) {
        return new BigDecimal(number.toString()).setScale(scale, RoundingMode.FLOOR).toPlainString();
    }
}
