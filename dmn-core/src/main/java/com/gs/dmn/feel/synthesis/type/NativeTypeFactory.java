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
package com.gs.dmn.feel.synthesis.type;

import com.gs.dmn.feel.analysis.semantics.type.*;

public interface NativeTypeFactory {
    String toNativeType(String feelType);

    String toQualifiedNativeType(String feelType);

    String nullableType(String nativeType);

    String constructorOfGenericType(String typeName, String... typeParameters);

    String classOf(String type);

    //
    // Types
    //
    default String getNativeNumberType() {
        return toNativeType(NumberType.NUMBER.getName());
    }
    String getNativeNumberConcreteType();

    default String getNativeDateType() {
        return toNativeType(DateType.DATE.getName());
    }

    default String getNativeTimeType() {
        return toNativeType(TimeType.TIME.getName());
    }

    default String getNativeDateAndTimeType() {
        return toNativeType(DateTimeType.DATE_AND_TIME.getName());
    }

    default String getNativeDurationType() {
        return toNativeType(DaysAndTimeDurationType.DAYS_AND_TIME_DURATION.getName());
    }
}
