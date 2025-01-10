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
