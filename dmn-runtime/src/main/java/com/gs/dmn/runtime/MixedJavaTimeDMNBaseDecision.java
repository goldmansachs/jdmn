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
package com.gs.dmn.runtime;

import com.gs.dmn.feel.lib.MixedJavaTimeFEELLib;
import com.gs.dmn.runtime.annotation.AnnotationTarget;
import com.gs.dmn.runtime.annotation.DRGElement;
import com.gs.dmn.runtime.annotation.Rule;

import javax.xml.datatype.Duration;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.Map;

public class MixedJavaTimeDMNBaseDecision extends MixedJavaTimeFEELLib implements DMNDecision<BigDecimal, LocalDate, OffsetTime, ZonedDateTime, Duration>, AnnotationTarget {
    @Override
    public Object applyMap(Map<String, String> input_, ExecutionContext context_) {
        return null;
    }

    @Override
    public DRGElement getDRGElementAnnotation() {
        return this.getClass().getAnnotation(DRGElement.class);
    }

    @Override
    public Rule getRuleAnnotation(int ruleIndex) {
        String methodName = "rule%d".formatted(ruleIndex);
        Class<? extends MixedJavaTimeDMNBaseDecision> cls = this.getClass();
        Method[] declaredMethods = cls.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (methodName.equals(method.getName())) {
                return method.getAnnotation(Rule.class);
            }
        }
        return null;
    }
}
