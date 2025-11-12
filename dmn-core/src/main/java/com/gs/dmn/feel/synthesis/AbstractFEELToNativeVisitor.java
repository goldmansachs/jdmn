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
package com.gs.dmn.feel.synthesis;

import com.gs.dmn.NameUtils;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.error.LogAndThrowErrorHandler;
import com.gs.dmn.feel.analysis.AbstractAnalysisVisitor;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Expression;
import com.gs.dmn.feel.analysis.syntax.ast.expression.Name;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public abstract class AbstractFEELToNativeVisitor<R> extends AbstractAnalysisVisitor<Type, DMNContext, R> {
    private static final Map<String, String> FEEL_2_NATIVE_FUNCTION = new LinkedHashMap<>();

    static {
        // constructors
        FEEL_2_NATIVE_FUNCTION.put("date and time", "dateAndTime");
        FEEL_2_NATIVE_FUNCTION.put("years and months duration", "yearsAndMonthsDuration");

        // string functions
        FEEL_2_NATIVE_FUNCTION.put("ends with", "endsWith");
        FEEL_2_NATIVE_FUNCTION.put("starts with", "startsWith");
        FEEL_2_NATIVE_FUNCTION.put("substring after", "substringAfter");
        FEEL_2_NATIVE_FUNCTION.put("substring before", "substringBefore");
        FEEL_2_NATIVE_FUNCTION.put("lower case", "lowerCase");
        FEEL_2_NATIVE_FUNCTION.put("upper case", "upperCase");
        FEEL_2_NATIVE_FUNCTION.put("string length", "stringLength");
        FEEL_2_NATIVE_FUNCTION.put("string join", "stringJoin");

        // number functions
        FEEL_2_NATIVE_FUNCTION.put("round up", "roundUp");
        FEEL_2_NATIVE_FUNCTION.put("round down", "roundDown");
        FEEL_2_NATIVE_FUNCTION.put("round half up", "roundHalfUp");
        FEEL_2_NATIVE_FUNCTION.put("round half down", "roundHalfDown");

        // list functions
        FEEL_2_NATIVE_FUNCTION.put("distinct values", "distinctValues");
        FEEL_2_NATIVE_FUNCTION.put("index of", "indexOf");
        FEEL_2_NATIVE_FUNCTION.put("insert before", "insertBefore");
        FEEL_2_NATIVE_FUNCTION.put("list contains", "listContains");
        FEEL_2_NATIVE_FUNCTION.put("list replace", "listReplace");

        // context functions
        FEEL_2_NATIVE_FUNCTION.put("get value", "getValue");
        FEEL_2_NATIVE_FUNCTION.put("get entries", "getEntries");
        FEEL_2_NATIVE_FUNCTION.put("context put", "contextPut");
        FEEL_2_NATIVE_FUNCTION.put("context merge", "contextMerge");

        // range functions
        FEEL_2_NATIVE_FUNCTION.put("met by", "metBy");
        FEEL_2_NATIVE_FUNCTION.put("overlaps before", "overlapsBefore");
        FEEL_2_NATIVE_FUNCTION.put("overlaps after", "overlapsAfter");
        FEEL_2_NATIVE_FUNCTION.put("finished by", "finishedBy");
        FEEL_2_NATIVE_FUNCTION.put("started by", "startedBy");

        // date time properties
        FEEL_2_NATIVE_FUNCTION.put("day of year", "dayOfYear");
        FEEL_2_NATIVE_FUNCTION.put("day of week", "dayOfWeek");
        FEEL_2_NATIVE_FUNCTION.put("month of year", "monthOfYear");
        FEEL_2_NATIVE_FUNCTION.put("week of year", "weekOfYear");
    }

    private static final Set<String> RANGE_OPERATORS = new LinkedHashSet<>(
            Arrays.asList("=", "!=", "<", "<=", ">", ">=")
    );

    public AbstractFEELToNativeVisitor(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer) {
        super(dmnTransformer, new LogAndThrowErrorHandler(LOGGER));
    }

    protected String nativeFunctionName(String feelFunctionName) {
        String nativeFunctionName = FEEL_2_NATIVE_FUNCTION.get(feelFunctionName);
        if (StringUtils.isEmpty(nativeFunctionName)) {
            return feelFunctionName;
        } else {
            return nativeFunctionName;
        }
    }

    protected String propertyFunctionName(String memberName) {
        memberName = NameUtils.removeSingleQuotes(memberName);
        if ("time offset".equalsIgnoreCase(memberName)) {
            return "timeOffset";
        } else {
            return memberName;
        }
    }

    protected String nativeFriendlyVariableName(String name) {
        return this.dmnTransformer.lowerCaseFirst(name);
    }

    protected String functionName(Expression<Type> function) {
        return ((Name<Type>) function).getName();
    }

    protected static String normalizeOperator(String operator) {
        return StringUtils.isBlank(operator) ? "=" : operator;
    }

    protected static boolean isValidRangeOperator(String operator) {
        return RANGE_OPERATORS.contains(operator);
    }
}
