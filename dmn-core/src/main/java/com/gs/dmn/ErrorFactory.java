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
package com.gs.dmn;

import com.gs.dmn.ast.TDMNElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ModelLocation;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.SeverityLevel;

public class ErrorFactory {
    private ErrorFactory() {
    }

    //
    // Factory methods for SemanticErrors
    //
    public static SemanticError makeDMNError(ModelCoordinates coordinates, String errorMessage) {
        ModelLocation location = makeLocation(coordinates);
        return new SemanticError(SeverityLevel.ERROR, location, errorMessage);
    }

    public static SemanticError makeExpressionError(ModelCoordinates coordinates, String errorMessage) {
        ModelLocation location = makeLocation(coordinates);

        // Add expression to error message
        String finalErrorMessage = String.format("%s for expression '%s'", errorMessage, coordinates.expressionDescription());
        return new SemanticError(SeverityLevel.ERROR, location, finalErrorMessage);
    }

    public static SemanticError makeIfError(ModelCoordinates coordinates, Type thenType, Type elseType) {
        return makeDMNError(coordinates, String.format("Types of then and else branches are incompatible, found '%s' and '%s'", thenType, elseType));
    }

    private static ModelLocation makeLocation(ModelCoordinates coordinates) {
        if (coordinates == null) {
            return null;
        }

        TDefinitions definitions = coordinates.getModel();
        TDMNElement element = coordinates.getElement();
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

        return new ModelLocation(namespace, modelName, modelId, elementName, elementId);
    }
}