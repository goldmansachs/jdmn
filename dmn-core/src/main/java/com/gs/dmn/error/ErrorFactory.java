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

import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TExpression;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ErrorFactory {
    private ErrorFactory() {
    }

    public static String makeDMNErrorMessage(TDefinitions definitions, TNamedElement element, String errorMessage) {
        String location = makeLocation(definitions, element);
        return makeErrorMessage(location, errorMessage);
    }

    public static String makeDMNExpressionErrorMessage(TDefinitions definitions, TNamedElement element, TExpression expression, String errorMessage) {
        String expressionText = expressionDescription(expression);
        return makeExpressionErrorMessage(definitions, element, expressionText, errorMessage);
    }

    public static String makeELExpressionErrorMessage(TDefinitions definitions, TNamedElement element, Expression<Type> expression, String errorMessage) {
        String expressionText = expressionDescription(expression);
        return makeExpressionErrorMessage(definitions, element, expressionText, errorMessage);
    }

    private static String makeExpressionErrorMessage(TDefinitions definitions, TNamedElement element, String expressionText, String errorMessage) {
        String modelLocation = makeLocation(definitions, element);

        // Make final error message
        String finalErrorMessage;
        if (modelLocation == null) {
            finalErrorMessage = errorMessage;
        } else {
            finalErrorMessage = modelLocation + " " + errorMessage;
        }

        // Add expression location
        String expressionLocation = String.format(" for expression '%s'", expressionText);
        finalErrorMessage += expressionLocation;

        return finalErrorMessage;
    }

    private static String expressionDescription(Object expression) {
        return expression == null ? null : expression.toString();
    }

    public static String makeErrorMessage(String location, String errorMessage) {
        if (location == null) {
            return errorMessage;
        } else {
            return location + ": " + errorMessage;
        }
    }

    // DMN location of the error
    public static String makeLocation(TDefinitions definitions, TDMNElement element) {
        if (definitions == null && element == null) {
            return null;
        }

        List<String> locationParts = new ArrayList<>();
        addModelCoordinates(definitions, element, locationParts);
        addElementCoordinates(element, locationParts);
        return locationParts.isEmpty() ? null : String.format("(%s)", String.join(", ", locationParts));
    }

    protected static void addModelCoordinates(TDefinitions definitions, TDMNElement element, List<String> locationParts) {
        if (definitions != null) {
            String modelName = definitions.getName();
            if (!StringUtils.isBlank(modelName)) {
                locationParts.add(String.format("model='%s'", modelName));
            }
        }
    }

    protected static void addElementCoordinates(TDMNElement element, List<String> locationParts) {
        if (element != null) {
            String id = element.getId();
            String label = element.getLabel();
            String name = element instanceof TNamedElement ? ((TNamedElement) element).getName() : null;
            if (!StringUtils.isBlank(label)) {
                locationParts.add(String.format("label='%s'", label));
            }
            if (!StringUtils.isBlank(name)) {
                locationParts.add(String.format("name='%s'", name));
            }
            if (!StringUtils.isBlank(id)) {
                locationParts.add(String.format("id='%s'", id));
            }
        }
    }

    public static String makeIfErrorMessage(TNamedElement element, Type thenType, Type elseType) {
        if (element == null) {
            return String.format("Types of then and else branches are incompatible, found '%s' and '%s' in element '%s',", thenType, elseType, element.getName());
        } else {
            return String.format("Types of then and else branches are incompatible, found '%s' and '%s',", thenType, elseType);
        }
    }

    public static String getDiagramId(TDMNElement element) {
        if (element == null) {
            return null;
        }
        Map<QName, String> otherAttributes = element.getOtherAttributes();
        if (otherAttributes != null) {
            for (Map.Entry<QName, String> entry : otherAttributes.entrySet()) {
                if ("diagramId".equals(entry.getKey().getLocalPart())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

}
