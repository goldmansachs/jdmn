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
package com.gs.dmn.feel.lib.type.string;

import net.sf.saxon.s9api.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the FEEL string functions replace(), matches() and split() with the
 * exact XPath F&amp;O semantics required by the DMN specification, delegating to
 * Saxon-HE.
 *
 * <p>This is the only class in the runtime that references Saxon. It is loaded
 * lazily by {@link DefaultStringLib}, so Saxon-HE is an optional dependency that
 * is only required when this evaluator is selected.
 */
class XPathRegexEvaluator implements RegexEvaluator {
    // Saxon's Processor is thread-safe and expensive to create; share one instance.
    private final Processor processor = new Processor(false);

    @Override
    public String evaluateReplace(String input, String pattern, String replacement, String flags) throws SaxonApiException {
        String functionName = "replace";
        List<String> paramNames = Arrays.asList("input", "pattern", "replacement", "flags");
        List<String> arguments = Arrays.asList(input, pattern, replacement, flags);
        XdmValue result = evaluateFunction(functionName, paramNames, arguments);
        return ((XdmAtomicValue) result).getStringValue();
    }

    @Override
    public boolean evaluateMatches(String input, String pattern, String flags) throws SaxonApiException {
        String functionName = "matches";
        List<String> paramNames = Arrays.asList("input", "pattern", "flags");
        List<String> arguments = Arrays.asList(input, pattern, flags);
        XdmValue result = evaluateFunction(functionName, paramNames, arguments);
        return ((XdmAtomicValue) result).getBooleanValue();
    }

    @Override
    public List<String> evaluateSplit(String input, String pattern) throws SaxonApiException {
        String functionName = "tokenize";
        List<String> paramNames = Arrays.asList("input", "pattern");
        List<String> arguments = Arrays.asList(input, pattern);
        XdmValue result = evaluateFunction(functionName, paramNames, arguments);
        // Iterate over the resulting tokens
        return result.stream().map(XdmItem::getStringValue).collect(Collectors.toList());
    }

    private XdmValue evaluateFunction(String functionName, List<String> paramNames, List<String> arguments) throws SaxonApiException {
        XPathCompiler xpathCompiler = processor.newXPathCompiler();

        // Declare variables in the XPath expression
        for (String param: paramNames) {
            xpathCompiler.declareVariable(new QName(param));
        }

        // Construct an XPath expression using the tokenize() function
        String call = String.format("%s(%s)", functionName, paramNames.stream().map(p -> "$"+p).collect(Collectors.joining(", ")));
        XPathExecutable compile = xpathCompiler.compile(call);
        XPathSelector selector = compile.load();

        // Set the input string and pattern as variables
        for (int i = 0; i< paramNames.size(); i++) {
            selector.setVariable(new QName(paramNames.get(i)), new XdmAtomicValue(arguments.get(i)));
        }

        // Evaluate the XPath expression
        return selector.evaluate();
    }
}
