package com.gs.dmn.transformation;

public class FunctionResource {
    private final String functionName;
    private final String codeUri;
    private final String handler;
    private final String restPath;

    public FunctionResource(String functionName, String codeUri, String handler, String restPath) {
        this.functionName = functionName;
        this.codeUri = codeUri;
        this.handler = handler;
        this.restPath = restPath;
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

    public String getRestPath() {
        return restPath;
    }
}
