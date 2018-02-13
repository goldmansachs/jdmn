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
package com.gs.dmn.dialect;

import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.UniformJavaTimeFEELLib;
import com.gs.dmn.feel.synthesis.type.FEELTypeTranslator;
import com.gs.dmn.feel.synthesis.type.UniformJavaTimeFEELTypeTranslator;
import com.gs.dmn.runtime.UniformJavaTimeDMNBaseDecision;

public class UniformJavaTimeDMNDialectDefinition extends StandardDMNDialectDefinition {
    //
    // DMN execution
    //
    @Override
    public FEELTypeTranslator createTypeTranslator() {
        return new UniformJavaTimeFEELTypeTranslator();
    }

    @Override
    public FEELLib createFEELLib() {
        return new UniformJavaTimeFEELLib();
    }

    @Override
    public String getDecisionBaseClass() {
        return UniformJavaTimeDMNBaseDecision.class.getName();
    }
}
