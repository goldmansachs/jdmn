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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.runtime.listener.DRGElement;

public class ExpressionEvaluationContext extends EvaluationContext {
    private final DMNContext context;
    private final DRGElement elementAnnotation;
    public ExpressionEvaluationContext(TDRGElement element, DMNContext context, DRGElement elementAnnotation) {
        super(element);
        this.context = context;
        this.elementAnnotation = elementAnnotation;
    }

    public DMNContext getContext() {
        return context;
    }

    public DRGElement getElementAnnotation() {
        return elementAnnotation;
    }
}