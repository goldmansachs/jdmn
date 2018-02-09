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

import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.interpreter.FEELInterpreterImpl;
import com.gs.dmn.feel.interpreter.SFEELInterpreterImpl;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.feel.synthesis.FEELTranslatorImpl;
import com.gs.dmn.feel.synthesis.SFEELTranslatorImpl;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import org.omg.spec.dmn._20151101.dmn.TDefinitions;

/**
 * Created by Octavian Patrascoiu on 24/05/2017.
 */
public abstract class AbstractDMNDialectDefinition implements DMNDialectDefinition {
    //
    // FEEL Processors
    //
    @Override
    public FEELInterpreter createFEELInterpreter(TDefinitions definitions) {
        DMNInterpreter dmnInterpreter = createDMNInterpreter(definitions);
        return new FEELInterpreterImpl(dmnInterpreter);
    }

    @Override
    public FEELTranslator createFEELTranslator(TDefinitions definitions, String javaRootPackage) {
        return new FEELTranslatorImpl(createBasicTransformer(definitions, javaRootPackage));
    }

    @Override
    public FEELInterpreter createSFEELInterpreter(TDefinitions definitions) {
        DMNInterpreter dmnInterpreter = createDMNInterpreter(definitions);
        return new SFEELInterpreterImpl(dmnInterpreter);
    }

    @Override
    public FEELTranslator createSFEELTranslator(TDefinitions definitions, String javaRootPackage) {
        return new SFEELTranslatorImpl(createBasicTransformer(definitions, javaRootPackage));
    }
}
