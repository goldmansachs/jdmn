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
import com.gs.dmn.ImportPath;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.LogErrorHandler;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.interpreter.FEELInterpreterImpl;
import com.gs.dmn.feel.interpreter.TypeConverter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.*;
import com.gs.dmn.runtime.annotation.HitPolicy;
import com.gs.dmn.runtime.function.DMNFunction;
import com.gs.dmn.runtime.function.DMNInvocable;
import com.gs.dmn.runtime.function.Function;
import com.gs.dmn.runtime.listener.EventListener;
import com.gs.dmn.runtime.listener.*;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
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

    private final DMNModelRepository repository;
    private final EnvironmentFactory environmentFactory;
    protected final ErrorHandler errorHandler;

    private final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    protected final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib;
    private final FEELInterpreter<Type, DMNContext> feelInterpreter;
    private final TypeConverter typeConverter;

    public StandardDMNInterpreter(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib, TypeConverter typeConverter) {
        this.errorHandler = new LogErrorHandler(LOGGER);
        this.typeConverter = typeConverter;
        this.dmnTransformer = dmnTransformer;
        this.repository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.feelLib = feelLib;
        this.feelInterpreter = new FEELInterpreterImpl<>(this);
    }

    @Override
    public BasicDMNToNativeTransformer<Type, DMNContext> getBasicDMNTransformer() {
        return this.dmnTransformer;
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
    // Evaluate DRG elements
    //
    @Override
    public Result evaluateDecision(String namespace, String decisionName, Map<String, Object> informationRequirements) {
        try {
            TDRGElement element = this.repository.findDRGElementByName(namespace, decisionName);
            if (element instanceof TDecision) {
                // Infer types for InputData
                Map<TInputData, Pair<String, String>> inferredTypes = inferInputDataType(element, informationRequirements);
                // Make context
                DRGElementReference<TDecision> reference = this.repository.makeDRGElementReference((TDecision) element);
                DMNContext decisionContext = makeDecisionGlobalContext(element, informationRequirements);
                // Evaluate decision
                Result result = evaluateDecision(reference, decisionContext);
                // Restore original types
                restoreOriginalTypes(inferredTypes);
                return result;
            } else {
                throw new DMNRuntimeException(String.format("Cannot find decision namespace='%s' name='%s'", namespace, decisionName));
            }
        } catch (Exception e) {
            return handleEvaluationError(namespace, "decision", decisionName, e);
        }
    }

    private Map<TInputData, Pair<String, String>> inferInputDataType(TDRGElement element, Map<String, Object> informationRequirements) {
        Map<TInputData, Pair<String, String>> result = new LinkedHashMap<>();
        if (!this.dmnTransformer.isStrongTyping()) {
            TDefinitions model = this.repository.getModel(element);
            for (Map.Entry<String, Object> entry: informationRequirements.entrySet()) {
                String inputDataName = entry.getKey();
                Object value = entry.getValue();
                TDRGElement drgElementByName = findInputData(model, inputDataName);
                if (drgElementByName instanceof TInputData) {
                    TInputData inputData = (TInputData) drgElementByName;
                    TInformationItem variable = inputData.getVariable();
                    String originalTypeRef = variable.getTypeRef();
                    if (Type.isNullOrAny(originalTypeRef)) {
                        String inferredType = null;
                        if (feelLib.isNumber(value)) {
                            inferredType = NumberType.NUMBER.getName();
                        } else if (feelLib.isBoolean(value)) {
                            inferredType = BooleanType.BOOLEAN.getName();
                        } else if (feelLib.isString(value)) {
                            inferredType = StringType.STRING.getName();
                        } else if (feelLib.isDate(value)) {
                            inferredType = DateType.DATE.getName();
                        } else if (feelLib.isTime(value)) {
                            inferredType = TimeType.TIME.getName();
                        } else if (feelLib.isDateTime(value)) {
                            inferredType = DateTimeType.DATE_AND_TIME.getName();
                        } else if (feelLib.isDaysAndTimeDuration(value)) {
                            inferredType = DurationType.DAYS_AND_TIME_DURATION.getName();
                        } else if (feelLib.isYearsAndMonthsDuration(value)) {
                            inferredType = DurationType.DAYS_AND_TIME_DURATION.getName();
                        }
                        if (inferredType != null) {
                            variable.setTypeRef(inferredType);
                            result.put(inputData, new Pair<>(originalTypeRef, inferredType));
                        }
                    }
                }
            }
        }
        return result;
    }

    private void restoreOriginalTypes(Map<TInputData, Pair<String, String>> inferredTypes) {
        if (!this.dmnTransformer.isStrongTyping()) {
            for(Map.Entry<TInputData, Pair<String, String>> entry: inferredTypes.entrySet()) {
                TInputData inputData = entry.getKey();
                String originalTypeRef = entry.getValue().getLeft();
                inputData.getVariable().setTypeRef(originalTypeRef);
            }
        }
    }

    private TDRGElement findInputData(TDefinitions model, String inputDataName) {
        try {
            return this.repository.findDRGElementByName(model, inputDataName);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Result evaluateInvocable(String namespace, String invocableName, List<Object> argList) {
        try {
            TDRGElement element = this.repository.findDRGElementByName(namespace, invocableName);
            if (element instanceof TInvocable) {
                // Make context
                DRGElementReference<? extends TInvocable> reference = this.repository.makeDRGElementReference((TInvocable) element);
                DMNContext invocableContext = makeInvocableGlobalContext(reference.getElement(), argList);
                // Evaluate invocable
                return evaluateInvocable(reference, invocableContext);
            } else {
                throw new DMNRuntimeException(String.format("Cannot find invocable namespace='%s' name='%s'", namespace, invocableName));
            }
        } catch (Exception e) {
            return handleEvaluationError(namespace, "invocable", invocableName, e);
        }
    }

    //
    // Evaluate DMN elements in context
    //
    @Override
    public Result evaluate(TInvocable invocable, List<Object> argList, DMNContext parentContext) {
        try {
            DRGElementReference<TInvocable> reference = this.repository.makeDRGElementReference(invocable);
            return evaluateInvocableInContext(reference, argList, parentContext);
        } catch (Exception e) {
            String errorMessage = String.format("Evaluation error in invocable '%s' in context of element of '%s'", invocable.getName(), parentContext.getElementName());
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    @Override
    public Result evaluate(TFunctionDefinition functionDefinition, List<Object> argList, DMNContext parentContext) {
        try {
            // Create new environments and bind parameters
            DMNContext functionContext = this.dmnTransformer.makeFunctionContext(functionDefinition, parentContext);
            List<TInformationItem> formalParameterList = functionDefinition.getFormalParameter();
            for (int i = 0; i < formalParameterList.size(); i++) {
                TInformationItem param = formalParameterList.get(i);
                String name = param.getName();
                Object value = argList.get(i);
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
            String errorMessage = String.format("Evaluation error in function definition in context of element '%s'", parentContext.getElementName());
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    protected Result evaluateDecisionInContext(DRGElementReference<TDecision> reference, DMNContext parentContext) {
        DMNContext decisionContext = this.dmnTransformer.makeGlobalContext(reference.getElement(), parentContext);
        return evaluateDecision(reference, decisionContext);
    }

    protected Result evaluateDecision(DRGElementReference<TDecision> reference, DMNContext decisionContext) {
        TDecision decision = reference.getElement();
        ImportPath importPath = reference.getImportPath();

        // Decision start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(decision);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(decision, decisionContext);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Check if has already been evaluated
        String decisionName = decision.getName();
        Result result;
        if (dagOptimisation() && decisionContext.isBound(decisionName)) {
            // Retrieve value from environment
            Object output = lookupBinding(decisionContext, reference);
            Type expectedType = this.dmnTransformer.drgElementOutputFEELType(decision, decisionContext);
            result = Result.of(output, expectedType);
        } else {
            // Evaluate dependencies
            evaluateInformationRequirementList(importPath, decision, decision.getInformationRequirement(), decisionContext);
            evaluateKnowledgeRequirements(importPath, decision, decision.getKnowledgeRequirement(), decisionContext);

            // Evaluate expression
            TExpression expression = this.repository.expression(decision);
            result = evaluateExpression(decision, expression, decisionContext, drgElementAnnotation);

            // Check value and apply implicit conversions
            Type expectedType = this.dmnTransformer.drgElementOutputFEELType(decision, decisionContext);
            result = this.typeConverter.convertResult(result, expectedType, this.feelLib);
        }

        // Decision end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, Result.value(result), (System.currentTimeMillis() - startTime_));

        return result;
    }

    private Result evaluateInvocableInContext(DRGElementReference<? extends TInvocable> reference, List<Object> argList, DMNContext parentContext) {
        TInvocable element = reference.getElement();
        if (element != null) {
            DMNContext invocableContext = makeInvocableGlobalContext(element, argList, parentContext);
            return evaluateInvocable(reference, invocableContext);
        } else {
            throw new DMNRuntimeException(String.format("Cannot evaluate invocable '%s'", reference));
        }
    }

    private Result evaluateInvocable(DRGElementReference<? extends TInvocable> reference, DMNContext invocableContext) {
        TInvocable element = reference.getElement();
        if (element instanceof TDecisionService) {
            return evaluateDecisionService((DRGElementReference<TDecisionService>) reference, invocableContext);
        } else if (element instanceof TBusinessKnowledgeModel) {
            return evaluateBKM((DRGElementReference<TBusinessKnowledgeModel>) reference, invocableContext);
        } else {
            throw new DMNRuntimeException(String.format("Not supported type '%s'", element.getClass().getSimpleName()));
        }
    }

    private Result evaluateBKM(DRGElementReference<TBusinessKnowledgeModel> reference, DMNContext bkmContext) {
        TBusinessKnowledgeModel bkm = reference.getElement();

        // BKM start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(bkm);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(bkm, bkmContext);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Execute function body
        TExpression expression = this.repository.expression(bkm);
        Result result = evaluateExpression(bkm, expression, bkmContext, drgElementAnnotation);

        // Check value and apply implicit conversions
        Type expectedType = this.dmnTransformer.drgElementOutputFEELType(bkm, bkmContext);
        result = this.typeConverter.convertResult(result, expectedType, this.feelLib);
        Object value = Result.value(result);

        // BKM end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, value, (System.currentTimeMillis() - startTime_));

        return result;
    }

    private Result evaluateDecisionService(DRGElementReference<TDecisionService> serviceReference, DMNContext serviceContext) {
        TDecisionService service = serviceReference.getElement();

        // Decision Service start
        long startTime_ = System.currentTimeMillis();
        DRGElement drgElementAnnotation = makeDRGElementAnnotation(service);
        com.gs.dmn.runtime.listener.Arguments decisionArguments = makeArguments(service, serviceContext);
        EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

        // Evaluate output decisions
        List<TDecision> outputDecisions = new ArrayList<>();
        List<Result> results = new ArrayList<>();
        for (TDMNElementReference outputDecisionReference: service.getOutputDecision()) {
            TDecision decision = this.repository.findDecisionByRef(service, outputDecisionReference.getHref());
            outputDecisions.add(decision);

            String importName = this.repository.findImportName(service, outputDecisionReference);
            ImportPath decisionImportPath = new ImportPath(serviceReference.getImportPath(), importName);
            Result result = evaluateDecisionInContext(this.repository.makeDRGElementReference(decisionImportPath, decision), serviceContext);
            results.add(result);
        }

        // Make context result
        Object output;
        if (outputDecisions.size() == 1) {
            output = Result.value(results.get(0));
        } else {
            output = new Context();
            for(int i=0; i < outputDecisions.size(); i++) {
                TDecision decision = outputDecisions.get(i);
                Object value = Result.value(results.get(i));
                ((Context) output).add(decision.getName(), value);
            }
        }

        // Check value and apply implicit conversions
        Type expectedType = this.dmnTransformer.drgElementOutputFEELType(service, serviceContext);
        Result result = this.typeConverter.convertValue(output, expectedType, this.feelLib);
        output = Result.value(result);

        // Decision service end
        EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, output, (System.currentTimeMillis() - startTime_));

        return Result.of(output, this.dmnTransformer.drgElementOutputFEELType(service));
    }

    private void evaluateKnowledgeRequirements(ImportPath importPath, TDRGElement parent, List<TKnowledgeRequirement> knowledgeRequirementList, DMNContext context) {
        for (TKnowledgeRequirement requirement : knowledgeRequirementList) {
            // Find invocable
            TDMNElementReference requiredKnowledge = requirement.getRequiredKnowledge();
            String href = requiredKnowledge.getHref();
            TInvocable invocable = this.repository.findInvocableByRef(parent, href);

            // Calculate import path
            String importName = this.repository.findImportName(parent, requiredKnowledge);
            ImportPath invocableImportPath = new ImportPath(importPath, importName);

            // Evaluate invocable
            if (invocable instanceof TBusinessKnowledgeModel) {
                applyBKM(this.repository.makeDRGElementReference(invocableImportPath, (TBusinessKnowledgeModel) invocable), context);
            } else if (invocable instanceof TDecisionService) {
                applyDecisionService(this.repository.makeDRGElementReference(invocableImportPath, (TDecisionService) invocable), context);
            } else {
                throw new DMNRuntimeException(String.format("Not supported invocable '%s'", invocable.getClass().getSimpleName()));
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
        Type type = dmnTransformer.drgElementVariableFEELType(bkm);
        bind(context, reference, DMNInvocable.of(bkm, type, context));
    }

    private void applyDecisionService(DRGElementReference<TDecisionService> reference, DMNContext context) {
        TDecisionService service = reference.getElement();

        // Bind name to DMN definition
        Type type = dmnTransformer.drgElementVariableFEELType(service);
        bind(context, reference, DMNInvocable.of(service, type, context));
    }

    protected boolean dagOptimisation() {
        return true;
    }

    private void evaluateInformationRequirementList(ImportPath importPath, TDRGElement parent, List<TInformationRequirement> informationRequirementList, DMNContext context) {
        for (TInformationRequirement informationRequirement : informationRequirementList) {
            TDMNElementReference requiredInput = informationRequirement.getRequiredInput();
            TDMNElementReference requiredDecision = informationRequirement.getRequiredDecision();
            if (requiredInput != null) {
                TInputData child = this.repository.findInputDataByRef(parent, requiredInput.getHref());
                String importName = this.repository.findImportName(parent, requiredInput);
                ImportPath childImportPath = new ImportPath(importPath, importName);

                // Add new binding to match path in parent
                addBinding(context, this.repository.makeDRGElementReference(childImportPath, child), importName);
            } else if (requiredDecision != null) {
                TDecision child = this.repository.findDecisionByRef(parent, requiredDecision.getHref());
                String importName = this.repository.findImportName(parent, requiredDecision);
                ImportPath childImportPath = new ImportPath(importPath, importName);
                DRGElementReference<TDecision> reference = this.repository.makeDRGElementReference(childImportPath, child);
                Result result = evaluateDecisionInContext(reference, context);

                // Add new binding to match path in parent
                addBinding(context, this.repository.makeDRGElementReference(childImportPath, child), importName, Result.value(result));
            } else {
                this.errorHandler.reportError("Incorrect InformationRequirement. Missing required input and decision");
            }
        }
    }

    //
    // Context
    //
    private DMNContext makeDecisionGlobalContext(TDRGElement element, Map<String, Object> informationRequirements) {
        // No caching when is not strongly typed, input data types might be inferred from input data
        boolean strongTyping = dmnTransformer.isStrongTyping();
        DMNContext globalContext = strongTyping ? this.dmnTransformer.makeGlobalContext(element) : this.dmnTransformer.makeGlobalContext(element, true);
        for (Map.Entry<String, Object> entry: informationRequirements.entrySet()) {
            globalContext.bind(entry.getKey(), entry.getValue());
        }
        return globalContext;
    }

    private DMNContext makeInvocableGlobalContext(TInvocable invocable, List<Object> argList, DMNContext parentContext) {
        DMNContext invocableContext = this.dmnTransformer.makeGlobalContext(invocable, parentContext);
        bindArguments(invocable, argList, invocableContext);
        return invocableContext;
    }

    private DMNContext makeInvocableGlobalContext(TInvocable invocable, List<Object> argList) {
        DMNContext invocableContext = this.dmnTransformer.makeGlobalContext(invocable);
        bindArguments(invocable, argList, invocableContext);
        return invocableContext;
    }

    private void bindArguments(TInvocable invocable, List<Object> argList, DMNContext invocableContext) {
        if (invocable instanceof TBusinessKnowledgeModel) {
            bindArguments((TBusinessKnowledgeModel) invocable, argList, invocableContext);
        } else if (invocable instanceof TDecisionService) {
            bindArguments((TDecisionService) invocable, argList, invocableContext);
        } else {
            throw new DMNRuntimeException(String.format("Not supported invocable '%s'", invocable.getClass().getSimpleName()));
        }
    }

    private void bindArguments(TBusinessKnowledgeModel bkm, List<Object> argList, DMNContext bkmContext) {
        TDefinitions model = this.repository.getModel(bkm);
        // Bind parameters
        List<TInformationItem> formalParameterList = bkm.getEncapsulatedLogic().getFormalParameter();
        for (int i = 0; i < formalParameterList.size(); i++) {
            TInformationItem param = formalParameterList.get(i);
            String name = param.getName();
            String paramTypeRef = param.getTypeRef();
            Type paramType = null;
            if (!StringUtils.isEmpty(paramTypeRef)) {
                paramType = this.dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, paramTypeRef));
            }
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = this.typeConverter.convertValue(value, paramType, this.feelLib);
            value = Result.value(result);

            // Declaration is already in environment
            bkmContext.bind(name, value);
        }
    }

    private void bindArguments(TDecisionService service, List<Object> argList, DMNContext serviceContext) {
        // Bind parameters
        List<FormalParameter<Type, DMNContext>> formalParameterList = this.dmnTransformer.dsFEELParameters(service);
        for (int i = 0; i < formalParameterList.size(); i++) {
            FormalParameter<Type, DMNContext> param = formalParameterList.get(i);
            String name = param.getName();
            Type type = param.getType();
            Object value = argList.get(i);

            // Check value and apply implicit conversions
            Result result = this.typeConverter.convertValue(value, type, this.feelLib);
            value = Result.value(result);

            // Variable declaration already exists
            serviceContext.bind(name, value);
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
        return this.feelInterpreter.evaluateExpression(text, context);
    }

    private Result evaluateInvocationExpression(TDRGElement element, TInvocation invocation, DMNContext parentContext, DRGElement elementAnnotation) {
        // Compute name-java binding for arguments
        Map<String, Object> argBinding = new LinkedHashMap<>();
        for(TBinding binding: invocation.getBinding()) {
            String argName = binding.getParameter().getName();
            TExpression argExpression = binding.getExpression().getValue();
            Result argResult = evaluateExpression(element, argExpression, parentContext, elementAnnotation);
            Object argJava = Result.value(argResult);
            argBinding.put(argName, argJava);
        }

        // Evaluate body
        TExpression body = invocation.getExpression().getValue();
        if (body instanceof TLiteralExpression) {
            // Find BKM
            String bkmName = ((TLiteralExpression) body).getText();
            TBusinessKnowledgeModel bkm = this.repository.findKnowledgeModelByName(bkmName);
            if (bkm == null) {
                throw new DMNRuntimeException(String.format("Cannot find BKM for '%s'", bkmName));
            }
            // Make args
            List<Object> argList = new ArrayList<>();
            List<String> formalParameterList = this.dmnTransformer.bkmFEELParameterNames(bkm);
            for(String paramName: formalParameterList) {
                if (argBinding.containsKey(paramName)) {
                    Object argValue = argBinding.get(paramName);
                    argList.add(argValue);
                } else {
                    throw new DMNRuntimeException(String.format("Cannot find binding for parameter '%s'", paramName));
                }
            }

            // Evaluate invocation
            DRGElementReference<TBusinessKnowledgeModel> reference = this.repository.makeDRGElementReference(bkm);
            return evaluateInvocableInContext(reference, argList, parentContext);
        } else {
            throw new DMNRuntimeException(String.format("Not supported '%s'", body.getClass().getSimpleName()));
        }
    }

    private Result evaluateContextExpression(TDRGElement element, TContext context, DMNContext parentContext, DRGElement elementAnnotation) {
        TDefinitions model = this.repository.getModel(element);

        // Make entry context
        Pair<DMNContext, Map<TContextEntry, Expression<Type, DMNContext>>> pair = this.dmnTransformer.makeContextEnvironment(element, context, parentContext);
        DMNContext localContext = pair.getLeft();

        // Evaluate entries
        Result returnResult = null;
        Map<TContextEntry, Result> entryResultMap = new LinkedHashMap<>();
        Map<TContextEntry, Expression<Type, DMNContext>> literalExpressionMap = pair.getRight();
        for(TContextEntry entry: context.getContextEntry()) {
            // Evaluate entry value
            Result entryResult;
            JAXBElement<? extends TExpression> jaxbElement = entry.getExpression();
            if (jaxbElement != null) {
                TExpression expression = jaxbElement.getValue();
                if (expression instanceof TLiteralExpression) {
                    Expression<Type, DMNContext> feelExpression = literalExpressionMap.get(entry);
                    entryResult = this.feelInterpreter.evaluateExpression(feelExpression, localContext);
                } else {
                    entryResult = evaluateExpression(element, expression, localContext, elementAnnotation);
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
                localContext.bind(entryName, entryValue);
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
            for(TContextEntry entry: context.getContextEntry()) {
                TInformationItem variable = entry.getVariable();
                if (variable != null) {
                    String entryName = variable.getName();
                    output.add(entryName, localContext.lookupBinding(entryName));
                    String typeRef = variable.getTypeRef();
                    Type entryType;
                    if (StringUtils.isEmpty(typeRef)) {
                        Result entryResult = entryResultMap.get(entry);
                        entryType = Result.type(entryResult);
                    } else {
                        entryType = this.dmnTransformer.toFEELType(model, typeRef);
                    }
                    type.addMember(entryName, new ArrayList<>(), entryType);
                }
            }
            // Return value
            return Result.of(output, type);
        }
    }

    private Result evaluateListExpression(TDRGElement element, TList list, DMNContext context, DRGElement elementAnnotation) {
        TDefinitions model = this.repository.getModel(element);
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
                String typeRef = exp.getTypeRef();
                if (!this.repository.isNull(typeRef)) {
                    elementType = this.dmnTransformer.toFEELType(model, typeRef);
                } else {
                    elementType = this.dmnTransformer.expressionType(element, exp, context);
                }
            }
            resultValue.add(Result.value(expResult));
        }
        return Result.of(resultValue, new ListType(elementType));
    }

    private Result evaluateRelationExpression(TDRGElement element, TRelation relation, DMNContext parentContext, DRGElement elementAnnotation) {
        if (relation.getRow() == null || relation.getColumn() == null) {
            return null;
        }

        // Make relation environment
        DMNContext relationContext = this.dmnTransformer.makeRelationContext(element, relation, parentContext);
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
        Type type = this.dmnTransformer.expressionType(element, expression, context);
        Function function = DMNFunction.of(expression, type, context);
        return Result.of(function, type);
    }

    private Result evaluateDecisionTable(TDRGElement element, TDecisionTable decisionTable, DMNContext context, DRGElement elementAnnotation) {
        // Evaluate InputClauses
        List<InputClausePair> inputClauseList = new ArrayList<>();
        for (TInputClause inputClause : decisionTable.getInput()) {
            TLiteralExpression inputExpression = inputClause.getInputExpression();
            String inputExpressionText = inputExpression.getText();
            Expression<Type, DMNContext> expression = this.feelInterpreter.analyzeExpression(inputExpressionText, context);
            Result inputExpressionResult = this.feelInterpreter.evaluateExpression(expression, context);
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
        return Result.of(value, this.dmnTransformer.drgElementOutputFEELType(element));
    }

    private InterpretedRuleOutput evaluateRule(TDRGElement element, TDecisionTable decisionTable, TDecisionRule rule, List<InputClausePair> inputClauseList, DMNContext context, DRGElement elementAnnotation, Rule ruleAnnotation) {
        // Check tests
        List<TUnaryTests> inputEntry = rule.getInputEntry();
        boolean ruleMatched = true;
        for (int index = 0; index < inputEntry.size(); index++) {
            TUnaryTests unaryTest = inputEntry.get(index);
            String text = unaryTest.getText();
            Expression<Type, DMNContext> inputExpression = inputClauseList.get(index).getExpression();
            DMNContext testContext = this.dmnTransformer.makeUnaryTestContext(inputExpression, context);
            testContext.bind(DMNContext.INPUT_ENTRY_PLACE_HOLDER, inputClauseList.get(index).getValue());
            Expression<Type, DMNContext> ast = this.feelInterpreter.analyzeUnaryTests(text, testContext);
            Result result = this.feelInterpreter.evaluateUnaryTests((UnaryTests<Type, DMNContext>) ast, testContext);
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
            if (this.repository.isCompoundDecisionTable(element)) {
                Context output = new Context();
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                for (int i = 0; i < outputEntry.size(); i++) {
                    TLiteralExpression literalExpression = outputEntry.get(i);
                    String key = decisionTable.getOutput().get(i).getName();
                    Result result = evaluateLiteralExpression(element, literalExpression, context, elementAnnotation);
                    Object value = Result.value(result);
                    if (this.repository.isOutputOrderHit(hitPolicy)) {
                        Object priority = this.dmnTransformer.priority(element, rule.getOutputEntry().get(i), i);
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
                if (this.repository.isOutputOrderHit(hitPolicy)) {
                    Object priority = this.dmnTransformer.priority(element, rule.getOutputEntry().get(0), 0);
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
            return evaluateDefaultValue(element, decisionTable, this.dmnTransformer, context, elementAnnotation);
        } else {
            THitPolicy hitPolicy = decisionTable.getHitPolicy();
            if (this.repository.isSingleHit(hitPolicy)) {
                InterpretedRuleOutput ruleOutput = (InterpretedRuleOutput) ruleOutputList.applySingle(HitPolicy.fromValue(hitPolicy.value()));
                return toDecisionOutput(element, decisionTable, ruleOutput);
            } else if (this.repository.isMultipleHit(hitPolicy)) {
                List<? extends RuleOutput> ruleOutputs = ruleOutputList.applyMultiple(HitPolicy.fromValue(hitPolicy.value()));
                if (this.repository.isCompoundDecisionTable(element)) {
                    if (this.repository.hasAggregator(decisionTable)) {
                        return null;
                    } else {
                        return ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    }
                } else {
                    List decisionOutput = ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                    if (this.repository.hasAggregator(decisionTable)) {
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
                            throw new DMNRuntimeException(String.format("Not supported '%s' aggregation.", aggregation));
                        }
                    } else {
                        return decisionOutput;
                    }
                }
            } else {
                throw new DMNRuntimeException(String.format("Hit policy '%s' not supported ", hitPolicy));
            }
        }
    }

    private Object evaluateDefaultValue(TDRGElement element, TDecisionTable decisionTable, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNContext context, DRGElement elementAnnotation) {
        if (this.repository.hasDefaultValue(decisionTable)) {
            // Evaluate and collect default values
            List<TOutputClause> outputClauses = decisionTable.getOutput();
            Context defaultValue = new Context();
            for (TOutputClause output : outputClauses) {
                TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
                String key = this.repository.outputClauseName(element, output);
                if (defaultOutputEntry == null) {
                    defaultValue.put(key, null);
                } else {
                    Result result = evaluateLiteralExpression(element, defaultOutputEntry, context, elementAnnotation);
                    Object value = Result.value(result);
                    defaultValue.put(key, value);
                }
            }
            // Return result
            if (this.repository.isCompoundDecisionTable(element)) {
                if (dmnTransformer.hasListType(element)) {
                    return Arrays.asList(defaultValue);
                } else {
                    return defaultValue;
                }
            } else {
                String key = this.repository.outputClauseName(element, decisionTable.getOutput().get(0));
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
        if (this.dmnTransformer.isSingletonInputData()) {
            return context.lookupBinding(this.dmnTransformer.bindingName(reference));
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
        if (this.dmnTransformer.isSingletonInputData()) {
            context.bind(this.dmnTransformer.bindingName(reference), value);
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
        Object value = lookupBinding(context, reference);
        addBinding(context, reference, importName, value);
    }

    // Add binding required by parent: importName.elementName
    private void addBinding(DMNContext context, DRGElementReference<? extends TDRGElement> reference, String importName, Object value) {
        if (this.dmnTransformer.isSingletonInputData()) {
            String name = reference.getElementName();
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
            if (ImportPath.isEmpty(importPath)) {
                context.bind(reference.getElementName(), value);
            } else {
                if (ImportPath.isEmpty(importName)) {
                    context.bind(reference.getElementName(), value);
                } else {
                    bind(context, this.repository.makeDRGElementReference(new ImportPath(importName), reference.getElement()), value);
                }
            }
        }
    }

    //
    // Logging
    //
    protected DRGElement makeDRGElementAnnotation(TDRGElement element) {
        return new DRGElement(null,
                this.repository.name(element),
                this.repository.label(element),
                this.dmnTransformer.elementKind(element),
                this.dmnTransformer.expressionKind(element),
                this.dmnTransformer.hitPolicy(element),
                this.repository.rulesCount(element)
        );
    }

    private Arguments makeArguments(TDRGElement element, DMNContext context) {
        Arguments arguments = new Arguments();
        DRGElementReference<? extends TDRGElement> reference = this.repository.makeDRGElementReference(element);
        List<String> parameters = this.dmnTransformer.drgElementArgumentDisplayNameList(reference);
        for (String p: parameters) {
            Object value = context.lookupBinding(p);
            arguments.put(p, value);
        }
        return arguments;
    }

    private Rule makeRuleAnnotation(TDecisionRule rule, int ruleIndex) {
        return new Rule(ruleIndex, this.dmnTransformer.annotationEscapedText(rule));
    }

    private Result handleEvaluationError(String namespace, String type, String name, Exception e) {
        String errorMessage = String.format("Cannot evaluate %s namespace='%s' name='%s'", type, namespace, name);
        this.errorHandler.reportError(errorMessage, e);
        Result result = Result.of(null, NullType.NULL);
        result.addError(errorMessage, e);
        return result;
    }
}
