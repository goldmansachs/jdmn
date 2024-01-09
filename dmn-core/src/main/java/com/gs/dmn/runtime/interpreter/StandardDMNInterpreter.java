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
package com.gs.dmn.runtime.interpreter;

import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.interpreter.StandardFEELInterpreter;
import com.gs.dmn.feel.interpreter.TypeConverter;
import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

public class StandardDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    public StandardDMNInterpreter(BasicDMNToNativeTransformer<Type, DMNContext> dmnTransformer, FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        super(dmnTransformer, feelLib);
        this.elInterpreter = new StandardFEELInterpreter<>(this);
        this.typeConverter = new TypeConverter(this.dmnTransformer, this.elInterpreter, this.feelLib);
    }

    @Override
    public StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> getFeelLib() {
        return (StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) this.feelLib;
    }
}
