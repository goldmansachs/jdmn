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
package com.gs.dmn.dialect;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.el.interpreter.ELInterpreter;
import com.gs.dmn.el.synthesis.ELTranslator;
import com.gs.dmn.feel.analysis.syntax.ast.library.LibraryRepository;
import com.gs.dmn.feel.interpreter.AbstractFEELInterpreter;
import com.gs.dmn.feel.synthesis.FEELTranslator;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;

public abstract class AbstractDMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> implements DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    //
    // FEEL Processors
    //
    @Override
    public LibraryRepository createLibraryRepository(InputParameters inputParameters) {
        return new LibraryRepository(inputParameters);
    }

    @Override
    public ELInterpreter<Type, DMNContext> createELInterpreter(DMNModelRepository repository, InputParameters inputParameters) {
        DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter = createDMNInterpreter(repository, inputParameters);
        return createFEELInterpreter(dmnInterpreter);
    }

    @Override
    public ELTranslator<Type, DMNContext> createFEELTranslator(DMNModelRepository repository, InputParameters inputParameters) {
        return new FEELTranslator(createBasicTransformer(repository, new NopLazyEvaluationDetector(), inputParameters));
    }

    protected abstract AbstractFEELInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> createFEELInterpreter(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter);
}
