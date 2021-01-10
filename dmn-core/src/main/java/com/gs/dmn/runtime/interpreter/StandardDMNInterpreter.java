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

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.LogErrorHandler;
import com.gs.dmn.feel.analysis.semantics.environment.Environment;
import com.gs.dmn.feel.analysis.semantics.environment.EnvironmentFactory;
import com.gs.dmn.feel.analysis.semantics.type.ContextType;
import com.gs.dmn.feel.analysis.semantics.type.ListType;
import com.gs.dmn.feel.analysis.semantics.type.NullType;
import com.gs.dmn.feel.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.interpreter.FEELInterpreterImpl;
import com.gs.dmn.feel.interpreter.TypeConverter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.interpreter.environment.RuntimeEnvironment;
import com.gs.dmn.runtime.listener.Arguments;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.*;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.basic.QualifiedName;
import org.apache.commons.lang3.StringUtils;
import org.omg.spec.dmn._20191111.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.feel.analysis.semantics.type.AnyType.ANY;

public class StandardDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StandardDMNInterpreter.class);

    protected static EventListener EVENT_LISTENER = new LoggingEventListener(LOGGER);
    public static void setEventListener(EventListener eventListener) {
        EVENT_LISTENER = eventListener;
    }

    private final DMNModelRepository dmnModelRepository;
    private final EnvironmentFactory environmentFactory;
    protected final ErrorHandler errorHandler;

    private final BasicDMNToNativeTransformer basicDMNTransformer;
    protected final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib;
    private final FEELInterpreter feelInterpreter;
    private final TypeConverter typeConverter;

    public StandardDMNInterpreter(BasicDMNToNativeTransformer basicDMNTransformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib, TypeConverter typeConverter) {
        this.errorHandler = new LogErrorHandler(LOGGER);
        this.typeConverter = typeConverter;
        this.basicDMNTransformer = basicDMNTransformer;
        this.dmnModelRepository = basicDMNTransformer.getDMNModelRepository();
        this.environmentFactory = basicDMNTransformer.getEnvironmentFactory();
        this.feelLib = feelLib;
        this.feelInterpreter = new FEELInterpreterImpl<>(this);
    }

    @Override
    public BasicDMNToNativeTransformer getBasicDMNTransformer() {
        return this.basicDMNTransformer;
    }

    @Override
    public FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getFeelLib() {
        return this.feelLib;
    }

    @Override
    public TypeConverter getTypeConverter() {
        return this.typeConverter;
    }

    //
    // Evaluate TDecisions
    //
    @Override
    public Result evaluate(DRGElementReference<? extends TDecision> reference, Map<String, Object> informationRequirements) {
        try {
            DMNContext context = DMNContext.of(
                    reference.getElement(),
                    this.basicDMNTransformer.makeEnvironment(reference.getElement()),
                    RuntimeEnvironment.of()
            );
            for (Map.Entry<String, Object> entry: informationRequirements.entrySet()) {
                context.bind(entry.getKey(), entry.getValue());
            }
            List<Object> args = new ArrayList<>();
            return evaluate(reference, args, context);
        } catch (Exception e) {
            String errorMessage = "Evaluation error";
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    //
    // Evaluate TInvocables
    //
    @Override
    public Result evaluate(DRGElementReference<? extends TInvocable> reference, List<Object> args) {
        try {
            DMNContext context = DMNContext.of(
                    reference.getElement(),
                    this.basicDMNTransformer.makeEnvironment(reference.getElement()),
                    RuntimeEnvironment.of()
            );
            return evaluate(reference, args, context);
        } catch (Exception e) {
            String errorMessage = "Evaluation error";
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    @Override
    public Result evaluate(TInvocable invocable, List<Object> argList, DMNContext context) {
        try {
            DMNContext invocationContext = DMNContext.of(
                    invocable,
                    this.basicDMNTransformer.makeEnvironment(invocable, context.getEnvironment()),
                    context.getRuntimeEnvironment()
            );
            Result result;
            if (invocable instanceof TDecisionService) {
                result = evaluate(this.dmnModelRepository.makeDRGElementReference(invocable), argList, invocationContext);
            } else if (invocable instanceof TBusinessKnowledgeModel) {
                result = evaluate(this.dmnModelRepository.makeDRGElementReference(invocable), argList, invocationContext);
            } else {
                throw new IllegalArgumentException(String.format("Not supported type '%s'", invocable.getClass().getSimpleName()));
            }
            return result;
        } catch (Exception e) {
            String errorMessage = "Evaluation error";
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    @Override
    public Result evaluate(TFunctionDefinition functionDefinition, List<Object> argList, DMNContext context) {
        try {
            // Create new environments and bind parameters
            DMNContext functionContext = DMNContext.of(
                    context.getElement(),
                    this.environmentFactory.makeEnvironment(context.getEnvironment()),
                    RuntimeEnvironment.of(context.getRuntimeEnvironment())
            );
            List<TInformationItem> formalParameterList = functionDefinition.getFormalParameter();
            TDefinitions model = this.dmnModelRepository.getModel(context.getElement());
            for (int i = 0; i < formalParameterList.size(); i++) {
                TInformationItem param = formalParameterList.get(i);
                String name = param.getName();
                Type type = this.basicDMNTransformer.toFEELType(null, QualifiedName.toQualifiedName(model, param.getTypeRef()));
                Object value = argList.get(i);
                functionContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, type));
                functionContext.bind(name, value);
            }

            // Execute function body
            JAXBElement<? extends TExpression> expressionElement = functionDefinition.getExpression();
            Result output;
            if (expressionElement == null) {
                output = null;
            } else {
                TExpression expression = expressionElement.getValue();
                output = evaluateExpression(null, expression, functionContext, null);
            }

            return output;
        } catch (Exception e) {
            String errorMessage = "Evaluation error";
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    private Result evaluate(DRGElementReference<? extends TDRGElement> reference, List<Object> args, DMNContext context) {
        try {
            TDRGElement drgElement = reference.getElement();
            Result actualOutput;
            if (drgElement instanceof TInputData) {
                actualOutput = evaluateInputData((DRGElementReference<TInputData>) reference, context);
            } else if (drgElement instanceof TDecision) {
                actualOutput = evaluateDecision((DRGElementReference<TDecision>) reference, context);
            } else if (drgElement instanceof TDecisionService) {
                actualOutput = evaluateDecisionService((DRGElementReference<TDecisionService>) reference, args, context);
            } else if (drgElement instanceof TBusinessKnowledgeModel) {
                actualOutput = evaluateBKM((DRGElementReference<TBusinessKnowledgeModel>) reference, args, context);
            } else {
                throw new IllegalArgumentException(String.format("Not supported type '%s'", drgElement.getClass().getSimpleName()));
            }
            return actualOutput;
        } catch (Exception e) {
            String errorMessage = "Evaluation error";
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    private Result evaluateInputData(DRGElementReference<TInputData> reference, DMNContext context) {
        TInputData inputData = reference.getElement();
        Object value = lookupBinding(context, reference);
        return Result.of(value, this.basicDMNTransformer.drgElementOutputFEELType(inputData));
    }

    private Result evaluateDecision(DRGElementReference<TDecision> reference, DMNContext context) {
        applyDecision(reference, context);

        TDecision decision = reference.getElement();
        Object value = lookupBinding(context, reference);
        return Result.of(value, this.basicDMNTransformer.drgElementOutputFEELType(decision));
    }

    private Result evaluateBKM(DRGElementReference<TBusinessKnowledgeModel> reference, List<Object> argList, DMNContext context) {
        TBusinessKnowledgeModel bkm = reference.getElement();
        TDefinitions model = this.dmnModelRepository.getModel(bkm);

        // Create BKM context
        DMNContext bkmContext = DMNContext.of(
                bkm,
                this.basicDMNTransformer.makeEnvironment(bkm, context.getEnvironment()),
                RuntimeEnvironment.of(context.getRuntimeEnvironment())
        );

        // BKM start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(bkm);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(bkm, bkmContext);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Bind parameters
        List<TInformationItem> formalParameterList = bkm.getEncapsulatedLogic().getFormalParameter();
        for (int i = 0; i < formalParameterList.size(); i++) {
            TInformationItem param = formalParameterList.get(i);
            String name = param.getName();
            String paramTypeRef = param.getTypeRef();
            Type paramType = null;
            if (!StringUtils.isEmpty(paramTypeRef)) {
                paramType = this.basicDMNTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, paramTypeRef));
            }
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = this.typeConverter.convertValue(value, paramType, this.feelLib);
            value = Result.value(result);

            // Declaration is already in environment
            bkmContext.bind(name, value);
        }

        // Execute function body
        TExpression expression = this.dmnModelRepository.expression(bkm);
        Result result = evaluateExpression(bkm, expression, bkmContext, drgElementAnnotation);
        Object value = Result.value(result);

        // Check value and apply implicit conversions
        Type expectedType = this.basicDMNTransformer.drgElementOutputFEELType(bkm, bkmContext.getEnvironment());
        result = this.typeConverter.convertResult(result, expectedType, this.feelLib);
        value = Result.value(result);

        // BKM end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, value, (System.currentTimeMillis() - startTime_));

        return result;
    }

    private Result evaluateDecisionService(DRGElementReference<TDecisionService> serviceReference, List<Object> argList, DMNContext context) {
        TDecisionService service = serviceReference.getElement();

        // Create service context
        DMNContext serviceContext = DMNContext.of(
                service,
                this.environmentFactory.makeEnvironment(context.getEnvironment()),
                RuntimeEnvironment.of(context.getRuntimeEnvironment())
        );

        // Decision Service start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(service);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(service, serviceContext);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Bind parameters
        List<FormalParameter> formalParameterList = this.basicDMNTransformer.dsFEELParameters(service);
        for (int i = 0; i < formalParameterList.size(); i++) {
            FormalParameter param = formalParameterList.get(i);
            String name = param.getName();
            Type type = param.getType();
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = this.typeConverter.convertValue(value, type, this.feelLib);
            value = Result.value(result);

            serviceContext.addDeclaration(this.environmentFactory.makeVariableDeclaration(name, type));
            serviceContext.bind(name, value);
        }

        // Evaluate output decisions
        List<TDecision> outputDecisions = new ArrayList<>();
        List<ImportPath> outputDecisionImportPaths = new ArrayList<>();
        for (TDMNElementReference outputDecisionReference: service.getOutputDecision()) {
            TDecision decision = this.dmnModelRepository.findDecisionByRef(service, outputDecisionReference.getHref());
            outputDecisions.add(decision);

            String importName = this.dmnModelRepository.findImportName(service, outputDecisionReference);
            ImportPath decisionImportPath = new ImportPath(serviceReference.getImportPath(), importName);
            outputDecisionImportPaths.add(decisionImportPath);

            applyDecision(this.dmnModelRepository.makeDRGElementReference(decisionImportPath, decision), serviceContext);
        }

        // Make context result
        Object output;
        if (outputDecisions.size() == 1) {
            TDecision decision = outputDecisions.get(0);
            ImportPath childImportPath = outputDecisionImportPaths.get(0);
            output = lookupBinding(serviceContext, this.dmnModelRepository.makeDRGElementReference(childImportPath, decision));
        } else {
            output = new Context();
            for(int i=0; i < outputDecisions.size(); i++) {
                TDecision decision = outputDecisions.get(i);
                ImportPath decisionImportPath = outputDecisionImportPaths.get(i);
                Object value = lookupBinding(serviceContext, this.dmnModelRepository.makeDRGElementReference(decisionImportPath, decision));
                ((Context) output).add(decision.getName(), value);
            }
        }

        // Check value and apply implicit conversions
        Type expectedType = this.basicDMNTransformer.drgElementOutputFEELType(service, serviceContext.getEnvironment());
        Result result = this.typeConverter.convertValue(output, expectedType, this.feelLib);
        output = Result.value(result);

        // Set variable
        serviceContext.bind(service.getName(), output);

        // Decision service end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));

        return Result.of(output, this.basicDMNTransformer.drgElementOutputFEELType(service));
    }

    private void evaluateKnowledgeRequirements(ImportPath importPath, TDRGElement parent, List<TKnowledgeRequirement> knowledgeRequirementList, DMNContext context) {
        for (TKnowledgeRequirement requirement : knowledgeRequirementList) {
            // Find invocable
            TDMNElementReference requiredKnowledge = requirement.getRequiredKnowledge();
            String href = requiredKnowledge.getHref();
            TInvocable invocable = this.dmnModelRepository.findInvocableByRef(parent, href);

            // Calculate import path
            String importName = this.dmnModelRepository.findImportName(parent, requiredKnowledge);
            ImportPath invocableImportPath = new ImportPath(importPath, importName);

            // Evaluate invocable
            if (invocable instanceof TBusinessKnowledgeModel) {
                applyBKM(this.dmnModelRepository.makeDRGElementReference(invocableImportPath, (TBusinessKnowledgeModel) invocable), context);
            } else if (invocable instanceof TDecisionService) {
                applyDecisionService(this.dmnModelRepository.makeDRGElementReference(invocableImportPath, (TDecisionService) invocable), context);
            } else {
                throw new UnsupportedOperationException(String.format("Not supported invocable '%s'", invocable.getClass().getSimpleName()));
            }
        }
    }

    private void applyBKM(DRGElementReference<TBusinessKnowledgeModel> reference, DMNContext context) {
        ImportPath importPath = reference.getImportPath();
        TBusinessKnowledgeModel bkm = reference.getElement();

        // Evaluate knowledge requirements
        List<TKnowledgeRequirement> knowledgeRequirement = bkm.getKnowledgeRequirement();
        evaluateKnowledgeRequirements(importPath, bkm, knowledgeRequirement, context);

        // Bind name to DMN definition
        bind(context, reference, bkm);
    }

    private void applyDecisionService(DRGElementReference<TDecisionService> reference, DMNContext dmnContext) {
        ImportPath importPath = reference.getImportPath();
        TDecisionService service = reference.getElement();

        // Bind name to DMN definition
        bind(dmnContext, reference, service);
    }

    protected void applyDecision(DRGElementReference<TDecision> reference, DMNContext parentContext) {
        TDecision decision = reference.getElement();
        ImportPath importPath = reference.getImportPath();

        // Create decision context
        DMNContext decisionContext = DMNContext.of(
                decision,
                this.basicDMNTransformer.makeEnvironment(decision),
                parentContext.getRuntimeEnvironment()
        );

        // Decision start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(decision);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(decision, decisionContext);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Check if has already been evaluated
        String decisionName = decision.getName();
        Object output;
        if (dagOptimisation() && parentContext.isBound(decisionName)) {
            // Retrieve value from environment
            output = lookupBinding(parentContext, reference);
        } else {
            // Evaluate dependencies
            evaluateInformationRequirementList(importPath, decision, decision.getInformationRequirement(), decisionContext);
            evaluateKnowledgeRequirements(importPath, decision, decision.getKnowledgeRequirement(), decisionContext);

            // Evaluate expression
            TExpression expression = this.dmnModelRepository.expression(decision);
            Result result = evaluateExpression(decision, expression, decisionContext, drgElementAnnotation);
            output = Result.value(result);

            // Check value and apply implicit conversions
            Type expectedType = this.basicDMNTransformer.drgElementOutputFEELType(decision, decisionContext.getEnvironment());
            result = this.typeConverter.convertResult(result, expectedType, this.feelLib);
            output = Result.value(result);

            // Bind value
            bind(parentContext, reference, output);
        }

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));
    }

    protected boolean dagOptimisation() {
        return true;
    }

    private void evaluateInformationRequirementList(ImportPath importPath, TDRGElement parent, List<TInformationRequirement> informationRequirementList, DMNContext context) {
        for (TInformationRequirement informationRequirement : informationRequirementList) {
            TDMNElementReference requiredInput = informationRequirement.getRequiredInput();
            TDMNElementReference requiredDecision = informationRequirement.getRequiredDecision();
            if (requiredInput != null) {
                TInputData child = this.dmnModelRepository.findInputDataByRef(parent, requiredInput.getHref());
                String importName = this.dmnModelRepository.findImportName(parent, requiredInput);
                ImportPath childImportPath = new ImportPath(importPath, importName);

                // Add new binding to match path in parent
                addBinding(context, this.dmnModelRepository.makeDRGElementReference(childImportPath, child), importName);
            } else if (requiredDecision != null) {
                TDecision child = this.dmnModelRepository.findDecisionByRef(parent, requiredDecision.getHref());
                String importName = this.dmnModelRepository.findImportName(parent, requiredDecision);
                ImportPath childImportPath = new ImportPath(importPath, importName);
                evaluateDecision(this.dmnModelRepository.makeDRGElementReference(childImportPath, child), context);

                // Add new binding to match path in parent
                addBinding(context, this.dmnModelRepository.makeDRGElementReference(childImportPath, child), importName);
            } else {
                this.errorHandler.reportError("Incorrect InformationRequirement. Missing required input and decision");
            }
        }
    }

    //
    // Expression evaluation
    //
    protected Result evaluateExpression(TDRGElement element, TExpression expression, DMNContext context, DRGElement elementAnnotation) {
        Result result = null;
        if (expression == null) {
            String message = String.format("Missing expression for element '%s'", element == null ? null : element.getName());
            this.errorHandler.reportError(message);
        } else if (expression instanceof TContext) {
            result = evaluateContextExpression(element, (TContext) expression, context, elementAnnotation);
        } else if (expression instanceof TDecisionTable) {
            result = evaluateDecisionTable(element, (TDecisionTable)expression, context, elementAnnotation);
        } else if (expression instanceof TFunctionDefinition) {
            result = evaluateFunctionDefinitionExpression(element, (TFunctionDefinition)expression, context, elementAnnotation);
        } else if (expression instanceof TInvocation) {
            result = evaluateInvocationExpression(element, (TInvocation) expression, context, elementAnnotation);
        } else if (expression instanceof TLiteralExpression) {
            result = evaluateLiteralExpression(element, (TLiteralExpression) expression, context, elementAnnotation);
        } else if (expression instanceof TList) {
            result = evaluateListExpression(element, (TList) expression, context, elementAnnotation);
        } else if (expression instanceof TRelation) {
            result = evaluateRelationExpression(element, (TRelation) expression, context, elementAnnotation);
        } else {
            this.errorHandler.reportError(String.format("Expression '%s' not supported yet", expression.getClass().getSimpleName()));
        }
        return result;
    }

    private Result evaluateLiteralExpression(TDRGElement element, TLiteralExpression expression, DMNContext context, DRGElement elementAnnotation) {
        String text = expression.getText();
        return evaluateLiteralExpression(element, text, context);
    }

    protected Result evaluateLiteralExpression(TDRGElement element, String text, DMNContext context) {
        DMNContext expContext = DMNContext.of(
                element,
                context.getEnvironment(),
                context.getRuntimeEnvironment()
        );
        return this.feelInterpreter.evaluateExpression(text, expContext);
    }

    private Result evaluateInvocationExpression(TDRGElement element, TInvocation invocation, DMNContext context, DRGElement elementAnnotation) {
        // Compute name-java binding for arguments
        Map<String, Object> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName = binding.getParameter().getName();
            TExpression argExpression = binding.getExpression().getValue();
            Result argResult = evaluateExpression(element, argExpression, context, elementAnnotation);
            Object argJava = Result.value(argResult);
            argBinding.put(argName, argJava);
        }

        // Evaluate body
        TExpression body = invocation.getExpression().getValue();
        if (body instanceof TLiteralExpression) {
            // Find BKM
            String bkmName = ((TLiteralExpression) body).getText();
            TBusinessKnowledgeModel bkm = this.dmnModelRepository.findKnowledgeModelByName(bkmName);
            if (bkm == null) {
                throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
            }
            // Make args
            List<Object> argList = new ArrayList<>();
            List<String> formalParameterList = this.basicDMNTransformer.bkmFEELParameterNames(bkm);
            for(String paramName: formalParameterList) {
                if (argBinding.containsKey(paramName)) {
                    Object argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find binding for parameter '%s'", paramName));
                }
            }

            // Evaluate invocation
            DMNContext invocationContext = DMNContext.of(
                    element,
                    this.basicDMNTransformer.makeEnvironment(element),
                    context.getRuntimeEnvironment()
            );
            return evaluate(this.dmnModelRepository.makeDRGElementReference(bkm), argList, invocationContext);
        } else {
            throw new UnsupportedOperationException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }

    private Result evaluateContextExpression(TDRGElement element, TContext tContext, DMNContext parentContext, DRGElement elementAnnotation) {
        TDefinitions model = this.dmnModelRepository.getModel(element);

        // Make context
        Pair<Environment, Map<TContextEntry, Expression>> pair = this.basicDMNTransformer.makeContextEnvironment(element, tContext, parentContext.getEnvironment());
        DMNContext expContext = DMNContext.of(
                element,
                pair.getLeft(),
                RuntimeEnvironment.of(parentContext.getRuntimeEnvironment())
        );
        Map<TContextEntry, Expression> literalExpressionMap = pair.getRight();

        // Evaluate entries
        Result returnResult = null;
        Map<TContextEntry, Result> entryResultMap = new LinkedHashMap<>();
        for(TContextEntry entry: tContext.getContextEntry()) {
            // Evaluate entry value
            Result entryResult;
            JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
            if (jaxbElement != null) {
                TExpression expression = jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression feelExpression = literalExpressionMap.get(entry);
                    entryResult = this.feelInterpreter.evaluateExpression(feelExpression, expContext);
                } else {
                    entryResult = evaluateExpression(element, expression, expContext, elementAnnotation);
                }
            } else {
                entryResult = null;
            }
            entryResultMap.put(entry, entryResult);

            // Add runtime binding
            TInformationItem variable = entry.getVariable();
            if (variable != null) {
                String entryName = variable.getName();
                Object entryValue = Result.value(entryResult);
                expContext.bind(entryName, entryValue);
            } else {
                returnResult = entryResult;
            }
        }

        // Return result
        if (returnResult != null) {
            return returnResult;
        } else {
            // Make complex type value
            Context output = new Context();
            ContextType type = new ContextType();
            // Add entries
            for(TContextEntry entry: tContext.getContextEntry()) {
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String entryName = variable.getName();
                    output.add(entryName, expContext.lookupBinding(entryName));
                    String typeRef = variable.getTypeRef();
                    Type entryType;
                    if (StringUtils.isEmpty(typeRef)) {
                        Result entryResult = entryResultMap.get(entry);
                        entryType = Result.type(entryResult);
                    } else {
                        entryType = this.basicDMNTransformer.toFEELType(model, typeRef);
                    }
                    type.addMember(entryName, new ArrayList<>(), entryType);
                }
            }
            // Return value
            return Result.of(output, type);
        }
    }

    private Result evaluateListExpression(TDRGElement element, TList list, DMNContext context, DRGElement elementAnnotation) {
        TDefinitions model = this.dmnModelRepository.getModel(element);
        if (list.getExpression() == null) {
            return null;
        }
        List<Object> resultValue = new ArrayList<>();
        Type elementType = ANY;
        for(JAXBElement<? extends TExpression> expElement: list.getExpression()) {
            Result expResult;
            if (expElement == null) {
                expResult = null;
            } else {
                TExpression exp = expElement.getValue();
                expResult = evaluateExpression(element, exp, context, elementAnnotation);
                elementType = this.basicDMNTransformer.toFEELType(model, exp.getTypeRef());
            }
            resultValue.add(Result.value(expResult));
        }
        return Result.of(resultValue, new ListType(elementType));
    }

    private Result evaluateRelationExpression(TDRGElement element, TRelation relation, DMNContext context, DRGElement elementAnnotation) {
        if (relation.getRow() == null || relation.getColumn() == null) {
            return null;
        }

        // Make relation environment
        TDefinitions model = this.dmnModelRepository.getModel(element);
        DMNContext relationContext = DMNContext.of(
                element,
                this.basicDMNTransformer.makeRelationEnvironment(model, relation, context.getEnvironment()),
                context.getRuntimeEnvironment()
        );
        // Column names
        List<String> columnNameList = relation.getColumn().stream().map(TNamedElement::getName).collect(Collectors.toList());

        // Scan relation and evaluate each row
        List<Object> relationValue = new ArrayList<>();
        ContextType relationType = new ContextType();
        for(TList row: relation.getRow()) {
            Object rowValue = null;
            List<JAXBElement<? extends TExpression>> jaxbElementList = row.getExpression();
            if (jaxbElementList != null) {
                Context contextValue = new Context();
                ContextType contextType = new ContextType();
                for(int i = 0; i < jaxbElementList.size(); i++) {
                    JAXBElement<? extends TExpression> jaxbElement = jaxbElementList.get(i);
                    TExpression expression = jaxbElement == null ? null : jaxbElement.getValue();
                    Result columnResult = expression == null ? null : evaluateExpression(element, expression, relationContext, elementAnnotation);
                    Object columnValue = Result.value(columnResult);
                    contextValue.add(columnNameList.get(i), columnValue);
                    Type type = columnResult == null ? ANY : columnResult.getType();
                    contextType.addMember(columnNameList.get(i), new ArrayList<>(), type);
                    relationType = contextType;
                }
                rowValue = contextValue;
            }
            relationValue.add(rowValue);
        }
        return Result.of(relationValue, new ListType(relationType));
    }

    private Result evaluateFunctionDefinitionExpression(TDRGElement element, TFunctionDefinition expression, DMNContext context, DRGElement elementAnnotation) {
        Type type = this.basicDMNTransformer.expressionType(element, expression, context.getEnvironment());
        return Result.of(expression, type);
    }

    private Result evaluateDecisionTable(TDRGElement element, TDecisionTable decisionTable, DMNContext context, DRGElement elementAnnotation) {
        // Evaluate InputClauses
        List<InputClausePair> inputClauseList = new ArrayList<>();
        for (TInputClause inputClause : decisionTable.getInput()) {
            TLiteralExpression inputExpression = inputClause.getInputExpression();
            String inputExpressionText = inputExpression.getText();
            DMNContext expContext = DMNContext.of(
                    element,
                    context.getEnvironment(),
                    context.getRuntimeEnvironment()
            );
            Expression expression = this.feelInterpreter.analyzeExpression(inputExpressionText, expContext);
            Result inputExpressionResult = this.feelInterpreter.evaluateExpression(expression, expContext);
            Object inputExpressionValue = Result.value(inputExpressionResult);
            inputClauseList.add(new InputClausePair(expression, inputExpressionValue));
        }

        // Evaluate rules
        List<TDecisionRule> ruleList = decisionTable.getRule();
        RuleOutputList ruleOutputList = new RuleOutputList();
        for (int i = 0; i < ruleList.size(); i++) {
            TDecisionRule rule = ruleList.get(i);
            Rule ruleAnnotation = makeRuleAnnotation(rule, i);

            // Rule start
            EVENT_LISTENER.startRule(elementAnnotation, ruleAnnotation);

            InterpretedRuleOutput ruleOutput = evaluateRule(element, decisionTable, rule, inputClauseList, context, elementAnnotation, ruleAnnotation);
            ruleOutputList.add(ruleOutput);

            // Rule end
            EVENT_LISTENER.endRule(elementAnnotation, ruleAnnotation, ruleOutput);
        }

        // Return results based on hit policy
        Object value = applyHitPolicy(element, decisionTable, ruleOutputList, context, elementAnnotation);
        return Result.of(value, this.basicDMNTransformer.drgElementOutputFEELType(element));
    }

    private InterpretedRuleOutput evaluateRule(TDRGElement element, TDecisionTable decisionTable, TDecisionRule rule, List<InputClausePair> inputClauseList, DMNContext context, DRGElement elementAnnotation, Rule ruleAnnotation) {
        // Check tests
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        boolean ruleMatched = true;
        for (int index = 0; index < inputEntry.size(); index++) {
            TUnaryTests unaryTest = inputEntry.get(index);
            String text = unaryTest.getText();
            DMNContext testContext = DMNContext.of(
                    element,
                    this.basicDMNTransformer.makeInputEntryEnvironment(element, inputClauseList.get(index).getExpression()),
                    RuntimeEnvironment.of(inputClauseList, context.getRuntimeEnvironment(), index)
            );
            Expression ast = this.feelInterpreter.analyzeUnaryTests(text, testContext);
            Result result = this.feelInterpreter.evaluateUnaryTests((UnaryTests) ast, testContext);
            Object testMatched = Result.value(result);
            if (isFalse(testMatched)) {
                ruleMatched = false;
                break;
            }
        }

        // Compute output
        if (ruleMatched) {
            // Rule match
            EVENT_LISTENER.matchRule(elementAnnotation, ruleAnnotation);

            THitPolicy hitPolicy = decisionTable.getHitPolicy();
            if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                Context output = new Context();
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                for (int i = 0; i < outputEntry.size(); i++) {
                    TLiteralExpression literalExpression = outputEntry.get(i);
                    String key = decisionTable.getOutput().get(i).getName();
                    Result result = evaluateLiteralExpression(element, literalExpression, context, elementAnnotation);
                    Object value = Result.value(result);
                    if (this.dmnModelRepository.isOutputOrderHit(hitPolicy)) {
                        Object priority = this.basicDMNTransformer.priority(element, rule.getOutputEntry().get(i), i);
                        output.put(key, new Pair<>(value, priority));
                    } else {
                        output.put(key, new Pair<>(value, null));
                    }
                }
                return new InterpretedRuleOutput(ruleMatched, output);
            } else {
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                TLiteralExpression literalExpression = outputEntry.get(0);
                Object output;
                Result result = evaluateLiteralExpression(element, literalExpression, context, elementAnnotation);
                Object value = Result.value(result);
                if (this.dmnModelRepository.isOutputOrderHit(hitPolicy)) {
                    Object priority = this.basicDMNTransformer.priority(element, rule.getOutputEntry().get(0), 0);
                    output = new Pair<>(value, priority);
                } else {
                    output = new Pair<>(value, null);
                }
                return new InterpretedRuleOutput(ruleMatched, output);
            }
        } else {
            return new InterpretedRuleOutput(ruleMatched, null);
        }
    }

    private boolean isFalse(Object o) {
        if (o instanceof List) {
            if (((List<Object>) o).stream().anyMatch(t -> t == null || Boolean.FALSE.equals(o))) {
                return false;
            }
        }
        return o == null || Boolean.FALSE.equals(o);
    }

    private Object applyHitPolicy(TDRGElement element, TDecisionTable decisionTable, RuleOutputList ruleOutputList, DMNContext context, DRGElement elementAnnotation) {
        if (ruleOutputList.noMatchedRules()) {
            return evaluateDefaultValue(element, decisionTable, this.basicDMNTransformer, context, elementAnnotation);
        } else {
            THitPolicy hitPolicy = decisionTable.getHitPolicy();
            if (this.dmnModelRepository.isSingleHit(hitPolicy)) {
                InterpretedRuleOutput ruleOutput = (InterpretedRuleOutput) ruleOutputList.applySingle(HitPolicy.fromValue(hitPolicy.value()));
                return toDecisionOutput(element, decisionTable, ruleOutput);
            } else if (this.dmnModelRepository.isMultipleHit(hitPolicy)) {
                List<? extends RuleOutput> ruleOutputs = ruleOutputList.applyMultiple(HitPolicy.fromValue(hitPolicy.value()));
                if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                    if (this.dmnModelRepository.hasAggregator(decisionTable)) {
                        return null;
                    } else {
                        return ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    }
                } else {
                    List decisionOutput = ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    if (this.dmnModelRepository.hasAggregator(decisionTable)) {
                        TBuiltinAggregator aggregation = decisionTable.getAggregation();
                        if (aggregation == TBuiltinAggregator.MIN) {
                            return this.feelLib.min(decisionOutput);
                        } else if (aggregation == TBuiltinAggregator.MAX) {
                            return this.feelLib.max(decisionOutput);
                        } else if (aggregation == TBuiltinAggregator.COUNT) {
                            return this.feelLib.number(String.format("%d", decisionOutput.size()));
                        } else if (aggregation == TBuiltinAggregator.SUM) {
                            return this.feelLib.sum(decisionOutput);
                        } else {
                            throw new UnsupportedOperationException(String.format("Not supported '%s' aggregation.", aggregation));
                        }
                    } else {
                        return decisionOutput;
                    }
                }
            } else {
                throw new UnsupportedOperationException(String.format("Hit policy '%s' not supported ", hitPolicy));
            }
        }
    }

    private Object evaluateDefaultValue(TDRGElement element, TDecisionTable decisionTable, BasicDMNToNativeTransformer dmnTransformer, DMNContext context, DRGElement elementAnnotation) {
        if (this.dmnModelRepository.hasDefaultValue(decisionTable)) {
            // Evaluate and collect default values
            List<TOutputClause> outputClauses = decisionTable.getOutput();
            Context defaultValue = new Context();
            for (TOutputClause output : outputClauses) {
                TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
                String key = this.dmnModelRepository.outputClauseName(element, output);
                if (defaultOutputEntry == null) {
                    defaultValue.put(key, null);
                } else {
                    Result result = evaluateLiteralExpression(element, defaultOutputEntry, context, elementAnnotation);
                    Object value = Result.value(result);
                    defaultValue.put(key, value);
                }
            }
            // Return result
            if (this.dmnModelRepository.isCompoundDecisionTable(element)) {
                if (dmnTransformer.hasListType(element)) {
                    return Arrays.asList(defaultValue);
                } else {
                    return defaultValue;
                }
            } else {
                String key = this.dmnModelRepository.outputClauseName(element, decisionTable.getOutput().get(0));
                return defaultValue.get(key);
            }
        } else {
            return null;
        }
    }

    private Object toDecisionOutput(TDRGElement element, TDecisionTable decisionTable, InterpretedRuleOutput ruleOutput) {
        if (ruleOutput == null) {
            return null;
        }
        Object result = ruleOutput.getResult();
        // Compound decision
        if (result instanceof Context) {
            Context newContext = new Context();
            for (Object key : ((Context) result).keySet()) {
                newContext.put(key, ((Pair) ((Context) result).get(key)).getLeft());
            }
            return newContext;
        // Simple decision
        } else if (result instanceof Pair) {
            return ((Pair) result).getLeft();
        } else {
            return result;
        }
    }

    //
    // Binding
    //
    private Object lookupBinding(DMNContext context, DRGElementReference<? extends TDRGElement> reference) {
        if (this.basicDMNTransformer.isSingletonInputData()) {
            Object value = context.lookupBinding(this.basicDMNTransformer.bindingName(reference));
            return value;
        } else {
            ImportPath importPath = reference.getImportPath();
            String name = reference.getElementName();

            if (ImportPath.isEmpty(importPath)) {
                return context.lookupBinding(name);
            } else {
                List<String> pathElements = importPath.getPathElements();
                // Lookup root context
                String rootName = pathElements.get(0);
                Object obj = context.lookupBinding(rootName);
                if (obj instanceof Context) {
                    // Lookup inner contexts
                    Context parentContext = (Context) obj;
                    for (int i = 1; i < pathElements.size(); i++) {
                        String childName = pathElements.get(i);
                        Context childContext = (Context) parentContext.get(childName);
                        if (childContext == null) {
                            childContext = new Context();
                            parentContext.put(childName, childContext);
                        }
                        parentContext = childContext;
                    }
                    // lookup name
                    return parentContext.get(name);
                } else {
                    throw new DMNRuntimeException(String.format("Context value expected, found '%s'", obj.getClass().getSimpleName()));
                }
            }
        }
    }

    private void bind(DMNContext context, DRGElementReference<? extends TDRGElement> reference, Object value) {
        if (this.basicDMNTransformer.isSingletonInputData()) {
            context.bind(this.basicDMNTransformer.bindingName(reference), value);
        } else {
            ImportPath importPath = reference.getImportPath();
            String name = reference.getElementName();

            if (ImportPath.isEmpty(importPath)) {
                context.bind(name, value);
            } else {
                try {
                    List<String> pathElements = importPath.getPathElements();
                    // lookup or bind root context
                    String rootName = pathElements.get(0);
                    Context parentContext = (Context) context.lookupBinding(rootName);
                    if (parentContext == null) {
                        parentContext = new Context();
                        context.bind(rootName, parentContext);
                    }
                    // lookup or bind inner contexts
                    for (int i = 1; i < pathElements.size(); i++) {
                        String childName = pathElements.get(i);
                        Context childContext = (Context) parentContext.get(childName);
                        if (childContext == null) {
                            childContext = new Context();
                            parentContext.put(childName, childContext);
                        }
                        parentContext = childContext;
                    }
                    // bind name -> value
                    parentContext.put(name, value);
                } catch (Exception e) {
                    throw new DMNRuntimeException(String.format("cannot bind value to '%s.%s'", importPath.asString(), name), e);
                }
            }
        }
    }

    // Add binding required by parent: importName.elementName
    private void addBinding(DMNContext context, DRGElementReference<? extends TDRGElement> reference, String importName) {
        if (this.basicDMNTransformer.isSingletonInputData()) {
            String name = reference.getElementName();
            Object value = lookupBinding(context, reference);
            if (ImportPath.isEmpty(importName)) {
                context.bind(name, value);
            } else {
                // Lookup / bind import name
                Context parentContext = (Context) context.lookupBinding(importName);
                if (parentContext == null) {
                    parentContext = new Context();
                    context.bind(importName, parentContext);
                }
                // bind name -> value
                parentContext.put(name, value);
            }
        } else {
            ImportPath importPath = reference.getImportPath();
            if (!ImportPath.isEmpty(importPath)) {
                Object value = lookupBinding(context, reference);
                if (ImportPath.isEmpty(importName)) {
                    context.bind(reference.getElementName(), value);
                } else {
                    bind(context, this.dmnModelRepository.makeDRGElementReference(new ImportPath(importName), reference.getElement()), value);
                }
            }
        }
    }

    //
    // Logging
    //
    protected DRGElement makeDRGElementAnnotation(TDRGElement element) {
        return new DRGElement(null,
                this.dmnModelRepository.name(element),
                this.dmnModelRepository.label(element),
                this.basicDMNTransformer.elementKind(element),
                this.basicDMNTransformer.expressionKind(element),
                this.basicDMNTransformer.hitPolicy(element),
                this.dmnModelRepository.rulesCount(element)
        );
    }

    private Arguments makeArguments(TDRGElement element, DMNContext context) {
        Arguments arguments = new Arguments();
        DRGElementReference<? extends TDRGElement> reference = this.dmnModelRepository.makeDRGElementReference(element);
        List<String> parameters = this.basicDMNTransformer.drgElementArgumentDisplayNameList(reference);
        parameters.forEach(p -> arguments.put(p, context.lookupBinding(p)));
        return arguments;
    }

    private Rule makeRuleAnnotation(TDecisionRule rule, int ruleIndex) {
        return new Rule(ruleIndex, this.basicDMNTransformer.annotationEscapedText(rule));
    }
}
