parser grammar LibraryParser;
import FEELParser;

options { tokenVocab=LibraryLexer; }

@header {
}

@members {
    public static LibraryParser makeLibraryParser(TokenStream input, ASTFactory astFactory) {
        LibraryParser parser = new LibraryParser(input);
        parser.astFactory = astFactory;
        return parser;
    }

}
// Start rules
library returns [Library ast] :
	NAMESPACE namespace = qualifiedName SEMICOLON name = identifier BRACE_OPEN decls = functions BRACE_CLOSE
    {$ast = astFactory.toLibrary($namespace.ast, $name.text, $decls.ast);}
    EOF
    ;

functions returns [List<FunctionDeclaration> ast] :
    {List<FunctionDeclaration> functions = new ArrayList<>();}
	f = function
    {functions.add($f.ast);}
	(
	    SEMICOLON f = function
        {functions.add($f.ast);}
	)*
	{$ast = functions;}
	;

function returns [FunctionDeclaration ast] :
    {List<FormalParameter> parameters = new ArrayList<>();}
	FUNCTION name = identifier
	PAREN_OPEN
	(
	    param = formalParameter
        {parameters.add($param.ast);}
	    (
	        COMMA param = formalParameter
            {parameters.add($param.ast);}
	    )*
	)? PAREN_CLOSE COLON returnType = type
    {$ast = astFactory.toFunctionDeclaration($name.text, parameters, $returnType.ast);}
    ;

