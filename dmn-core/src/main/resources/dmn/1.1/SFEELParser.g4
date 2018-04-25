parser grammar SFEELParser;

options { tokenVocab=SFEELLexer; }

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

}

@parser::members {
    private ASTFactory astFactory;

    public SFEELParser(TokenStream input, ASTFactory astFactory) {
        this(input);
        this.astFactory = astFactory;
    }

    public ASTFactory getASTFactory() {
        return astFactory;
    }
}

// Start rules
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

// Tests
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

simplePositiveUnaryTest returns [SimplePositiveUnaryTest ast] :
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
    (op = MINUS)? opd = simpleValue
  	{$ast = ($op == null) ? $opd.ast : astFactory.toNegation($op.text, $opd.ast);}
;

//
// Expression
//
expression returns [Expression ast] :
    left = simpleExpression
    {$ast = $left.ast; }
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

simpleExpression returns [Expression ast] :
    left = comparison
    {$ast = $left.ast;}
;

comparison returns [Expression ast] :
    ae1 = arithmeticExpression
    {$ast = $ae1.ast;}
    (
        (op = EQ | op = NE | op = LT | op = GT | op = LE | op = GE) ae2 = arithmeticExpression
        {$ast = astFactory.toComparison($op.text, $ae1.ast, $ae2.ast);}
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

arithmeticNegation returns [Expression ast] :
    {List<String> prefixOperators = new ArrayList<>();}
    (
        (MINUS {prefixOperators.add("-");})
    )*
    opd = primaryExpression
 	{$ast = astFactory.toNegation(prefixOperators, $opd.ast);}
;

primaryExpression returns [Expression ast] :
    (
        simpleValue
        {$ast = $simpleValue.ast;}
    )
    |
    (
        PAREN_OPEN expression PAREN_CLOSE
        {$ast = $expression.ast;}
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

dateTimeLiteral returns [Expression ast] :
    ( kind = identifier ) PAREN_OPEN stringLiteral PAREN_CLOSE
    {$ast = astFactory.toDateTimeLiteral($kind.text, $stringLiteral.ast);}
;

identifier returns [Token ast] :
    token = NAME
    {$ast = $token;}
;