lexer grammar SFEELLexer;

@header {
package com.gs.dmn.feel.analysis.syntax.antlrv4;

import java.util.*;

}

@lexer::members {
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
    ('"' ( EscSeq | ~(["] | [\u000A-\u000D]) )*  '"' )
    ;
NUMBER:
    (Digits ('.' Digits)?) | ('.' Digits)
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

NAME:
    'date and time'
    |
    'days and time duration'
    |
    'years and months duration'
    |
    'string length'
    |
    'upper case'
    |
    'lower case'
    |
    'substring before'
    |
    'substring after'
    |
    'starts with'
    |
    'ends with'
    |
    'start position'
    |
    'list contains'
    |
    'insert before'
    |
    'index of'
    |
    'distinct values'
    |
    'time offset'
    |
    NameStartChar ( NamePartChar )*
    |
    ('\'' ( ~(['] | [\u000A-\u000D]) )*  '\'' )
    ;

fragment EscSeq:
	Esc
	(
	    [btnfr"'\\]	    // The standard escaped character set such as tab, newline, etc.
	    | UnicodeEsc	// A Unicode escape sequence
	    | .				// Invalid escape character
	    | EOF			// Incomplete at EOF
	)
	;

fragment Esc : '\\'	;

fragment UnicodeEsc:
    'u' (HexDigit (HexDigit (HexDigit HexDigit?)?)?)?
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
