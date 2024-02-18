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
import com.gs.dmn.ast.*;
import com.gs.dmn.ast.visitor.NopVisitor;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.el.analysis.semantics.type.NullType;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.error.ErrorHandler;
import com.gs.dmn.error.LogErrorHandler;
import com.gs.dmn.feel.analysis.semantics.type.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.FilterExpression;
import com.gs.dmn.feel.analysis.syntax.ast.test.UnaryTests;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.*;
import java.util.stream.Collectors;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;

public abstract class AbstractDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> implements DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDMNInterpreter.class);

    protected static EventListener EVENT_LISTENER = new LoggingEventListener(LOGGER);
    public static void setEventListener(EventListener eventListener) {
        EVENT_LISTENER = eventListener;
    }

    private final DMNModelRepository repository;
    private final EnvironmentFactory environmentFactory;
    protected final ErrorHandler errorHandler;

    protected final BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer;
    protected final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib;
    protected ELInterpreter<Type, DMNContext> elInterpreter;
    protected TypeChecker typeChecker;

    protected InterpreterVisitor visitor;

    public AbstractDMNInterpreter(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        this.errorHandler = new LogErrorHandler(LOGGER);
        this.dmnTransformer = dmnTransformer;
        this.repository = dmnTransformer.getDMNModelRepository();
        this.environmentFactory = dmnTransformer.getEnvironmentFactory();
        this.feelLib = feelLib;
        this.visitor = new InterpreterVisitor(this.errorHandler);
    }

    @Override
    public BasicDMNToNativeTransformer<Type, DMNContext> getBasicDMNTransformer() {
        return this.dmnTransformer;
    }

    @Override
    public ELInterpreter<Type, DMNContext> getElInterpreter() {
        return this.elInterpreter;
    }

    @Override
    public FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getFeelLib() {
        return this.feelLib;
    }

    @Override
    public TypeChecker getTypeChecker() {
        return this.typeChecker;
    }

    //
    // Evaluate DRG elements
    //
    @Override
    public Result evaluateDecision(String namespace, String decisionName, EvaluationContext context) {
        try {
            TDRGElement element = this.repository.findDRGElementByName(namespace, decisionName);
            if (element instanceof TDecision decision) {
                // Infer types for InputData
                Map<String, Object> informationRequirements = ((DecisionEvaluationContext) context).getInformationRequirements();
                Map<TInputData, Pair<String, String>> inferredTypes = inferInputDataType(element, informationRequirements);
                // Make context
                DRGElementReference<TDecision> reference = this.repository.makeDRGElementReference(decision);
                DMNContext decisionContext = makeDecisionGlobalContext(element, informationRequirements);
                // Evaluate decision
                Result result = this.visitor.visitDecisionReference(reference, decisionContext);
                // Restore original types
                restoreOriginalTypes(inferredTypes);
                return result;
            } else {
                throw new DMNRuntimeException("Cannot find decision namespace='%s' name='%s'".formatted(namespace, decisionName));
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
                if (drgElementByName instanceof TInputData inputData) {
                    TInformationItem variable = inputData.getVariable();
                    String originalTypeRef = QualifiedName.toName(variable.getTypeRef());
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
                            variable.setTypeRef(new QName(inferredType));
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
                inputData.getVariable().setTypeRef(originalTypeRef == null ? null : new QName(originalTypeRef));
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
    public Result evaluateInvocable(String namespace, String invocableName, EvaluationContext context) {
        List<Object> argList = ((FunctionInvocationContext) context).getArgList();
        try {
            TDRGElement element = this.repository.findDRGElementByName(namespace, invocableName);
            if (element instanceof TInvocable invocable) {
                // Make context
                DRGElementReference<? extends TInvocable> reference = this.repository.makeDRGElementReference(invocable);
                DMNContext invocableContext = makeInvocableGlobalContext(reference.getElement(), argList);
                // Evaluate invocable
                return this.visitor.visitInvocable(reference, invocableContext);
            } else {
                throw new DMNRuntimeException("Cannot find invocable namespace='%s' name='%s'".formatted(namespace, invocableName));
            }
        } catch (Exception e) {
            return handleEvaluationError(namespace, "invocable", invocableName, e);
        }
    }

    //
    // Evaluate DMN elements in context
    //
    @Override
    public Result evaluate(TInvocable invocable, EvaluationContext context) {
        List<Object> argList = ((FunctionInvocationContext) context).getArgList();
        DMNContext parentContext = ((FunctionInvocationContext) context).getContext();
        try {
            DRGElementReference<TInvocable> reference = this.repository.makeDRGElementReference(invocable);
            return this.visitor.visitInvocable(reference, makeInvocableGlobalContext(((DRGElementReference<? extends TInvocable>) reference).getElement(), argList, parentContext));
        } catch (Exception e) {
            String errorMessage = "Evaluation error in invocable '%s' in context of element of '%s'".formatted(invocable.getName(), parentContext.getElementName());
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    @Override
    public Result evaluate(TFunctionDefinition functionDefinition, EvaluationContext context) {
        List<Object> argList = ((FunctionInvocationContext) context).getArgList();
        DMNContext parentContext = ((FunctionInvocationContext) context).getContext();
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
            TExpression expression = functionDefinition.getExpression();
            Result output;
            if (expression == null) {
                output = null;
            } else {
                output = this.visitor.visit(expression, EvaluationContext.makeExpressionEvaluationContext(null, functionContext, null));
            }

            return output;
        } catch (Exception e) {
            String errorMessage = "Evaluation error in function definition in context of element '%s'".formatted(parentContext.getElementName());
            this.errorHandler.reportError(errorMessage, e);
            Result result = Result.of(null, NullType.NULL);
            result.addError(errorMessage, e);
            return result;
        }
    }

    private void evaluateKnowledgeRequirements(List<TKnowledgeRequirement> knowledgeRequirementList, ImportPath importPath, TDRGElement parent, DMNContext context) {
        for (TKnowledgeRequirement requirement : knowledgeRequirementList) {
            // Find invocable
            TDMNElementReference requiredKnowledge = requirement.getRequiredKnowledge();
            String href = requiredKnowledge.getHref();
            TInvocable invocable = this.repository.findInvocableByRef(parent, href);

            // Calculate import path
            String importName = this.repository.findImportName(parent, requiredKnowledge);
            ImportPath invocableImportPath = new ImportPath(importPath, importName);

            // Evaluate invocable
            if (invocable instanceof TBusinessKnowledgeModel model) {
                applyBKM(this.repository.makeDRGElementReference(invocableImportPath, model), context);
            } else if (invocable instanceof TDecisionService service) {
                applyDecisionService(this.repository.makeDRGElementReference(invocableImportPath, service), context);
            } else {
                throw new DMNRuntimeException("Not supported invocable '%s'".formatted(invocable.getClass().getSimpleName()));
            }
        }
    }

    private void applyBKM(DRGElementReference<TBusinessKnowledgeModel> reference, DMNContext context) {
        ImportPath importPath = reference.getImportPath();
        TBusinessKnowledgeModel bkm = reference.getElement();

        // Evaluate knowledge requirements
        List<TKnowledgeRequirement> knowledgeRequirement = bkm.getKnowledgeRequirement();
        evaluateKnowledgeRequirements(knowledgeRequirement, importPath, bkm, context);

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

    private void evaluateInformationRequirementList(List<TInformationRequirement> informationRequirementList, ImportPath importPath, TDRGElement parent, DMNContext context) {
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
                Result result = this.visitor.visitDecisionReference(reference, makeDecisionGlobalContext(reference, context));

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
    protected DMNContext makeDecisionGlobalContext(DRGElementReference<TDecision> reference, DMNContext parentContext) {
        return dmnTransformer.makeGlobalContext(reference.getElement(), parentContext);
    }

    private DMNContext makeDecisionGlobalContext(TDRGElement element, Map<String, Object> informationRequirements) {
        // No caching when is not strongly typed, input data types might be inferred from input data
        boolean strongTyping = dmnTransformer.isStrongTyping();
        DMNContext globalContext = strongTyping ? this.dmnTransformer.makeGlobalContext(element) : this.dmnTransformer.makeGlobalContext(element, true);
        for (Map.Entry<String, Object> entry: informationRequirements.entrySet()) {
            globalContext.bind(entry.getKey(), entry.getValue());
        }
        return globalContext;
    }

    protected DMNContext makeInvocableGlobalContext(TInvocable invocable, List<Object> argList, DMNContext parentContext) {
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
        if (invocable instanceof TBusinessKnowledgeModel model) {
            bindArguments(model, argList, invocableContext);
        } else if (invocable instanceof TDecisionService service) {
            bindArguments(service, argList, invocableContext);
        } else {
            throw new DMNRuntimeException("Not supported invocable '%s'".formatted(invocable.getClass().getSimpleName()));
        }
    }

    private void bindArguments(TBusinessKnowledgeModel bkm, List<Object> argList, DMNContext bkmContext) {
        TDefinitions model = this.repository.getModel(bkm);
        // Bind parameters
        List<TInformationItem> formalParameterList = bkm.getEncapsulatedLogic().getFormalParameter();
        for (int i = 0; i < formalParameterList.size(); i++) {
            TInformationItem param = formalParameterList.get(i);
            String name = param.getName();
            String paramTypeRef = QualifiedName.toName(param.getTypeRef());
            Type paramType = null;
            if (!StringUtils.isEmpty(paramTypeRef)) {
                paramType = this.dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, paramTypeRef));
            }
            Object value = argList.get(i);

            // Check argument
            Result result = this.typeChecker.checkArgument(value, paramType);
            value = Result.value(result);

            // Declaration is already in environment
            bkmContext.bind(name, value);
        }
    }

    private void bindArguments(TDecisionService service, List<Object> argList, DMNContext serviceContext) {
        // Bind parameters
        List<FormalParameter<Type>> formalParameterList = this.dmnTransformer.dsFEELParameters(service);
        for (int i = 0; i < formalParameterList.size(); i++) {
            FormalParameter<Type> param = formalParameterList.get(i);
            String name = param.getName();
            Type type = param.getType();
            Object value = argList.get(i);

            // Check argument
            Result result = this.typeChecker.checkArgument(value, type);
            value = Result.value(result);

            // Variable declaration already exists
            serviceContext.bind(name, value);
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
                if (obj instanceof Context parentContext) {
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
                    throw new DMNRuntimeException("Context value expected, found '%s'".formatted(obj.getClass().getSimpleName()));
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
                    throw new DMNRuntimeException("cannot bind value to '%s.%s'".formatted(importPath.asString(), name), e);
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
    // Expression evaluation
    //
    protected Result evaluateLiteralExpression(String text, DMNContext context) {
        return elInterpreter.evaluateExpression(text, context);
    }

    protected class InterpreterVisitor extends NopVisitor<EvaluationContext, Result> implements ReferenceVisitor<DMNContext, Result> {
        public InterpreterVisitor(ErrorHandler errorHandler) {
            super(errorHandler);
        }

        @Override
        public Result visitDecisionReference(DRGElementReference<TDecision> reference, DMNContext decisionContext) {
            TDecision decision = reference.getElement();
            ImportPath importPath = reference.getImportPath();

            // Decision start
            long startTime_ = System.currentTimeMillis();
            DRGElement drgElementAnnotation = makeDRGElementAnnotation(decision);
            Arguments decisionArguments = makeArguments(decision, decisionContext);
            EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

            // Check if has already been evaluated
            String decisionName = decision.getName();
            Result result;
            if (dagOptimisation() && decisionContext.isBound(decisionName)) {
                // Retrieve value from environment
                Object output = lookupBinding(decisionContext, reference);
                Type expectedType = dmnTransformer.drgElementOutputFEELType(decision, decisionContext);
                result = Result.of(output, expectedType);
            } else {
                // Evaluate dependencies
                evaluateInformationRequirementList(decision.getInformationRequirement(), importPath, decision, decisionContext);
                evaluateKnowledgeRequirements(decision.getKnowledgeRequirement(), importPath, decision, decisionContext);

                // Evaluate expression
                TExpression expression = repository.expression(decision);
                result = this.visit(expression, EvaluationContext.makeExpressionEvaluationContext(decision, decisionContext, drgElementAnnotation));

                // Check result
                Type expectedType = dmnTransformer.drgElementOutputFEELType(decision, decisionContext);
                result = typeChecker.checkResult(result, expectedType);
            }

            // Decision end
            EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, Result.value(result), (System.currentTimeMillis() - startTime_));

            return result;
        }

        @Override
        public Result visitBKMReference(DRGElementReference<TBusinessKnowledgeModel> reference, DMNContext bkmContext) {
            TBusinessKnowledgeModel bkm = reference.getElement();

            // BKM start
            long startTime_ = System.currentTimeMillis();
            DRGElement drgElementAnnotation = makeDRGElementAnnotation(bkm);
            Arguments decisionArguments = makeArguments(bkm, bkmContext);
            EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

            // Execute function body
            TExpression expression = repository.expression(bkm);
            Result result = this.visit(expression, EvaluationContext.makeExpressionEvaluationContext(bkm, bkmContext, drgElementAnnotation));

            // Check result
            Type expectedType = dmnTransformer.drgElementOutputFEELType(bkm, bkmContext);
            result = typeChecker.checkResult(result, expectedType);

            // BKM end
            EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, Result.value(result), (System.currentTimeMillis() - startTime_));

            return result;
        }

        @Override
        public Result visitDSReference(DRGElementReference<TDecisionService> serviceReference, DMNContext serviceContext) {
            TDecisionService service = serviceReference.getElement();

            // Decision Service start
            long startTime_ = System.currentTimeMillis();
            DRGElement drgElementAnnotation = makeDRGElementAnnotation(service);
            Arguments decisionArguments = makeArguments(service, serviceContext);
            EVENT_LISTENER.startDRGElement(drgElementAnnotation, decisionArguments);

            // Evaluate output decisions
            List<TDecision> outputDecisions = new ArrayList<>();
            List<Result> results = new ArrayList<>();
            for (TDMNElementReference outputDecisionReference : service.getOutputDecision()) {
                TDecision decision = repository.findDecisionByRef(service, outputDecisionReference.getHref());
                outputDecisions.add(decision);

                String importName = repository.findImportName(service, outputDecisionReference);
                ImportPath decisionImportPath = new ImportPath(serviceReference.getImportPath(), importName);
                DRGElementReference<TDecision> reference = repository.makeDRGElementReference(decisionImportPath, decision);
                Result result = visitDecisionReference(reference, makeDecisionGlobalContext(reference, serviceContext));
                results.add(result);
            }

            // Make context result
            Object output;
            if (outputDecisions.size() == 1) {
                output = Result.value(results.get(0));
            } else {
                output = new Context();
                for (int i = 0; i < outputDecisions.size(); i++) {
                    TDecision decision = outputDecisions.get(i);
                    Object value = Result.value(results.get(i));
                    ((Context) output).add(decision.getName(), value);
                }
            }

            // Check result
            Type expectedType = dmnTransformer.drgElementOutputFEELType(service, serviceContext);
            Result result = typeChecker.checkResult(Result.of(output, expectedType), expectedType);

            // Decision service end
            EVENT_LISTENER.endDRGElement(drgElementAnnotation, decisionArguments, Result.value(result), (System.currentTimeMillis() - startTime_));

            return result;
        }

        //
        // Expression evaluation
        //
        @Override
        public Result visit(TExpression expression, EvaluationContext evaluationContext) {
            TDRGElement element = ((ExpressionEvaluationContext) evaluationContext).getElement();
            Result result = null;
            if (expression == null) {
                String message = "Missing expression for element '%s'".formatted(element == null ? null : element.getName());
                this.errorHandler.reportError(message);
            } else {
                result = expression.accept(this, evaluationContext);
            }
            return result;
        }

        @Override
        public Result visit(TLiteralExpression expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            DMNContext context = expressionContext.getContext();
            String text = expression.getText();
            return evaluateLiteralExpression(text, context);
        }

        @Override
        public Result visit(TInvocation invocation, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext parentContext = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Compute and bind arguments
            TDefinitions model = repository.getModel(element);
            Map<String, Object> argBinding = new LinkedHashMap<>();
            for (TBinding binding : invocation.getBinding()) {
                // Evaluate argument
                String argName = binding.getParameter().getName();
                TExpression argExpression = binding.getExpression();
                Result argResult = this.visit(argExpression, EvaluationContext.makeExpressionEvaluationContext(element, parentContext, elementAnnotation));

                // Check argument
                argResult = typeChecker.checkArgument(argResult, binding.getParameter(), model);
                Object argJava = Result.value(argResult);

                // Bind argument
                argBinding.put(argName, argJava);
            }

            // Evaluate functionExp
            TExpression functionExp = invocation.getExpression();
            Result functionResult = this.visit(functionExp, EvaluationContext.makeExpressionEvaluationContext(element, parentContext, elementAnnotation));
            Type functionType = Result.type(functionResult);
            if (functionType instanceof FunctionType type) {
                // Make args
                List<Object> argList = new ArrayList<>();
                for (FormalParameter<Type> param : type.getParameters()) {
                    String paramName = param.getName();
                    if (argBinding.containsKey(paramName)) {
                        Object argValue = argBinding.get(paramName);
                        argList.add(argValue);
                    } else {
                        throw new DMNRuntimeException("Cannot find binding for parameter '%s' in in element '%s'".formatted(paramName, element));
                    }
                }

                // Evaluate function invocation
                Object function = Result.value(functionResult);
                Result returnResult = elInterpreter.evaluateFunctionInvocation((Function) function, type, argList);

                // Check result
                returnResult = typeChecker.checkResult(returnResult, type.getReturnType());
                return returnResult;
            } else {
                throw new DMNRuntimeException("Expecting function type found '%s' in element '%s'".formatted(functionType, element));
            }
        }

        @Override
        public Result visit(TContext context, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext parentContext = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            TDefinitions model = repository.getModel(element);

            // Make entry context
            Pair<DMNContext, Map<TContextEntry, Expression<Type>>> pair = dmnTransformer.makeContextEnvironment(element, context, parentContext);
            DMNContext localContext = pair.getLeft();

            // Evaluate entries
            Result returnResult = null;
            Map<TContextEntry, Result> entryResultMap = new LinkedHashMap<>();
            Map<TContextEntry, Expression<Type>> literalExpressionMap = pair.getRight();
            for (TContextEntry entry : context.getContextEntry()) {
                // Evaluate entry value
                Result entryResult;
                TExpression expression = entry.getExpression();
                if (expression != null) {
                    if (expression instanceof TLiteralExpression) {
                        Expression<Type> feelExpression = literalExpressionMap.get(entry);
                        entryResult = elInterpreter.evaluateExpression(feelExpression, localContext);
                    } else {
                        entryResult = this.visit(expression, EvaluationContext.makeExpressionEvaluationContext(element, localContext, elementAnnotation));
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
                for (TContextEntry entry : context.getContextEntry()) {
                    TInformationItem variable = entry.getVariable();
                    if (variable != null) {
                        // Determine entry type
                        String entryName = variable.getName();
                        String typeRef = QualifiedName.toName(variable.getTypeRef());
                        Type entryType;
                        if (StringUtils.isEmpty(typeRef)) {
                            Result entryResult = entryResultMap.get(entry);
                            entryType = Result.type(entryResult);
                        } else {
                            entryType = dmnTransformer.toFEELType(model, typeRef);
                        }
                        // Add member type
                        type.addMember(entryName, new ArrayList<>(), entryType);

                        // Check constraint for entry
                        Object entryValue = localContext.lookupBinding(entryName);
                        entryValue = typeChecker.checkEntry(entryValue, entryType);
                        // Add entry value
                        output.add(entryName, entryValue);
                    }
                }
                // Return value
                return Result.of(output, type);
            }
        }

        @Override
        public Result visit(TList list, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            if (list.getExpression() == null) {
                return null;
            }

            // Evaluate list
            List<Object> resultValue = new ArrayList<>();
            Type firstElementType = ANY;
            TDefinitions model = repository.getModel(element);
            for (TExpression elementExp : list.getExpression()) {
                // Evaluate list element
                Result elementResult = this.visit(elementExp, EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));

                // Set elementType
                if (Type.isNullOrAny(firstElementType)) {
                    firstElementType = Result.type(elementResult);
                }

                // Check constraint for element
                elementResult = typeChecker.checkListElement(elementResult, elementExp.getTypeRef(), model);

                // Collect element value
                Object expValue = Result.value(elementResult);
                resultValue.add(expValue);
            }

            // Determine list type
            QName listTypeRef = list.getTypeRef();
            Type listType;
            if (listTypeRef == null) {
                listType = new ListType(firstElementType);
            } else {
                listType = dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, listTypeRef));
            }

            // Check result
            Result result = typeChecker.checkResult(Result.of(resultValue, listType), listType);

            // Return result
            return result;
        }

        @Override
        public Result visit(TRelation relation, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext parentContext = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            if (relation.getRow() == null || relation.getColumn() == null) {
                return null;
            }

            // Evaluate relation
            List<Object> relationValue = new ArrayList<>();
            TDefinitions model = repository.getModel(element);
            DMNContext relationContext = dmnTransformer.makeRelationContext(element, relation, parentContext);
            List<TInformationItem> columns = relation.getColumn();
            ContextType firstRowType = null;
            for (TList row : relation.getRow()) {
                // Evaluate row
                Object rowValue = null;
                List<? extends TExpression> rowExp = row.getExpression();
                if (rowExp != null) {
                    Context contextValue = new Context();
                    ContextType actualRowType = new ContextType();
                    for (int i = 0; i < rowExp.size(); i++) {
                        // Evaluate column
                        TExpression columnExp = rowExp.get(i);
                        Result columnResult = columnExp == null ? null : this.visit(columnExp, EvaluationContext.makeExpressionEvaluationContext(element, relationContext, elementAnnotation));

                        // Check constraint for column
                        columnResult = typeChecker.checkListElement(columnResult, columns.get(i), columnExp, model);

                        // Collect column value
                        String columnName = columns.get(i).getName();
                        Object columnValue = Result.value(columnResult);
                        contextValue.add(columnName, columnValue);

                        // Determine actual column type
                        Type columnType = columnResult == null ? ANY : Result.type(columnResult);
                        actualRowType.addMember(columnName, new ArrayList<>(), columnType);
                        if (Type.isNull(firstRowType)) {
                            firstRowType = actualRowType;
                        }
                    }
                    rowValue = contextValue;
                }
                relationValue.add(rowValue);
            }

            // Determine relation type
            Type relationType;
            QName listTypeRef = relation.getTypeRef();
            if (listTypeRef == null) {
                relationType = new ListType(firstRowType);
            } else {
                relationType = dmnTransformer.toFEELType(model, QualifiedName.toQualifiedName(model, listTypeRef));
            }

            // Check result
            Result result = typeChecker.checkResult(Result.of(relationValue, relationType), relationType);

            // Return result
            return result;
        }

        @Override
        public Result visit(TConditional expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Check semantics
            Type type = dmnTransformer.expressionType(element, expression, context);

            // Evaluate
            Result condition = this.visit(expression.getIf().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
            Result result;
            if (Result.value(condition) == Boolean.TRUE) {
                result = this.visit(expression.getThen().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
            } else {
                result = this.visit(expression.getElse().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
            }

            return Result.of(Result.value(result), type);
        }

        @Override
        public Result visit(TFilter expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Check semantics
            Type sourceType = dmnTransformer.expressionType(element, expression, context);

            // Evaluate
            Result source = this.visit(expression.getIn().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
            Object sourceValue = Result.value(source);
            sourceValue = checkSource(sourceValue, "Expected list in filter boxed expression found '%s' in element '%s'".formatted(sourceValue, element.getName()));
            List<Object> resultValue = new ArrayList<>();
            for (Object item : (List) sourceValue) {
                String filterParameterName = FilterExpression.FILTER_PARAMETER_NAME;
                DMNContext filterContext = dmnTransformer.makeFilterContext(sourceType, filterParameterName, context);
                filterContext.bind(filterParameterName, item);

                Result filterResult = this.visit(expression.getMatch().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, filterContext, elementAnnotation));
                if (Result.value(filterResult) == Boolean.TRUE) {
                    resultValue.add(item);
                }
            }
            return Result.of(resultValue, sourceType);
        }

        @Override
        public Result visit(TFor expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Check semantics
            Type sourceType = dmnTransformer.expressionType(element, expression, context);

            // Evaluate
            Result source = this.visit(expression.getIn().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
            Object sourceValue = Result.value(source);
            sourceValue = checkSource(sourceValue, "Expected list in filter boxed expression found '%s' in element '%s'".formatted(sourceValue, element.getName()));
            List<Object> resultValue = new ArrayList<>();
            for (Object item : (List) sourceValue) {
                DMNContext iteratorContext = dmnTransformer.makeIteratorContext(context);
                String iteratorVariable = expression.getIteratorVariable();
                iteratorContext.addDeclaration(environmentFactory.makeVariableDeclaration(iteratorVariable, ((ListType) sourceType).getElementType()));
                iteratorContext.bind(iteratorVariable, item);

                Result iterationResult = this.visit(expression.getReturn().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, iteratorContext, elementAnnotation));
                resultValue.add(Result.value(iterationResult));
            }
            return Result.of(resultValue, sourceType);
        }

        @Override
        public Result visit(TSome expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Check semantics
            Type type = dmnTransformer.expressionType(element, expression, context);
            Type sourceType = dmnTransformer.expressionType(element, expression.getIn().getExpression(), context);

            // Evaluate
            Result source = this.visit(expression.getIn().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
            Object sourceValue = Result.value(source);
            sourceValue = checkSource(sourceValue, "Expected list in some boxed expression found '%s' in element '%s'".formatted(sourceValue, element.getName()));
            Boolean resultValue = Boolean.FALSE;
            for (Object item : (List) sourceValue) {
                DMNContext iteratorContext = dmnTransformer.makeIteratorContext(context);
                String iteratorVariable = expression.getIteratorVariable();
                iteratorContext.addDeclaration(environmentFactory.makeVariableDeclaration(iteratorVariable, ((ListType) sourceType).getElementType()));
                iteratorContext.bind(iteratorVariable, item);

                Result iterationResult = this.visit(expression.getSatisfies().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, iteratorContext, elementAnnotation));
                if (Result.value(iterationResult) == Boolean.TRUE) {
                    resultValue = Boolean.TRUE;
                    break;
                }
            }
            return Result.of(resultValue, type);
        }

        @Override
        public Result visit(TEvery expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Check semantics
            Type type = dmnTransformer.expressionType(element, expression, context);
            Type sourceType = dmnTransformer.expressionType(element, expression.getIn().getExpression(), context);

            // Evaluate
            Result source = this.visit(expression.getIn().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
            Object sourceValue = Result.value(source);
            sourceValue = checkSource(sourceValue, "Expected list in every boxed expression found '%s' in element '%s'".formatted(sourceValue, element.getName()));
            Boolean resultValue = Boolean.TRUE;
            for (Object item : (List) sourceValue) {
                DMNContext iteratorContext = dmnTransformer.makeIteratorContext(context);
                String iteratorVariable = expression.getIteratorVariable();
                iteratorContext.addDeclaration(environmentFactory.makeVariableDeclaration(iteratorVariable, ((ListType) sourceType).getElementType()));
                iteratorContext.bind(iteratorVariable, item);

                Result iterationResult = this.visit(expression.getSatisfies().getExpression(), EvaluationContext.makeExpressionEvaluationContext(element, iteratorContext, elementAnnotation));
                Object value = Result.value(iterationResult);
                if (value == null || value == Boolean.FALSE) {
                    resultValue = Boolean.FALSE;
                    break;
                }
            }
            return Result.of(resultValue, type);
        }

        private List checkSource(Object sourceValue, String errorMessage) {
            if (sourceValue instanceof List list) {
                return list;
            } else {
                throw new DMNRuntimeException(errorMessage);
            }
        }

        @Override
        public Result visit(TFunctionDefinition expression, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            Type type = dmnTransformer.expressionType(element, expression, context);
            Function function = DMNFunction.of(expression, type, context);
            return Result.of(function, type);
        }

        @Override
        public Result visit(TDecisionTable decisionTable, EvaluationContext evaluationContext) {
            ExpressionEvaluationContext expressionContext = (ExpressionEvaluationContext) evaluationContext;
            TDRGElement element = expressionContext.getElement();
            DMNContext context = expressionContext.getContext();
            DRGElement elementAnnotation = expressionContext.getElementAnnotation();
            // Evaluate InputClauses
            List<InputClausePair> inputClauseList = new ArrayList<>();
            for (TInputClause inputClause : decisionTable.getInput()) {
                // Evaluate input
                TLiteralExpression inputExpression = inputClause.getInputExpression();
                String inputExpressionText = inputExpression.getText();
                Expression<Type> feelInputExpression = elInterpreter.analyzeExpression(inputExpressionText, context);
                Result inputExpressionResult = elInterpreter.evaluateExpression(feelInputExpression, context);

                // Check input expression
                Object inputExpressionValue = typeChecker.checkInputExpression(inputExpressionResult, inputClause, feelInputExpression.getType());

                // Collect result
                inputClauseList.add(new InputClausePair(feelInputExpression, inputExpressionValue));
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
            return Result.of(value, dmnTransformer.drgElementOutputFEELType(element));
        }

        private InterpretedRuleOutput evaluateRule(TDRGElement element, TDecisionTable decisionTable, TDecisionRule rule, List<InputClausePair> inputClauseList, DMNContext context, DRGElement elementAnnotation, Rule ruleAnnotation) {
            // Check tests
            TDefinitions model = repository.getModel(element);
            List<TUnaryTests> inputEntry = rule.getInputEntry();
            boolean ruleMatched = true;
            for (int index = 0; index < inputEntry.size(); index++) {
                TUnaryTests unaryTest = inputEntry.get(index);
                String text = unaryTest.getText();
                Expression<Type> inputExpression = inputClauseList.get(index).getExpression();
                DMNContext testContext = dmnTransformer.makeUnaryTestContext(inputExpression, context);
                testContext.bind(DMNContext.INPUT_ENTRY_PLACE_HOLDER, inputClauseList.get(index).getValue());
                Expression<Type> ast = elInterpreter.analyzeUnaryTests(text, testContext);
                Result result = elInterpreter.evaluateUnaryTests((UnaryTests<Type>) ast, testContext);
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
                List<TLiteralExpression> outputEntry = rule.getOutputEntry();
                if (repository.isCompoundDecisionTable(element)) {
                    Context output = new Context();
                    for (int i = 0; i < outputEntry.size(); i++) {
                        // Evaluate output expression
                        TOutputClause outputClause = decisionTable.getOutput().get(i);
                        TLiteralExpression outputExpression = outputEntry.get(i);
                        Result result = this.visit(outputExpression, EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));

                        // Check output expression
                        result = typeChecker.checkOutputClause(result, outputClause, outputExpression, model);

                        // Collect rule output
                        Object value = Result.value(result);
                        String key = decisionTable.getOutput().get(i).getName();
                        if (repository.isOutputOrderHit(hitPolicy)) {
                            Object priority = dmnTransformer.outputClausePriority(element, rule.getOutputEntry().get(i), i);
                            output.put(key, new Pair<>(value, priority));
                        } else {
                            output.put(key, new Pair<>(value, null));
                        }
                    }
                    return new InterpretedRuleOutput(ruleMatched, output);
                } else {
                    // Evaluate output entry
                    TOutputClause outputClause = decisionTable.getOutput().get(0);
                    TLiteralExpression outputExpression = outputEntry.get(0);
                    Result result = this.visit(outputExpression, EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));

                    // Check output expression
                    result = typeChecker.checkOutputClause(result, outputClause, outputExpression, model);

                    // Collect rule output
                    Object value = Result.value(result);
                    Object output;
                    if (repository.isOutputOrderHit(hitPolicy)) {
                        Object priority = dmnTransformer.outputClausePriority(element, rule.getOutputEntry().get(0), 0);
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
            if (o instanceof List list) {
                if (list.stream().anyMatch(t -> t == null || Boolean.FALSE.equals(o))) {
                    return false;
                }
            }
            return o == null || Boolean.FALSE.equals(o);
        }

        private Object applyHitPolicy(TDRGElement element, TDecisionTable decisionTable, RuleOutputList ruleOutputList, DMNContext context, DRGElement elementAnnotation) {
            if (ruleOutputList.noMatchedRules()) {
                return evaluateDefaultValue(element, decisionTable, dmnTransformer, context, elementAnnotation);
            } else {
                THitPolicy hitPolicy = decisionTable.getHitPolicy();
                if (repository.isSingleHit(hitPolicy)) {
                    InterpretedRuleOutput ruleOutput = (InterpretedRuleOutput) ruleOutputList.applySingle(HitPolicy.fromValue(hitPolicy.value()));
                    return toDecisionOutput(element, decisionTable, ruleOutput);
                } else if (repository.isMultipleHit(hitPolicy)) {
                    List<? extends RuleOutput> ruleOutputs = ruleOutputList.applyMultiple(HitPolicy.fromValue(hitPolicy.value()));
                    if (repository.isCompoundDecisionTable(element)) {
                        if (repository.hasAggregator(decisionTable)) {
                            return null;
                        } else {
                            return ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                        }
                    } else {
                        List decisionOutput = ruleOutputs.stream().map(r -> toDecisionOutput(element, decisionTable, (InterpretedRuleOutput) r)).collect(Collectors.toList());
                        if (repository.hasAggregator(decisionTable)) {
                            TBuiltinAggregator aggregation = decisionTable.getAggregation();
                            if (aggregation == TBuiltinAggregator.MIN) {
                                return feelLib.min(decisionOutput);
                            } else if (aggregation == TBuiltinAggregator.MAX) {
                                return feelLib.max(decisionOutput);
                            } else if (aggregation == TBuiltinAggregator.COUNT) {
                                return feelLib.number("%d".formatted(decisionOutput.size()));
                            } else if (aggregation == TBuiltinAggregator.SUM) {
                                return feelLib.sum(decisionOutput);
                            } else {
                                throw new DMNRuntimeException("Not supported '%s' aggregation.".formatted(aggregation));
                            }
                        } else {
                            return decisionOutput;
                        }
                    }
                } else {
                    throw new DMNRuntimeException("Hit policy '%s' not supported ".formatted(hitPolicy));
                }
            }
        }

        private Object evaluateDefaultValue(TDRGElement element, TDecisionTable decisionTable, BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, DMNContext context, DRGElement elementAnnotation) {
            if (repository.hasDefaultValue(decisionTable)) {
                // Evaluate and collect default values
                List<TOutputClause> outputClauses = decisionTable.getOutput();
                Context defaultValue = new Context();
                for (TOutputClause output : outputClauses) {
                    TLiteralExpression defaultOutputEntry = output.getDefaultOutputEntry();
                    String key = repository.outputClauseName(element, output);
                    if (defaultOutputEntry == null) {
                        defaultValue.put(key, null);
                    } else {
                        Result result = this.visit(defaultOutputEntry, EvaluationContext.makeExpressionEvaluationContext(element, context, elementAnnotation));
                        Object value = Result.value(result);
                        defaultValue.put(key, value);
                    }
                }
                // Return result
                if (repository.isCompoundDecisionTable(element)) {
                    if (dmnTransformer.hasListType(element)) {
                        return Arrays.asList(defaultValue);
                    } else {
                        return defaultValue;
                    }
                } else {
                    String key = repository.outputClauseName(element, decisionTable.getOutput().get(0));
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
            if (result instanceof Context context) {
                Context newContext = new Context();
                for (Object key : context.keySet()) {
                    newContext.put(key, ((Pair) context.get(key)).getLeft());
                }
                return newContext;
                // Simple decision
            } else if (result instanceof Pair pair) {
                return pair.getLeft();
            } else {
                return result;
            }
        }


        //
        // Logging
        //
        protected DRGElement makeDRGElementAnnotation(TDRGElement element) {
            return new DRGElement(null,
                    repository.name(element),
                    repository.label(element),
                    dmnTransformer.elementKind(element),
                    dmnTransformer.expressionKind(element),
                    dmnTransformer.hitPolicy(element),
                    repository.rulesCount(element)
            );
        }

        private Arguments makeArguments(TDRGElement element, DMNContext context) {
            Arguments arguments = new Arguments();
            DRGElementReference<? extends TDRGElement> reference = repository.makeDRGElementReference(element);
            List<String> parameters = dmnTransformer.drgElementArgumentDisplayNameList(reference);
            for (String p : parameters) {
                Object value = context.lookupBinding(p);
                arguments.put(p, value);
            }
            return arguments;
        }

        private Rule makeRuleAnnotation(TDecisionRule rule, int ruleIndex) {
            return new Rule(ruleIndex, dmnTransformer.annotationEscapedText(rule));
        }
    }

    Result handleEvaluationError(String namespace, String type, String name, Exception e) {
        String errorMessage = "Cannot evaluate %s namespace='%s' name='%s'".formatted(type, namespace, name);
        this.errorHandler.reportError(errorMessage, e);
        Result result = Result.of(null, NullType.NULL);
        result.addError(errorMessage, e);
        return result;
    }
}