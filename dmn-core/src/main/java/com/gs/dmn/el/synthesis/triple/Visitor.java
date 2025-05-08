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
package com.gs.dmn.el.synthesis.triple;

public interface Visitor<C, R> {
    //
    // Literals
    //
    R visit(NumberLiteral triple, C context);

    R visit(StringLiteral triple, C context);

    R visit(BooleanLiteral triple, C context);

    R visit(DateTimeLiteral triple, C context);

    R visit(NullLiteral triple, C context);

    R visit(NameTriple triple, C context);

    R visit(ContextEntryTriple triple, C context);

    //
    // Path
    //
    R visit(ItemDefinitionAccessor triple, C context);

    R visit(ContextAccessor triple, C context);

    R visit(ContextSelect triple, C context);

    R visit(LibraryFunctionSelect triple, C context);

    R visit(RangeAccessor triple, C context);

    R visit(CollectionMap triple, C context);

    //
    // Filter
    //
    R visit(CollectionLogicFilter triple, C context);

    R visit(CollectionNumericFilter triple, C context);

    //
    // Function
    //
    R visit(FunctionDefinitionTriple triple, C context);

    R visit(BuiltinFunctionInvocation triple, C context);

    R visit(LibraryFunctionInvocation triple, C context);

    R visit(ApplyInvocation triple, C context);

    R visit(SingletonInvocableInstance triple, C context);

    R visit(Constructor triple, C context);

    R visit(FluentConstructor triple, C context);

    //
    // Conversion
    //
    R visit(AsList triple, C context);

    R visit(ConversionFunction triple, C context);

    R visit(ConvertArgument convertArgument, C context);

    //
    // Expressions
    //
    R visit(InfixTriple infixExpression, C context);

    R visit(IsNullTriple triple, C context);

    R visit(IsNotNullTriple triple, C context);

    R visit(IfTriple triple, C context);

    R visit(ForTriple triple, C context);

    R visit(EveryTriple triple, C context);

    R visit(SomeTriple triple, C context);

    R visit(InstanceOfTriple triple, C context);

    R visit(LazyEvaluationTriple triple, C context);

    R visit(TripleReference triple, C context);

    R visit(TextTriple triple, C context);
}
