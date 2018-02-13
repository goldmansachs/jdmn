/**
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
package com.gs.dmn.runtime.compiler;

public class JavaAssistClassData extends ClassDataImpl implements ClassData {
    private final String methodText;
    private final String bridgeMethodText;

    public JavaAssistClassData(String packageName, String className, String methodText, String bridgeMethodText) {
        super(packageName, className);
        this.methodText = methodText;
        this.bridgeMethodText = bridgeMethodText;
    }

    public String getMethodText() {
        return methodText;
    }

    public String getBridgeMethodText() {
        return bridgeMethodText;
    }
}
