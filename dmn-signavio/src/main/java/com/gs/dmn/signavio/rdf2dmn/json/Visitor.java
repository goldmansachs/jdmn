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
package com.gs.dmn.signavio.rdf2dmn.json;


import com.gs.dmn.signavio.rdf2dmn.json.decision.DecisionTable;
import com.gs.dmn.signavio.rdf2dmn.json.decision.FreeTextExpression;
import com.gs.dmn.signavio.rdf2dmn.json.decision.LiteralExpression;
import com.gs.dmn.signavio.rdf2dmn.json.expression.*;

public interface Visitor {
    // Decision expressions
    String visit(DecisionTable element, Context params);
    String visit(FreeTextExpression element, Context params);
    String visit(LiteralExpression element, Context params);

    // Expressions
    String visit(Logical element, Context params);
    String visit(Comparison element, Context params);
    String visit(Arithmetic element, Context params);
    String visit(ArithmeticNegation element, Context context);
    String visit(FunctionCall element, Context params);
    String visit(List element, Context params);
    String visit(SimpleLiteral element, Context params);

    // UnaryTests
    String visit(Any element, Context context);
    String visit(Disjunction element, Context context);
    String visit(Enumeration element, Context context);
    String visit(Hierarchy element, Context context);
    String visit(Interval element, Context context);
    String visit(Negation element, Context context);
    String visit(UnaryComparison element, Context context);
    String visit(UnaryTestFunctionCall element, Context context);
    String visit(DataAcceptance dataAcceptance, Context context);
    String visit(Reference element, Context params);
}
