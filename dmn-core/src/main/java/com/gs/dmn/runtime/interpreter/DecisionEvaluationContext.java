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

import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.TDRGElement;

import java.util.Map;

public class DecisionEvaluationContext extends EvaluationContext {
    private final Map<QualifiedName, Object> informationRequirements;

    public DecisionEvaluationContext(TDRGElement element, Map<QualifiedName, Object> informationRequirements) {
        super(element);
        this.informationRequirements = informationRequirements;
    }

    public Map<QualifiedName, Object> getInformationRequirements() {
        return informationRequirements;
    }
}
