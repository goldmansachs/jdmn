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
package com.gs.dmn.context;

public enum DMNContextKind {
    // The built-in context contains all the built-in functions.
    BUILT_IN,
    // The global context is a context created before the evaluation of e and contains names and values for the variables defined
    // outside expression e that are accessible in e. For example, when e is the body of a decision D, the global context contains
    // entries for the information requirements and knowledge requirements of D (i.e., names and logic of the business
    // knowledge models, decisions and decision services required by D).
    GLOBAL,
    // If e denotes the value of a context entry of context m, then m is the local context for e, and m is the first element of s.
    // Otherwise, e has no local context and the first element of s is the global context, or in some cases explained later, the first
    // element of s is a special context.
    LOCAL,
    // Special context
    UNARY_TEST,
    FUNCTION,
    FOR,
    ITERATOR,
    FILTER,
    LIST,
    RELATION
}
