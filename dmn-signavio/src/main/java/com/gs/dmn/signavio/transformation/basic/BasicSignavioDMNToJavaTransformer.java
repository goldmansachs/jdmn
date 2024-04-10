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
package com.gs.dmn.signavio.transformation.basic;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.QualifiedName;
import com.gs.dmn.ast.*;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.semantics.type.FEELFunctionType;
import com.gs.dmn.feel.analysis.syntax.ast.expression.context.Context;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FormalParameter;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.FunctionDefinition;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.StringLiteral;
import com.gs.dmn.feel.lib.StringEscapeUtil;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.runtime.metadata.ExtensionElement;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.extension.MultiInstanceDecisionLogic;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToJavaTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.native_.statement.Statement;
import com.gs.dmn.transformation.proto.ProtoBufferJavaFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BasicSignavioDMNToJavaTransformer extends BasicDMNToJavaTransformer {
    private static final String DECISION_OUTPUT_FIELD_NAME = "value";

    private final SignavioDMNModelRepository dmnModelRepository;

    public BasicSignavioDMNToJavaTransformer(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect, DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, NativeTypeFactory feelTypeTranslator, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        super(dialect, dmnModelRepository, environmentFactory, feelTypeTranslator, lazyEvaluationDetector, inputParameters);
        this.dmnModelRepository = (SignavioDMNModelRepository) super.getDMNModelRepository();
    }

    @Override
    protected void setProtoBufferFactory(BasicDMNToJavaTransformer transformer) {
        this.protoFactory = new ProtoBufferJavaFactory(this);
    }

    @Override
    protected void setDMNEnvironmentFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.dmnEnvironmentFactory = new SignavioDMNEnvironmentFactory(transformer);
    }

    @Override
    protected void setExpressionToNativeTransformer(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.expressionToNativeTransformer = new SignavioDMNExpressionToNativeTransformer(transformer);
    }

    //
    // BKM
    //
    @Override
    public String drgElementOutputType(TDRGElement element) {
        String outputType;
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            outputType = super.drgElementOutputType(outputDecision);
        } else if (element instanceof TDecision && this.dmnModelRepository.isFreeTextLiteralExpression(element)) {
            Type type = drgElementOutputFEELType(element);
            outputType = toNativeType(type);
        } else {
            outputType = super.drgElementOutputType(element);
        }
        return this.nativeTypeFactory.nullableType(outputType);
    }

    public String drgElementOutputFieldName(TDRGElement element, int outputIndex) {
        if (this.dmnModelRepository.isDecisionTableExpression(element)) {
            TDecisionTable decisionTable = (TDecisionTable) this.dmnModelRepository.expression(element);
            return nativeFriendlyVariableName(this.dmnModelRepository.outputClauseName(element, decisionTable.getOutput().get(outputIndex)));
        } else if (this.dmnModelRepository.isLiteralExpression(element)) {
            return DECISION_OUTPUT_FIELD_NAME;
        } else {
            TExpression value = this.dmnModelRepository.expression(element);
            throw new UnsupportedOperationException(String.format("'%s' is not supported yet ", value.getClass().getSimpleName()));
        }
    }

    public String externalFunctionClassName(Expression<Type> body) {
        if (body instanceof Context) {
            Expression<Type> javaExpression = ((Context<Type>) body).entry("java").getExpression();
            if (javaExpression instanceof Context) {
                Expression<Type> returnTypeExp = ((Context<Type>) javaExpression).entry("class").getExpression();
                if (returnTypeExp instanceof StringLiteral) {
                    String lexeme = ((StringLiteral<Type>) returnTypeExp).getLexeme();
                    return StringEscapeUtil.stripQuotes(lexeme);
                }
            }
        }
        throw new DMNRuntimeException(String.format("Missing class in '%s'", body));
    }

    public String externalFunctionMethodName(Expression<Type> body) {
        if (body instanceof Context) {
            Expression<Type> javaExpression = ((Context<Type>) body).entry("java").getExpression();
            if (javaExpression instanceof Context) {
                Expression<Type> returnTypeExp = ((Context<Type>) javaExpression).entry("methodSignature").getExpression();
                if (returnTypeExp instanceof StringLiteral) {
                    // Signature should be methodName(arg1, arg2, ..., argN)
                    String lexeme = ((StringLiteral<Type>) returnTypeExp).getLexeme();
                    String signature = StringEscapeUtil.stripQuotes(lexeme);
                    int index = signature.indexOf('(');
                    if (index != -1) {
                        signature = signature.substring(0, index);
                    }
                    return signature;
                }
            }
        }
        throw new DMNRuntimeException(String.format("Missing methodName in '%s'", body));
    }

    @Override
    public String drgElementSignature(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            DRGElementReference<TDecision> outputReference = this.dmnModelRepository.makeDRGElementReference(outputDecision);
            List<Pair<String, Type>> parameters = inputDataParametersClosure(outputReference);
            String decisionSignature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(toNativeType(p.getRight()), p.getLeft())).collect(Collectors.joining(", "));
            return augmentSignature(decisionSignature);
        } else {
            return super.drgElementSignature(reference);
        }
    }

    @Override
    protected List<Pair<String, Type>> bkmParameters(DRGElementReference<TBusinessKnowledgeModel> reference, Function<Object, String> nameProducer) {
        TBusinessKnowledgeModel bkm = reference.getElement();
        TFunctionDefinition encapsulatedLogic = bkm.getEncapsulatedLogic();
        if (encapsulatedLogic == null) {
            List<FormalParameter<Type>> parameters = new ArrayList<>();
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision(bkm);
            DRGElementReference<TDecision> outputReference = this.dmnModelRepository.makeDRGElementReference(outputDecision);
            List<Pair<String, Type>> paramaters = this.drgElementTypeSignature(outputDecision);
            return paramaters;
        } else {
            return super.bkmParameters(reference, nameProducer);
        }
    }

    @Override
    public String drgElementArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            DRGElementReference<TDecision> outputReference = this.dmnModelRepository.makeDRGElementReference(outputDecision);
            List<Pair<String, Type>> parameters = inputDataParametersClosure(outputReference);
            String arguments = parameters.stream().map(p -> String.format("%s", p.getLeft())).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            return super.drgElementArgumentList(reference);
        }
    }

    @Override
    public String drgElementConvertedArgumentList(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            DRGElementReference<TDecision> outputReference = this.dmnModelRepository.makeDRGElementReference(outputDecision);
            List<Pair<String, Type>> parameters = inputDataParametersClosure(outputReference);
            String arguments = parameters.stream().map(p -> String.format("%s", convertDecisionArgument(p.getLeft(), p.getRight()))).collect(Collectors.joining(", "));
            return augmentArgumentList(arguments);
        } else {
            return super.drgElementConvertedArgumentList(reference);
        }
    }

    @Override
    public List<String> drgElementArgumentNameList(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementArgumentNameList(outputDecision);
        } else {
            return super.drgElementArgumentNameList(reference);
        }
    }

    @Override
    public List<String> drgElementArgumentDisplayNameList(DRGElementReference<? extends TDRGElement> reference) {
        TDRGElement element = reference.getElement();
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return super.drgElementArgumentDisplayNameList(outputDecision);
        } else {
            return super.drgElementArgumentDisplayNameList(reference);
        }
    }

    public String bkmLinkedToDecisionToNative(TBusinessKnowledgeModel bkm) {
        TDecision outputDecision = this.dmnModelRepository.getOutputDecision(bkm);
        String decisionClassName = drgElementClassName(outputDecision);
        List<String> argNameList = drgElementArgumentNameList(outputDecision);
        String decisionArgList = String.join(", ", argNameList);
        decisionArgList = augmentArgumentList(decisionArgList);
        return String.format("%s.apply(%s)", defaultConstructor(decisionClassName), decisionArgList);
    }

    @Override
    public List<FormalParameter<Type>> bkmFEELParameters(TBusinessKnowledgeModel bkm) {
        TFunctionDefinition encapsulatedLogic = bkm.getEncapsulatedLogic();
        if (encapsulatedLogic == null) {
            List<FormalParameter<Type>> parameters = new ArrayList<>();
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision(bkm);
            DRGElementReference<TDecision> outputReference = this.dmnModelRepository.makeDRGElementReference(outputDecision);
            List<DRGElementReference<TInputData>> allInputDataReferences = this.dmnModelRepository.inputDataClosure(outputReference, this.drgElementFilter);
            this.dmnModelRepository.sortNamedElementReferences(allInputDataReferences);
            for (DRGElementReference<TInputData> reference: allInputDataReferences) {
                TInputData id = reference.getElement();
                parameters.add(new FormalParameter<>(id.getName(), drgElementOutputFEELType(id)));
            }
            return parameters;
        } else {
            return super.bkmFEELParameters(bkm);
        }
    }

    @Override
    public QualifiedName drgElementOutputTypeRef(TDRGElement element) {
        if (this.dmnModelRepository.isBKMLinkedToDecision(element)) {
            TDecision outputDecision = this.dmnModelRepository.getOutputDecision((TBusinessKnowledgeModel) element);
            return drgElementOutputTypeRef(outputDecision);
        } else {
            return super.drgElementOutputTypeRef(element);
        }
    }

    //
    // Manifest
    //
    public List<ExtensionElement> makeMetadataExtensions(TDecision decision) {
        List<ExtensionElement> extensions = new ArrayList<>();
        if (this.dmnModelRepository.isMultiInstanceDecision(decision)) {
            ExtensionElement extensionElement = this.dmnModelRepository.getExtension().makeMultiInstanceExtension(decision);
            extensions.add(extensionElement);
        }
        return extensions;
    }

    //
    // Annotations
    //
    @Override
    public List<String> annotations(TDRGElement element, List<String> annotationTexts) {
        List<String> annotationStatements = new ArrayList<>();
        DMNContext context = this.makeGlobalContext(element);
        for (String annotationText : annotationTexts) {
            try {
                // Add rule annotation
                if (StringUtils.isBlank(annotationText)) {
                    annotationStatements.add("\"\"");
                } else {
                    Statement statement = annotation(element, annotationText, context);
                    annotationStatements.add(statement.getText());
                }
            } catch (Exception e) {
                LOGGER.error(String.format("Cannot process annotation '%s' for element '%s'", annotationText, element == null ? "" : element.getName()));
                // Add unevaluated annotation text
                annotationStatements.add(String.format("\"%s\"", annotationText.replaceAll("\"", "\\\\\"")));
            }
        }
        return annotationStatements;
    }

    //
    // Multi Instance Decision
    //
    public MultiInstanceDecisionLogic multiInstanceDecisionLogic(TDecision decision) {
        return this.dmnModelRepository.getExtension().multiInstanceDecisionLogic(decision);
    }

    public String iterationExpressionToNative(TDecision decision, String iterationExpression) {
        return literalExpressionToNative(decision, iterationExpression);
    }

    private String iterationSignature(TDecision decision) {
        List<DRGElementReference<? extends TDRGElement>> dmnReferences = collectIterationInputs(decision);
        List<Pair<String, String>> parameters = new ArrayList<>();
        for (DRGElementReference<? extends TDRGElement> reference : dmnReferences) {
            TDRGElement element = reference.getElement();
            String parameterName = iterationParameterName(element);
            String parameterNativeType = lazyEvaluationType(element, parameterNativeType(element));
            parameters.add(new Pair<>(parameterName, parameterNativeType));
        }
        String signature = parameters.stream().map(p -> this.nativeFactory.nullableParameter(p.getRight(), p.getLeft())).collect(Collectors.joining(", "));
        return augmentSignature(signature);
    }

    private String iterationArgumentList(TDecision decision) {
        List<DRGElementReference<? extends TDRGElement>> dmnReferences = collectIterationInputs(decision);

        List<String> arguments = new ArrayList<>();
        for (DRGElementReference<? extends TDRGElement> reference: dmnReferences) {
            TDRGElement element = reference.getElement();
            String argumentName = iterationArgumentName(element);
            arguments.add(argumentName);
        }
        String argumentList = String.join(", ", arguments);
        return augmentArgumentList(argumentList);
    }

    private List<DRGElementReference<? extends TDRGElement>> collectIterationInputs(TDecision decision) {
        Set<DRGElementReference<? extends TDRGElement>> elementSet = new LinkedHashSet<>();
        DRGElementReference<TDecision> decisionReference = this.dmnModelRepository.makeDRGElementReference(decision);
        elementSet.addAll(this.dmnModelRepository.inputDataClosure(decisionReference, this.drgElementFilter));
        elementSet.addAll(this.dmnModelRepository.directSubDecisions(decision));
        List<DRGElementReference<? extends TDRGElement>> elements = new ArrayList<>(elementSet);
        this.dmnModelRepository.sortNamedElementReferences(elements);
        return elements;
    }

    private String iterationParameterName(TDRGElement element) {
        return namedElementVariableName(element);
    }

    private String iterationArgumentName(TDRGElement element) {
        return namedElementVariableName(element);
    }

    //
    // Free text LiteralExpression related functions
    //
    public String freeTextLiteralExpressionToNative(TDRGElement element) {
        TLiteralExpression expression = (TLiteralExpression) this.dmnModelRepository.expression(element);
        DMNContext globalContext = this.makeGlobalContext(element);
        Expression<Type> literalExpression = this.feelTranslator.analyzeExpression(expression.getText(), globalContext);
        if (literalExpression instanceof FunctionDefinition) {
            Expression<Type> body = ((FunctionDefinition<Type>) literalExpression).getBody();
            String javaCode;
            if (((FunctionDefinition<Type>) literalExpression).isExternal()) {
                Type type = literalExpression.getType();
                if (type instanceof FEELFunctionType) {
                    type = ((FEELFunctionType) type).getReturnType();
                }
                String returnNativeType = toNativeType(type);
                String className = externalFunctionClassName(body);
                String methodName = externalFunctionMethodName(body);
                String arguments = drgElementArgumentList(element);
                javaCode = this.nativeFactory.makeExternalExecutorCall(externalExecutorVariableName(), className, methodName, arguments, returnNativeType);
            } else {
                javaCode = this.feelTranslator.expressionToNative(body, globalContext);
            }
            Type expressionType = body.getType();
            Statement statement = this.nativeFactory.makeExpressionStatement(javaCode, expressionType);
            Type expectedType = drgElementOutputFEELType(element);
            Statement result = convertExpression(statement, expectedType);
            return result.getText();
        } else {
            return super.literalExpressionToNative(element, expression.getText());
        }
    }
}
