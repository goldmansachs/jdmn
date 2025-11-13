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
package com.gs.dmn.feel.analysis;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TNamedElement;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.SemanticError;
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.feel.FEELExpressionLocation;
import com.gs.dmn.feel.ModelLocation;
import com.gs.dmn.feel.analysis.semantics.type.DateType;
import com.gs.dmn.feel.analysis.semantics.type.NumberType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.visitor.AbstractVisitor;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.DMNEnvironmentFactory;
import com.gs.dmn.transformation.basic.ExternalFunctionExtractor;
import com.gs.dmn.transformation.native_.NativeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAnalysisVisitor<T, C, R> extends AbstractVisitor<T, C, R> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractAnalysisVisitor.class);

    protected final BasicDMNToNativeTransformer<T, C> dmnTransformer;

    protected final DMNModelRepository dmnModelRepository;
    protected final EnvironmentFactory environmentFactory;

    protected final NativeTypeFactory nativeTypeFactory;
    protected final NativeFactory nativeFactory;

    protected final DMNEnvironmentFactory dmnEnvironmentFactory;
    protected final ExternalFunctionExtractor functionExtractor;

    protected AbstractAnalysisVisitor(BasicDMNToNativeTransformer<T, C> dmnTransformer, ErrorHandler errorHandler) {
        super(errorHandler);
        this.dmnTransformer = dmnTransformer;

        this.dmnModelRepository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();

        this.nativeTypeFactory = dmnTransformer.getNativeTypeFactory();
        this.dmnEnvironmentFactory = dmnTransformer.getDMNEnvironmentFactory();
        this.functionExtractor = new ExternalFunctionExtractor();
        this.nativeFactory = dmnTransformer.getNativeFactory();
    }

    public BasicDMNToNativeTransformer<T, C> getDmnTransformer() {
        return dmnTransformer;
    }

    protected boolean isValidForIterationDomainType(Type elementType) {
        return Type.conformsTo(elementType, NumberType.NUMBER) || Type.conformsTo(elementType, DateType.DATE);
    }

    protected void handleError(SemanticError error) {
        throw new SemanticErrorException(error.toText());
    }

    protected void handleError(SemanticError error, Exception e) {
        throw new SemanticErrorException(error.toText(), e);
    }

    protected SemanticError makeDMNError(DMNContext context, String errorMessage) {
        // Make DMN location
        ModelLocation modelLocation = makeModelLocation(context);
        // Make error
        return ErrorFactory.makeDMNError(modelLocation, errorMessage);
    }

    protected SemanticError makeELExpressionError(DMNContext context, Expression<Type> expression, String errorMessage) {
        // Make DMN location
        FEELExpressionLocation location = makeExpressionLocation(context, expression);
        // Make error
        return ErrorFactory.makeELExpressionError(location, errorMessage);
    }

    protected ModelLocation makeModelLocation(DMNContext context) {
        TNamedElement element = context == null ? null : context.getElement();
        TDefinitions model = this.dmnModelRepository.getModel(element);
        return new ModelLocation(model, element);
    }

    protected FEELExpressionLocation makeExpressionLocation(DMNContext context, Expression<Type> expression) {
        TNamedElement element = context == null ? null : context.getElement();
        TDefinitions definitions = this.dmnModelRepository.getModel(element);
        return new FEELExpressionLocation(definitions, element, expression);
    }
}
