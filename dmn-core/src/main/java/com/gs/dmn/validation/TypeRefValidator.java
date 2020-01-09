/*
 Copyright 2016.
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
   http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
*/
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.analysis.semantics.type.FEELTypes;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.serialization.DMNVersion;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20180521.model.*;

import java.util.ArrayList;
import java.util.List;

public class TypeRefValidator extends SimpleDMNValidator {
    public TypeRefValidator() {
        super(new Slf4jBuildLogger(LOGGER));
    }

    public TypeRefValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        List<String> errors = new ArrayList<>();

        if (dmnModelRepository == null) {
            throw new IllegalArgumentException("Missing definitions");
        }

        List<TDRGElement> drgElements = dmnModelRepository.drgElements();
        for (TDRGElement element: drgElements) {
            logger.debug(String.format("Validate element '%s'", element.getName()));
            if (element instanceof TInputData) {
                TInformationItem variable = ((TInputData) element).getVariable();
                validate(variable, element, dmnModelRepository, errors);
            } else if (element instanceof TDecision) {
                TInformationItem variable = ((TDecision) element).getVariable();
                validate(variable, element, dmnModelRepository, errors);
            }
        }

        return errors;
    }

    private void validate(TInformationItem variable, TDRGElement element, DMNModelRepository dmnModelRepository, List<String> errors) {
        if (variable != null) {
            String varTypeRef = variable.getTypeRef();
            if (!isPrimitiveType(varTypeRef) && StringUtils.isNotEmpty(varTypeRef)) {
                QualifiedName typeRef = QualifiedName.toQualifiedName(varTypeRef);
                TItemDefinition itemDefinition = dmnModelRepository.lookupItemDefinition(typeRef);
                if (itemDefinition == null) {
                    String error = String.format("Cannot find type '%s' for DRGElement '%s'", typeRef.toString(), element.getName());
                    errors.add(error);
                }
            }
        }
    }

    private boolean isPrimitiveType(String name) {
        return FEELTypes.FEEL_TYPE_NAMES.contains(name) || name.startsWith(DMNVersion.DMN_12.getFeelPrefix());
    }
}
