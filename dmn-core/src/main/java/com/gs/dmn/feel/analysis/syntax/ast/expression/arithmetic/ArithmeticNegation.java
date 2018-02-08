/**
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
package com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic;

import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.FEELContext;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

import static com.gs.dmn.feel.analysis.semantics.type.NumberType.NUMBER;

public class ArithmeticNegation extends ArithmeticExpression {
    public ArithmeticNegation(Expression leftOperand) {
        super("-", leftOperand, null);
    }

    @Override
    public void deriveType(Environment environment) {
        Type type = getLeftOperand().getType();
        setType(NUMBER);
        if (type != NUMBER) {
            throw new SemanticError(this, String.format("Operator '%s' cannot be applied to '%s'", getOperator(), type));
        }
    }

    @Override
    public Object accept(Visitor visitor, FEELContext params) {
        return visitor.visit(this, params);
    }

    @Override
    public String toString() {
        return String.format("ArithmeticNegation(%s)", getLeftOperand());
    }
}
 
