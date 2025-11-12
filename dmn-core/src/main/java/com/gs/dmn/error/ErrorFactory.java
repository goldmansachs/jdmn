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
package com.gs.dmn.error;

import com.gs.dmn.ast.*;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.DMNExpressionLocation;
import com.gs.dmn.feel.FEELExpressionLocation;
import com.gs.dmn.feel.ModelLocation;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;

public class ErrorFactory {
    private ErrorFactory() {
    }

    //
    // Factory methods for SemanticErrors
    //
    public static SemanticError makeDMNError(ModelLocation modelLocation, String errorMessage) {
        LocationInfo location = makeLocation(modelLocation);
        return new SemanticError(SeverityLevel.ERROR, location, errorMessage);
    }

    public static SemanticError makeDMNExpressionError(DMNExpressionLocation location, String errorMessage) {
        TExpression expression = location.getExpression();
        String expressionText = expressionDescription(expression);
        return makeExpressionError(location, expressionText, errorMessage);
    }

    public static SemanticError makeELExpressionError(FEELExpressionLocation location, String errorMessage) {
        Expression<Type> expression = location.getExpression();
        String expressionText = expressionDescription(expression);
        return makeExpressionError(location, expressionText, errorMessage);
    }

    private static SemanticError makeExpressionError(ModelLocation modelLocation, String expressionText, String errorMessage) {
        LocationInfo locationInfo = makeLocation(modelLocation);

        // Add expression to error message
        String finalErrorMessage = String.format("%s for expression '%s'", errorMessage, expressionText);
        return new SemanticError(SeverityLevel.ERROR, locationInfo, finalErrorMessage);
    }

    public static SemanticError makeIfError(ModelLocation location, Type thenType, Type elseType) {
        String errorMessage = String.format("Types of then and else branches are incompatible, found '%s' and '%s'", thenType, elseType);
        return makeDMNError(location, errorMessage);
    }

    // DMN location of the error
    public static LocationInfo makeLocation(ModelLocation location) {
        if (location == null) {
            return null;
        }

        TDefinitions definitions = location.getModel();
        TDMNElement element = location.getElement();
        if (definitions == null && element == null) {
            return null;
        }

        // Calculate model coordinates
        String namespace = null;
        String modelName = null;
        String modelId = null;
        if (definitions != null) {
            namespace = definitions.getNamespace();
            modelName = definitions.getName();
            modelId = definitions.getId();
        }
        // Calculate element coordinates
        String elementName = null;
        String elementId = null;
        if (element != null) {
            elementId = element.getId();
        }
        if (element instanceof TNamedElement) {
            elementName = ((TNamedElement) element).getName();
        }

        return new LocationInfo(namespace, modelName, modelId, elementName, elementId);
    }

    private static String expressionDescription(Object expression) {
        if (expression == null) {
            return null;
        } else if (expression instanceof TLiteralExpression) {
            return ((TLiteralExpression) expression).getText();
        } else {
            return expression.toString();
        }
    }
}
