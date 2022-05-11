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
package com.gs.dmn.tck.ast;

public interface Visitor {
    //
    // TCK Elements
    //
    // Test Cases
    <C> Object visit(TestCases element, C context);

    <C> Object visit(TestCase element, C context);

    <C> Object visit(Labels element, C context);

    // Nodes
    <C> Object visit(InputNode element, C context);

    <C> Object visit(ResultNode element, C context);

    // Values
    <C> Object visit(ValueType element, C context);

    <C> Object visit(List element, C context);

    <C> Object visit(Component element, C context);

    // Extensions
    <C> Object visit(ExtensionElements element, C context);
}
