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
package com.gs.dmn.feel.analysis.syntax.ast.expression.function;

import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import java.util.function.BiFunction;

public abstract class Parameters<T> extends Element<T> {
    // API for initial status
    public abstract boolean isEmpty();
    public abstract ParameterTypes<T> getSignature();
    public abstract void setOriginalArguments(Arguments<T> originalArguments);

    // API during and after conversion
    public abstract void setParameterConversions(ParameterConversions<T> parameterConversions);
    public abstract void setConvertedParameterTypes(ParameterTypes<T> parameterTypes);
    public abstract Arguments<T> convertArguments(BiFunction<Object, Conversion<T>, Object> convertArgument);

    public abstract T getParameterType(int position, String name);
    public abstract Expression<T> getParameter(int position, String name);
}
