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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.feel.analysis.semantics.type.ItemDefinitionType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

public class FEELToNativeForInterpreterVisitor extends FEELToNativeVisitor {
    public FEELToNativeForInterpreterVisitor(BasicDMNToNativeTransformer dmnTransformer) {
        super(dmnTransformer);
    }

    @Override
    protected String makeNavigation(Expression element, Type sourceType, String source, String memberName, String memberVariableName) {
        if (sourceType instanceof ItemDefinitionType) {
            String javaType = dmnTransformer.toNativeType(((ItemDefinitionType) sourceType).getMemberType(memberName));
            return this.nativeFactory.makeItemDefinitionSelectExpression(source, memberName, javaType);
        } else {
            return super.makeNavigation(element, sourceType, source, memberName, memberVariableName);
        }
    }
}
