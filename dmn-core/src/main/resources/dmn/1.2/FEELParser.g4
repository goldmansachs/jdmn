parser grammar FEELParser;

options { tokenVocab=FEELLexer; }

@header {
package com.gs.dmn.feel.analysis.syntax.antlrv4;

import com.gs.dmn.feel.analysis.syntax.ast.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.arithmetic.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.comparison.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.function.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.literal.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.logic.*;
import com.gs.dmn.feel.analysis.syntax.ast.expression.textual.*;
import com.gs.dmn.feel.analysis.syntax.ast.test.*;

import java.util.*;
}

@parser::members {
    private ASTFactory astFactory;

    public FEELParser(TokenStream input, ASTFactory astFactory) {
        this(input);
        this.astFactory = astFactory;
    }

    public ASTFactory getASTFactory() {
        return astFactory;
    }
}

// Start rules
unaryTestsRoot returns [UnaryTests ast] :
    unaryTests {$ast = $unaryTests.ast;}
    EOF
;

simpleUnaryTestsRoot returns [SimpleUnaryTests ast] :
    simpleUnaryTests {$ast = $simpleUnaryTests.ast;}
    EOF
;

expressionRoot returns [Expression ast] :
    expression {$ast = $expression.ast;}
    EOF
;

simpleExpressionsRoot returns [Expression ast] :
    simpleExpressions {$ast = $simpleExpressions.ast;}
    EOF
;

textualExpressionsRoot returns [Expression ast] :
    textualExpressions {$ast = $textualExpressions.ast;}
    EOF
;

boxedExpressionRoot returns [Expression ast] :
    boxedExpression {$ast = $boxedExpression.ast;}
    EOF
;

// Tests
unaryTests returns [UnaryTests ast] :
    (
        NOT PAREN_OPEN tests = positiveUnaryTests PAREN_CLOSE
        {$ast = astFactory.toNegatedUnaryTests($tests.ast);}
    )
    |
    (
        tests = positiveUnaryTests
        {$ast = $tests.ast;}
    )
    |
    (
        MINUS
        {$ast = astFactory.toAny();}
    )
;

positiveUnaryTests returns [PositiveUnaryTests ast]:
	{List<Expression> tests = new ArrayList<>();}
    test = positiveUnaryTest
    {tests.add($test.ast);}
    (
        COMMA
        test = positiveUnaryTest
        {tests.add($test.ast);}
    )*
    {$ast = astFactory.toPositiveUnaryTests(tests);}
;

positiveUnaryTest returns [Expression ast]:
    (
        expression
        {$ast = astFactory.toPositiveUnaryTest($expression.ast);}
    )
;

simpleUnaryTests returns [SimpleUnaryTests ast] :
    (
        NOT PAREN_OPEN tests = simplePositiveUnaryTests PAREN_CLOSE
        {$ast = astFactory.toNegatedSimpleUnaryTests($tests.ast);}
    )
    |
    (
        tests = simplePositiveUnaryTests
        {$ast = $tests.ast;}
    )
    |
    (
        MINUS
        {$ast = astFactory.toAny();}
    )
;

simplePositiveUnaryTests returns [SimplePositiveUnaryTests ast]:
	{List<Expression> tests = new ArrayList<>();}
    test = simplePositiveUnaryTest
    {tests.add($test.ast);}
    (
        COMMA test = simplePositiveUnaryTest
        {tests.add($test.ast);}
    )*
    {$ast = astFactory.toSimplePositiveUnaryTests(tests);}
;

simplePositiveUnaryTest returns [Expression ast] :
    (
        ( op = LT | op = LE | op = GT | op = GE )? opd = endpoint
        {$ast = $op == null ? astFactory.toOperatorTest(null, $opd.ast) : astFactory.toOperatorTest($op.text, $opd.ast);}
    )
    |
    (
        opd2 = interval
        {$ast = $opd2.ast;}
    )
;

interval returns [RangeTest ast] :
    leftPar = intervalStartPar ep1 = endpoint DOT_DOT ep2 = endpoint rightPar = intervalEndPar
    {$ast = astFactory.toIntervalTest($leftPar.ast, $ep1.ast, $rightPar.ast, $ep2.ast);}
;

intervalStartPar returns [String ast] :
    (
        token = PAREN_OPEN
        {$ast = $token.text;}
    )
    |
    (
        token = BRACKET_CLOSE
        {$ast = $token.text;}
    )
    |
    (
        token = BRACKET_OPEN
        {$ast = $token.text;}
    )
;

intervalEndPar returns [String ast] :
    (
        token = PAREN_CLOSE
        {$ast = $token.text;}
    )
    |
    (
        token = BRACKET_OPEN
        {$ast = $token.text;}
    )
    |
    (
        token = BRACKET_CLOSE
        {$ast = $token.text;}
    )
;

endpoint returns [Expression ast]:
    (
        (op = MINUS)? opd = simpleValue
     	{$ast = ($op == null) ? $opd.ast : astFactory.toNegation($op.text, $opd.ast);}
    )
;

//
// Simple expressions
//
simpleExpressions returns [Expression ast] :
	{List<Expression> expressionList = new ArrayList<>();}
    exp = simpleExpression
    {expressionList.add($exp.ast);}
    (
        COMMA exp = simpleExpression
        {expressionList.add($exp.ast);}
    )*
    {$ast = astFactory.toExpressionList(expressionList);}
;

simpleExpression returns [Expression ast]:
    (
        arithmeticExpression
        {$ast = $arithmeticExpression.ast;}
    )
    |
    (
        simpleValue
        {$ast = $simpleValue.ast;}
    )
;

//
// Expression
//
expression returns [Expression ast] :
    (
        textualExpression
        {$ast = $textualExpression.ast;}
    )
;

textualExpressions returns [Expression ast] :
	{List<Expression> expressionList = new ArrayList<>();}
    exp = textualExpression
    {expressionList.add($exp.ast);}
    (
        COMMA exp = textualExpression
        {expressionList.add($exp.ast);}
    )*
    {$ast = astFactory.toExpressionList(expressionList);}
;

textualExpression returns [Expression ast] :
    (
        forExpression
        {$ast = $forExpression.ast;}
    )
    |
    (
        ifExpression
        {$ast = $ifExpression.ast;}
    )
    |
    (
        quantifiedExpression
        {$ast = $quantifiedExpression.ast;}
    )
    |
    (
        disjunction
        {$ast = $disjunction.ast;}
    )
;

functionDefinition returns [Expression ast] :
    {List<FormalParameter> parameters = new ArrayList<>();}
    {boolean external = false;}
    FUNCTION PAREN_OPEN
    (
        param = formalParameter
        {parameters.add($param.ast);}
        (
            COMMA param = formalParameter
            {parameters.add($param.ast);}
        )*
    )?
    PAREN_CLOSE ( EXTERNAL {external = true;})?
    body = expression
    {$ast = astFactory.toFunctionDefinition(parameters, $body.ast, external);}
;

formalParameter returns [FormalParameter ast]:
    {String typeName = null; }
    name = parameterName (COLON type = typeName {typeName = $type.ast;})?
    {$ast = astFactory.toFormalParameter($name.ast, typeName);}
;

forExpression returns [Expression ast] :
    {List<com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator> iterators = new ArrayList<>();}
    FOR var = identifier IN  domain = iterationDomain
    {iterators.add(astFactory.toIterator($var.text, $domain.ast));}
    (
        COMMA var = identifier IN domain = iterationDomain
        {iterators.add(astFactory.toIterator($var.text, $domain.ast));}
    )*
    RETURN body = expression
    {$ast = astFactory.toForExpression(iterators, $body.ast);}
;

iterationDomain returns [IteratorDomain ast]:
    {Expression end = null;}
    exp1 = expression (DOT_DOT exp2 = expression {end = $exp2.ast;})?
    {$ast = astFactory.toIteratorDomain($exp1.ast, end);}
;

ifExpression returns [Expression ast] :
    IF cond = expression THEN
    exp1 = expression ELSE exp2 = expression
    {$ast = astFactory.toIfExpression($cond.ast, $exp1.ast, $exp2.ast);}
;

quantifiedExpression returns [Expression ast] :
    {List<com.gs.dmn.feel.analysis.syntax.ast.expression.Iterator> iterators = new ArrayList<>();}
    ( op = SOME | op = EVERY ) var = identifier IN domain = expression
    {iterators.add(astFactory.toIterator($var.text, $domain.ast));}
    (
        var = identifier IN domain = expression
        {iterators.add(astFactory.toIterator($var.text, $domain.ast));}
    )*
    SATISFIES body = expression
    {$ast = astFactory.toQuantifiedExpression($op.text, iterators, $body.ast);}
;

disjunction returns [Expression ast]:
    left = conjunction
    {$ast = $left.ast;}
    (
        OR right = conjunction
        {$ast = astFactory.toDisjunction($ast, $right.ast);}
    )*
;

conjunction returns [Expression ast] :
    left = comparison
    {$ast = $left.ast;}
    (
        AND right = comparison
        {$ast = astFactory.toConjunction($ast, $right.ast);}
    )*
;

comparison returns [Expression ast] :
    ae1 = arithmeticExpression
    {$ast = $ae1.ast;}
    (
        (
            (op = EQ | op = NE | op = LT | op = GT | op = LE | op = GE) ae2 = arithmeticExpression
            {$ast = astFactory.toComparison($op.text, $ae1.ast, $ae2.ast);}
        )
        |
        (
            BETWEEN leftEndpoint = expression AND rightEndpoint = expression
            {$ast = astFactory.toBetweenExpression($ast, $leftEndpoint.ast, $rightEndpoint.ast);}
        )
        |
        (
            IN test = positiveUnaryTest
            {$ast = astFactory.toInExpression($ast, $test.ast);}
        )
        |
        (
            IN PAREN_OPEN tests = positiveUnaryTests PAREN_CLOSE
            {$ast = astFactory.toInExpression($ast, $tests.ast);}
        )
    )?
;

arithmeticExpression returns [Expression ast] :
    addition
    {$ast = $addition.ast;}
;

addition returns [Expression ast] :
    left = multiplication
    {$ast = $left.ast;}
    (
        (op = PLUS | op = MINUS) right = multiplication
        {$ast = astFactory.toAddition($op.text, $ast, $right.ast);}
    )*
;

multiplication returns [Expression ast] :
    left = exponentiation
    {$ast = $left.ast;}
    (
        (op = STAR | op = FORWARD_SLASH) right = exponentiation
        {$ast = astFactory.toMultiplication($op.text, $ast, $right.ast);}
    )*
;

exponentiation returns [Expression ast] :
    left = arithmeticNegation
    {$ast = $left.ast;}
    (
        STAR_STAR right = arithmeticNegation
        {$ast = astFactory.toExponentiation($ast, $right.ast);}
    )*
;

// Extended to support boolean negation
arithmeticNegation returns [Expression ast] :
    {List<String> prefixOperators = new ArrayList<>();}
    (
        (MINUS {prefixOperators.add("-");})
        |
        (NOT {prefixOperators.add("not");})
    )*
    opd = instanceOf
 	{$ast = astFactory.toNegation(prefixOperators, $opd.ast);}
;

instanceOf returns [Expression ast] :
    exp = postfixExpression
    {$ast = $exp.ast;}
    (
        INSTANCE_OF
        (
            qualifiedName
            {$ast = astFactory.toInstanceOf($ast, $qualifiedName.ast);}
        )
    )?
;

postfixExpression returns [Expression ast] :
    (
        (
            primaryExpression {$ast = $primaryExpression.ast;}
        )
    )
    (
        (
            BRACKET_OPEN filter = expression BRACKET_CLOSE
            {$ast = astFactory.toFilterExpression($ast, $filter.ast);}
        )
        |
        (
            parameters
            {$ast = astFactory.toFunctionInvocation($ast, $parameters.ast);}
        )
        |
        (
            DOT name = identifier
            {$ast = astFactory.toPathExpression($ast, $name.text);}
        )
    )*
;

parameters returns [Parameters ast] :
    PAREN_OPEN
    (
        (
            namedParameters
            {$ast = $namedParameters.ast;}
        )
        |
        (
            positionalParameters
            {$ast = $positionalParameters.ast;}
        )
    )
    PAREN_CLOSE
;

namedParameters returns [NamedParameters ast]:
    {Map<String, Expression> parameters = new LinkedHashMap<>();}
    name = parameterName COLON value = expression
    {parameters.put($name.text, $value.ast);}
    (
        COMMA name = parameterName COLON value = expression
        {parameters.put($name.text, $value.ast);}
    )*
    {$ast = astFactory.toNamedParameters(parameters);}
;

parameterName returns [String ast]:
    name = identifier
    {$ast = $name.text;}
;

typeName returns [String ast]:
    name = qualifiedName
    {$ast = $name.text;}
;

positionalParameters returns [PositionalParameters ast]:
    {List<Expression> parameters = new ArrayList<>();}
    (
        param = expression
        {parameters.add($param.ast);}
        (
            COMMA param = expression
            {parameters.add($param.ast);}
        )*
    )?
    {$ast = astFactory.toPositionalParameters(parameters);}
;

primaryExpression returns [Expression ast] :
    (
        literal
        {$ast = $literal.ast;}
    )
    |
    (
        name = identifier
        {$ast = astFactory.toName($name.text);}
    )
    |
    (
        PAREN_OPEN exp = textualExpression PAREN_CLOSE
        {$ast = $exp.ast;}
    )
    |
    (
        boxedExpression
        {$ast = $boxedExpression.ast;}
    )
    |
    (
        simplePositiveUnaryTest
        {$ast = $simplePositiveUnaryTest.ast; }
    )
;

simpleValue returns [Expression ast]:
    (
        simpleLiteral
        {$ast = $simpleLiteral.ast;}
    )
    |
    (
        qualifiedName
        {$ast = $qualifiedName.ast;}
    )
;

qualifiedName returns [Expression ast] :
	{List<String> names = new ArrayList<>();}
    name=identifier
    {names.add($name.text);}
    (
        DOT name=identifier
        {names.add($name.text);}
    )*
    {$ast = astFactory.toQualifiedName(names);}
;

literal returns [Expression ast] :
    (
        (
            simpleLiteral
            {$ast = $simpleLiteral.ast;}
        )
        |
        (
            NULL
            {$ast = astFactory.toNullLiteral();}
        )
    )
;

simpleLiteral returns [Expression ast]:
    (
        numericLiteral
        {$ast = $numericLiteral.ast;}
    )
    |
    (
        stringLiteral
        {$ast = $stringLiteral.ast;}
    )
    |
    (
        booleanLiteral
        {$ast = $booleanLiteral.ast;}
    )
    |
    (
        dateTimeLiteral
        {$ast = $dateTimeLiteral.ast;}
    )
;

stringLiteral returns [Expression ast]:
    lit = STRING
    {$ast = astFactory.toStringLiteral($lit.text);}
;

booleanLiteral returns [Expression ast]:
    (lit = TRUE | lit = FALSE)
    {$ast = astFactory.toBooleanLiteral($lit.text);}
;

numericLiteral returns [Expression ast]:
    lit = NUMBER
    {$ast = astFactory.toNumericLiteral($lit.text);}
;

boxedExpression returns [Expression ast]:
    (
        list
        {$ast = $list.ast;}
    )
    |
    (
        functionDefinition
        {$ast = $functionDefinition.ast;}
    )
    |
    (
        context
        {$ast = $context.ast;}
    )
;

list returns [Expression ast] :
    {List<Expression> expressions = new ArrayList<>();}
    BRACKET_OPEN
    (
        exp = expression
        {expressions.add($exp.ast);}
        (
            COMMA exp = expression
            {expressions.add($exp.ast);}
        )*
    )?
    BRACKET_CLOSE
    {$ast = astFactory.toListLiteral(expressions);}
;

context returns [Expression ast] :
    {List<ContextEntry> entries = new ArrayList<>();}
    BRACE_OPEN
    (
        entry = contextEntry
        {entries.add($entry.ast);}
        (
            COMMA entry = contextEntry
            {entries.add($entry.ast);}
        )*
    )?
    BRACE_CLOSE
    {$ast = astFactory.toContext(entries);}
;

contextEntry returns [ContextEntry ast] :
    key COLON expression
    {$ast = astFactory.toContextEntry($key.ast, $expression.ast);}
;

key returns [ContextEntryKey ast] :
    (
        name = identifier
        {$ast = astFactory.toContextEntryKey($name.text);}
    )
    |
    (
        stringLiteral
        {$ast = astFactory.toContextEntryKey($stringLiteral.text);}
    )
;

dateTimeLiteral returns [Expression ast] :
    ( kind = identifier )
    PAREN_OPEN stringLiteral PAREN_CLOSE
    {$ast = astFactory.toDateTimeLiteral($kind.text, $stringLiteral.ast);}
;

identifier returns [Token ast] :
    (
        token = NAME
        {$ast = $token;}
    )
    |
    (
        token = OR
        {$ast = $token;}
    )
    |
    (
        token = AND
        {$ast = $token;}
    )
;