package com.gs.dmn.transformation;

public class FunctionResource {
    private final String functionName;
    private final String codeUri;
    private final String handler;

    public FunctionResource(String functionName, String codeUri, String handler) {
        this.functionName = functionName;
        this.codeUri = codeUri;
        this.handler = handler;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getCodeUri() {
        return codeUri;
    }

    public String getHandler() {
        return handler;
    }
}
