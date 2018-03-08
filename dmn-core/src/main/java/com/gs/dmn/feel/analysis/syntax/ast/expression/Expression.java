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
package com.gs.dmn.feel.analysis.syntax.ast.expression;

import com.gs.dmn.feel.OperatorDecisionTable;
import com.gs.dmn.feel.analysis.semantics.SemanticError;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.Element;

public abstract class Expression extends Element {
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type memberType(Type sourceType, String member) {
        Type memberType = null;
        if (sourceType instanceof ItemDefinitionType) {
            memberType = ((ItemDefinitionType) sourceType).getMemberType(member);
        } else if (sourceType instanceof ContextType) {
            memberType = ((ContextType) sourceType).getMemberType(member);
        } else if (sourceType instanceof DateType) {
            memberType = DateType.getMemberType(member);
        } else if (sourceType instanceof TimeType) {
            memberType = TimeType.getMemberType(member);
        } else if (sourceType instanceof DateTimeType) {
            memberType = DateTimeType.getMemberType(member);
        } else if (sourceType instanceof DurationType) {
            memberType = DurationType.getMemberType(sourceType, member);
        }
        if (memberType == null) {
            throw new SemanticError(this, String.format("Cannot derive member type for '%s' in element '%s'", member, this));
        } else {
            return memberType;
        }
    }

    public Type navigationType(Type sourceType, String member) {
        Type type;
        if (sourceType instanceof ListType) {
            Type memberType = memberType(((ListType) sourceType).getElementType(), member);
            type = new ListType(memberType);
        } else {
            type = memberType(sourceType, member);
        }
        return type;
    }

    public abstract void deriveType(Environment environment);

    protected void checkType(String operator, Type leftOperandType, Type rightOperandType) {
        try {
            Type resultType = OperatorDecisionTable.resultType(operator, normalize(leftOperandType), normalize(rightOperandType));
            if (resultType != null) {
                setType(resultType);
            } else {
                throw new SemanticError(this, String.format("Operator '%s' cannot be applied to '%s', '%s'", operator, leftOperandType, rightOperandType));
            }
        } catch (Exception e) {
            throw new SemanticError(this, String.format("Operator '%s' cannot be applied to '%s', '%s'", operator, leftOperandType, rightOperandType), e);
        }
    }

    private Type normalize(Type type) {
        return type;
    }
}
 
