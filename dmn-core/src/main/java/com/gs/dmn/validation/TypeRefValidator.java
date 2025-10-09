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
package com.gs.dmn.validation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.TraversalVisitor;
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.feel.analysis.semantics.type.FEELType;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.serialization.DMNVersion;
import org.apache.commons.lang3.StringUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TypeRefValidator extends SimpleDMNValidator {
    public TypeRefValidator() {
        this(new Slf4jBuildLogger(LOGGER));
    }

    public TypeRefValidator(BuildLogger logger) {
        super(logger);
    }

    @Override
    public List<String> validate(DMNModelRepository dmnModelRepository) {
        if (isEmpty(dmnModelRepository)) {
            this.logger.warn("DMN repository is empty; validator will not run");
            return new ArrayList<>();
        }

        List<Pair<TNamedElement, String>> pairs = makeErrorReport(dmnModelRepository);
        return pairs.stream().map(Pair::getRight).collect(Collectors.toList());
    }

    public List<Pair<TNamedElement, String>> makeErrorReport(DMNModelRepository dmnModelRepository) {
        List<Pair<TNamedElement, String>> errorReport = new ArrayList<>();
        TypeRefValidatorVisitor visitor = new TypeRefValidatorVisitor(this.logger, this.errorHandler);
        for (TDefinitions definitions: dmnModelRepository.getAllDefinitions()) {
            TypeRefValidationContext context = new TypeRefValidationContext(definitions, dmnModelRepository, errorReport);
            definitions.accept(visitor, context);
        }
        return errorReport;
    }
}

class TypeRefValidatorVisitor extends TraversalVisitor<TypeRefValidationContext> {

    public TypeRefValidatorVisitor(BuildLogger logger, ErrorHandler errorHandler) {
        super(logger, errorHandler);
    }

    @Override
    public DMNBaseElement visit(TItemDefinition element, TypeRefValidationContext context) {
        if (element == null) {
            return null;
        }

        context.setElement(element);

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TInputData element, TypeRefValidationContext context) {
        if (element == null) {
            return null;
        }

        context.setElement(element);

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TDecision element, TypeRefValidationContext context) {
        if (element == null) {
            return null;
        }

        context.setElement(element);

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TBusinessKnowledgeModel element, TypeRefValidationContext context) {
        if (element == null) {
            return null;
        }

        context.setElement(element);

        return super.visit(element, context);
    }

    @Override
    public DMNBaseElement visit(TDecisionService element, TypeRefValidationContext context) {
        if (element == null) {
            return null;
        }

        context.setElement(element);

        return super.visit(element, context);
    }

    protected QName visitTypeRef(QName typeRef, TypeRefValidationContext context) {
        if (typeRef == null) {
            return null;
        }

        String typeRef1 = QualifiedName.toName(typeRef);
        // Ignore primitive types
        if (isPrimitiveType(typeRef1) || StringUtils.isEmpty(typeRef1)) {
            return typeRef;
        }

        // Lookup for itemDefinitions starting from the current model
        TDefinitions model = context.getModel();
        QualifiedName qualifiedName = QualifiedName.toQualifiedName(model, typeRef1);
        TItemDefinition itemDefinition = context.getRepository().lookupItemDefinition(model, qualifiedName);
        if (itemDefinition == null) {
            TNamedElement element = context.getElement();
            String errorMessage = ErrorFactory.makeDMNErrorMessage(model, element, String.format("Cannot find definition of typeRef '%s'", typeRef1));
            context.getErrorReport().add(new Pair<>(element, errorMessage));

            this.logger.debug(errorMessage);
        }

        return typeRef;
    }

    private boolean isPrimitiveType(String name) {
        return FEELType.FEEL_TYPE_NAMES.contains(name) || name.startsWith(DMNVersion.LATEST.getFeelPrefix());
    }
}
