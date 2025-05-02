lexer grammar FEELLexer;

@header {
package com.gs.dmn.feel.analysis.syntax.antlrv4;

import java.util.*;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

}

@lexer::members {
    private static Pattern UNICODE_6_HEX = Pattern.compile("\\\\U([0-9a-fA-F]){6}");

    private static String convertUnicodeEscape(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        StringBuffer builder = new StringBuffer();
        Matcher matcher = UNICODE_6_HEX.matcher(value);
        while (matcher.find()) {
            int cp = Integer.decode(matcher.group(0).replaceAll("\\\\U", "0x"));
            StringBuilder sb = new StringBuilder();
            if (Character.isBmpCodePoint(cp)) {
                sb.append((char) cp);
            } else if (Character.isValidCodePoint(cp)) {
                sb.append(Character.highSurrogate(cp));
                sb.append(Character.lowSurrogate(cp));
            } else {
                sb.append('?');
            }
            matcher.appendReplacement(builder, sb.toString());
        }
        matcher.appendTail(builder);
        String result = builder.toString();
        return result;
    }
}

// Tokens
BLOCK_COMMENT:
    '/*' .*? '*/' -> skip
    ;

LINE_COMMENT:
    '//' ~[\u000A-\u000D]* -> skip
    ;

// White spaces
WS:
    WhiteSpace+ -> skip
    ;

// Literals
STRING:
    // 33. string literal = """, { character – (""" | vertical space) | string escape sequence}, """ ;
    '"' ( StringEscSeq | ~(["] | [\u000A-\u000D]) )*  '"'
    { setText(convertUnicodeEscape(getText())); }
    ;
NUMBER:
    (Digits ('.' Digits)? | '.' Digits) (('e'|'E') ('+'|'-')? Digits)?
    ;

TEMPORAL:
    '@' WhiteSpace* STRING
    ;

// Operators
EQ:
    '=' | '=='
    ;
NE:
    '!='
    ;
LT:
    '<'
    ;
GT:
    '>'
    ;
LE:
    '<='
    ;
GE:
    '>='
    ;
PLUS:
    '+'
    ;
MINUS:
    '-'
    ;
STAR:
    '*'
    ;
FORWARD_SLASH:
    '/'
    ;
STAR_STAR:
    '**'
    ;

// Punctuation
DOT_DOT_DOT:
    '...'
    ;
DOT_DOT:
    '..'
    ;
DOT:
    '.'
    ;
COMMA:
    ','
    ;
PAREN_OPEN:
    '('
    ;
PAREN_CLOSE:
    ')'
    ;
BRACKET_OPEN:
    '['
    ;
BRACKET_CLOSE:
    ']'
    ;
BRACE_OPEN:
    '{'
    ;
BRACE_CLOSE:
    '}'
    ;
COLON:
    ':'
    ;
ARROW:
    '->'
    ;

// Keywords
NOT:
    'not'
    ;
TRUE:
    'true'
    ;
FALSE:
    'false'
    ;
NULL:
    'null'
    ;
FUNCTION:
    'function'
    ;
EXTERNAL:
    'external'
    ;
FOR:
    'for'
    ;
IN:
    'in'
    ;
RETURN:
    'return'
    ;
IF:
    'if'
    ;
THEN:
    'then'
    ;
ELSE:
    'else'
    ;
SOME:
    'some'
    ;
EVERY:
    'every'
    ;
SATISFIES:
    'satisfies'
    ;
AND:
    'and'
    ;
OR:
    'or'
    ;
BETWEEN:
    'between'
    ;
INSTANCE_OF:
    'instance' WhiteSpace+ 'of'
    { setText("instance of"); }
    ;

NAME:
    // contructors
    'date' WhiteSpace+ 'and' WhiteSpace+ 'time'
    { setText("date and time"); }
    |
    'days' WhiteSpace+ 'and' WhiteSpace+ 'time' WhiteSpace+ 'duration'
    { setText("days and time duration"); }
    |
    'years' WhiteSpace+ 'and' WhiteSpace+ 'months' WhiteSpace+ 'duration'
    { setText("years and months duration"); }
    |
    // number functions
    'round' WhiteSpace+ 'up'
    { setText("round up"); }
    |
    'round' WhiteSpace+ 'down'
    { setText("round down"); }
    |
    'round' WhiteSpace+ 'half' WhiteSpace+ 'up'
    { setText("round half up"); }
    |
    'round' WhiteSpace+ 'half' WhiteSpace+ 'down'
    { setText("round half down"); }
    |
    // string functions
    'string' WhiteSpace+ 'length'
    { setText("string length"); }
    |
    'upper' WhiteSpace+ 'case'
    { setText("upper case"); }
    |
    'lower' WhiteSpace+ 'case'
    { setText("lower case"); }
    |
    'substring' WhiteSpace+ 'before'
    { setText("substring before"); }
    |
    'substring' WhiteSpace+ 'after'
    { setText("substring after"); }
    |
    'starts' WhiteSpace+ 'with'
    { setText("starts with"); }
    |
    'ends' WhiteSpace+ 'with'
    { setText("ends with"); }
    |
    'string' WhiteSpace+ 'join'
    { setText("string join"); }
    |
    // list functions
    'start' WhiteSpace+ 'position'
    { setText("start position"); }
    |
    'list' WhiteSpace+ 'contains'
    { setText("list contains"); }
    |
    'insert' WhiteSpace+ 'before'
    { setText("insert before"); }
    |
    'index' WhiteSpace+ 'of'
    { setText("index of"); }
    |
    'distinct' WhiteSpace+ 'values'
    { setText("distinct values"); }
    |
    // context functions
    'get' WhiteSpace+ 'entries'
    { setText("get entries"); }
    |
    'get' WhiteSpace+ 'value'
    { setText("get value"); }
    |
    'context' WhiteSpace+ 'put'
    { setText("context put"); }
    |
    'context' WhiteSpace+ 'merge'
    { setText("context merge"); }
    |
    // range functions
    'met' WhiteSpace+ 'by'
    { setText("met by"); }
    |
    'overlaps' WhiteSpace+ 'before'
    { setText("overlaps before"); }
    |
    'overlaps' WhiteSpace+ 'after'
    { setText("overlaps after"); }
    |
    'finished' WhiteSpace+ 'by'
    { setText("finished by"); }
    |
    'started' WhiteSpace+ 'by'
    { setText("started by"); }
    |
    'start' WhiteSpace+ 'included'
    { setText("start included"); }
    |
    'end' WhiteSpace+ 'included'
    { setText("end included"); }
    |
    // date time properties
    'day' WhiteSpace+ 'of' WhiteSpace+ 'year'
    { setText("day of year"); }
    |
    'day' WhiteSpace+ 'of' WhiteSpace+ 'week'
    { setText("day of week"); }
    |
    'month' WhiteSpace+ 'of' WhiteSpace+ 'year'
    { setText("month of year"); }
    |
    'week' WhiteSpace+ 'of' WhiteSpace+ 'year'
    { setText("week of year"); }
    |
    'time' WhiteSpace+ 'offset'
    { setText("time offset"); }
    |
    'list' WhiteSpace+ 'replace'
    { setText("list replace"); }
    |
    NameStartChar ( NamePartChar )*
    |
    '\'' ( ~(['] | [\u000A-\u000D]) | '\'\'')*  '\''
    { setText(com.gs.dmn.NameUtils.removeSingleQuotes(getText())); }
    ;

fragment StringEscSeq:
    // 64. string escape sequence = "\'" | "\"" | "\\" | "\n" | "\r" | "\t" | code point;
	Esc
	(
	    [btnfr"'\\]	    // The standard escaped character set such as tab, newline, etc.
	    | CodePoint	    // A Unicode escape sequence
	    | .				// Invalid escape character
	    | EOF			// Incomplete at EOF
	)
	;

fragment Esc : '\\'	;

fragment CodePoint:
    'u' HexDigit HexDigit HexDigit HexDigit
    |
    'U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit
	;

fragment NameStartChar:
    '?' | [A-Z] | '_' | [a-z] | [\u00C0-\u00D6] | [\u00D8-\u00F6] | [\u00F8-\u02FF] | [\u0370-\u037D] | [\u037F-\u1FFF]
    | [\u200C-\u200D] | [\u2070-\u218F] | [\u2C00-\u2FEF] | [\u3001-\uD7FF] | [\uF900-\uFDCF] | [\uFDF0-\uFFFD]
    | [\u{10000}-\u{EFFFF}]
    ;

fragment NamePartChar:
    NameStartChar | Digit | '\u00B7' | [\u0300-\u036F] | [\u203F-\u2040]
    ;

fragment Digit:
    [0-9]
    ;

fragment HexDigit:
    [0-9a-fA-F]
    ;

fragment Digits:
    Digit (Digit)*
    ;

fragment WhiteSpace:
    VerticalSpace | '\u0009' | '\u0020' | '\u0085' | '\u00A0' | '\u1680' | '\u180E' |
    [\u2000-\u200B] | '\u2028' | '\u2029' | '\u202F' | '\u205F' | '\u3000' | '\uFEFF'
    ;

fragment VerticalSpace:
    [\u000A-\u000D]
    ;
