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
package com.gs.dmn.feel.analysis.syntax.ast.library;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.context.environment.Declaration;
import com.gs.dmn.dialect.JavaTimeDMNDialectDefinition;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.analysis.semantics.FEELSemanticVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.Element;
import com.gs.dmn.feel.analysis.syntax.ast.Visitor;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Library<T> extends Element<T> {
    private final String namespace;
    private final String name;
    private final List<FunctionDeclaration<T>> functions;
    private final List<Declaration> declarations;


    public Library(String namespace, String name, List<FunctionDeclaration<T>> functions) {
        this.namespace = namespace;
        this.name = name;
        this.functions = functions;
        this.declarations = new ArrayList<>();
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public List<FunctionDeclaration<T>> getFunctions() {
        return functions;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    @Override
    public <C, R> R accept(Visitor<T, C, R> visitor, C context) {
        return visitor.visit(this, context);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library<?> library = (Library<?>) o;
        return Objects.equals(namespace, library.namespace) && Objects.equals(name, library.name) && Objects.equals(functions, library.functions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, name, functions);
    }

    @Override
    public String toString() {
        return String.format("Library(%s, %s, %s)", namespace, name, functions);
    }

    public ELLib analyze(DMNContext context) {
        BasicDMNToNativeTransformer<Type, DMNContext> transformer = new JavaTimeDMNDialectDefinition().createBasicTransformer(new DMNModelRepository(), new NopLazyEvaluationDetector(), new InputParameters());
        FEELSemanticVisitor semanticVisitor = new FEELSemanticVisitor(transformer);
        Library<Type> library = (Library<Type>) semanticVisitor.visit((Library<Type>) this, context);
        return new ELLib(library.namespace, library.name, library.getDeclarations());
    }
}
