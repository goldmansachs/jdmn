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
package com.gs.dmn.transformation.basic;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.DRGElementReference;
import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.ast.TInvocable;
import com.gs.dmn.ast.TItemDefinition;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.EnvironmentFactory;
import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.synthesis.type.NativeTypeFactory;
import com.gs.dmn.runtime.Pair;
import com.gs.dmn.transformation.DMNToJavaTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.native_.PythonFactory;
import com.gs.dmn.transformation.proto.ProtoBufferPythonFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasicDMNToPythonTransformer extends BasicDMNToJavaTransformer {
    public BasicDMNToPythonTransformer(DMNDialectDefinition<?, ?, ?, ?, ?, ?> dialect, DMNModelRepository dmnModelRepository, EnvironmentFactory environmentFactory, NativeTypeFactory feelTypeTranslator, LazyEvaluationDetector lazyEvaluationDetector, InputParameters inputParameters) {
        super(dialect, dmnModelRepository, environmentFactory, feelTypeTranslator, lazyEvaluationDetector, inputParameters);
    }

    @Override
    protected void setProtoBufferFactory(BasicDMNToJavaTransformer transformer) {
        this.protoFactory = new ProtoBufferPythonFactory(this);
    }

    @Override
    protected void setNativeFactory(BasicDMNToNativeTransformer<Type, DMNContext> transformer) {
        this.nativeFactory = new PythonFactory(this);
    }

    //
    // TItemDefinition related functions
    //
    @Override
    public String itemDefinitionNativeQualifiedInterfaceName(TItemDefinition itemDefinition) {
        return super.itemDefinitionNativeQualifiedInterfaceName(itemDefinition);
    }

    @Override
    public String itemDefinitionNativeClassName(String qInterfaceName) {
        String name = qInterfaceName;
        // Remove Optional parts
        name = removeOptionalParts(name);
        //  Find simple name from qualified name p.I.I
        String simpleInterfaceName;
        int i = name.lastIndexOf(".");
        if (i == -1) {
            simpleInterfaceName = name;
        } else {
            simpleInterfaceName = name.substring(i + 1);
        }
        String simpleClassName = simpleInterfaceName + "Impl";
        return qInterfaceName.replaceAll(simpleInterfaceName, simpleClassName);
    }

    protected String removeOptionalParts(String name) {
        while (name.startsWith("typing.Optional[")) {
            int first = name.indexOf("[");
            int last = name.lastIndexOf("]");
            name = name.substring(first + 1, last);
        }
        return name;
    }

    @Override
    public String itemDefinitionSignature(TItemDefinition itemDefinition) {
        List<Pair<String, String>> parameters = new ArrayList<>();
        List<TItemDefinition> itemComponents = itemDefinition.getItemComponent();
        this.dmnModelRepository.sortNamedElements(itemComponents);
        for (TItemDefinition child : itemComponents) {
            parameters.add(new Pair<>(namedElementVariableName(child), itemDefinitionNativeQualifiedInterfaceName(child)));
        }
        return parameters.stream().map(p -> this.nativeFactory.nullableParameter(p.getRight(), p.getLeft()) + " = None").collect(Collectors.joining(", "));
    }

    //
    // DRGElement related functions
    //
    @Override
    public String drgElementOutputType(DRGElementReference<? extends TDRGElement> reference) {
        return this.nativeTypeFactory.nullableType(super.drgElementOutputType(reference.getElement()));
    }

    @Override
    public String drgElementOutputType(TDRGElement element) {
        return this.nativeTypeFactory.nullableType(super.drgElementOutputType(element));
    }

    @Override
    public String drgElementSignatureWithMap(TDRGElement element) {
        return String.format("%s: %s, %s: %s", inputVariableName(), inputClassName(), executionContextVariableName(), executionContextClassName());
    }

    @Override
    public String drgElementSignature(DRGElementReference<? extends TDRGElement> reference) {
        String result = super.drgElementSignature(reference);
        return augmentWithSelf(result);
    }

    @Override
    public String drgElementConstructorSignature(TDRGElement element) {
        return augmentWithSelf(super.drgElementConstructorSignature(element));
    }

    //
    // NamedElement functions
    //
    @Override
    public String lambdaApplySignature() {
        return "*" + lambdaArgsVariableName();
    }

    //
    // Decision related functions
    //
    @Override
    public String lazyEvaluationType(TDRGElement input, String inputNativeType) {
        return isLazyEvaluated(input) ? String.format("%s", lazyEvalClassName()) : inputNativeType;
    }

    @Override
    public String lazyEvaluation(String elementName, String nativeName) {
        return isLazyEvaluated(elementName) ? String.format("%s.getOrCompute()", nativeName) : nativeName;
    }

    @Override
    protected String inputClassName() {
        return "dict";
    }

    //
    // Invocable  related functions
    //
    @Override
    public String singletonInvocableInstance(TInvocable invocable) {
        return String.format("%s.%s", qualifiedName(invocable), "instance()");
    }

    //
    // Common functions
    //
    @Override
    public String makeListType(String listType, String elementType) {
        if (DMNToJavaTransformer.LIST_TYPE.equals(listType)) {
            listType = String.format("typing.%s", listType);
        }
        return this.nativeTypeFactory.nullableType(String.format("%s[%s]", listType, this.nativeTypeFactory.nullableType(elementType)));
    }

    @Override
    public String makeListType(String listType) {
        return makeListType(listType, "typing.Any");
    }

    @Override
    protected String makeFunctionType(String name, String returnType) {
        return name;
    }

    @Override
    public String jdmnRootPackage() {
        return "jdmn";
    }

    @Override
    public String qualifiedName(String pkg, String clsName) {
        String pythonClsPath = String.format("%s.%s", clsName, clsName);
        if (StringUtils.isBlank(pkg)) {
            return pythonClsPath;
        } else {
            return String.format("%s.%s", pkg, pythonClsPath);
        }
    }

    @Override
    public String qualifiedName(Class<?> cls) {
        String qName = String.format("%s.%s", cls.getName(), cls.getSimpleName());
        String superRoot = super.jdmnRootPackage();
        qName = qName.replace(superRoot, jdmnRootPackage());
        return qName;
    }

    @Override
    public String qualifiedModuleName(DRGElementReference<? extends TDRGElement> reference) {
        return qualifiedModuleName(reference.getElement());
    }

    @Override
    public String qualifiedModuleName(TDRGElement element) {
        TDefinitions definitions = this.dmnModelRepository.getModel(element);
        String pkg = this.nativeModelPackageName(definitions.getName());
        String name = drgElementClassName(element);
        return qualifiedModuleName(pkg, name);
    }

    @Override
    public String qualifiedModuleName(String pkg, String moduleName) {
        if (StringUtils.isBlank(pkg)) {
            return moduleName;
        } else {
            return String.format("%s.%s", pkg, moduleName);
        }
    }

    @Override
    public String getter(String name) {
        return String.format("%s", lowerCaseFirst(name));
    }

    @Override
    public String nativeTypePackageName(String modelName) {
        String modelPackageName = nativeModelPackageName(modelName);
        String dataPackageName = DMNToJavaTransformer.DATA_PACKAGE + "_";
        if (StringUtils.isBlank(modelPackageName)) {
            return dataPackageName;
        } else {
            return modelPackageName + "." + dataPackageName;
        }
    }

    private String augmentWithSelf(String result) {
        if (StringUtils.isBlank(result)) {
            return "self";
        } else {
            return String.format("self, %s", result);
        }
    }

    //
    // Mock testing related methods
    //
    @Override
    public String makeIntegerForInput(String text) {
        return this.nativeFactory.constructor(getNativeNumberType(), String.format("str(int(\"%s\"))", text));
    }

    @Override
    public String makeDecimalForInput(String text) {
        return this.nativeFactory.constructor(getNativeNumberType(), String.format("str(float(\"%s\"))", text));
    }
}
