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
package com.gs.dmn.fitnesse.fixture.sfeel;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.feel.interpreter.FEELInterpreter;
import com.gs.dmn.feel.interpreter.SFEELInterpreterImpl;
import com.gs.dmn.fitnesse.fixture.AbstractFixture;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;

public class SFEELFixture extends AbstractFixture {
    protected final FEELInterpreter feelInterpreter;

    public SFEELFixture() {
        DMNInterpreter dmnInterpreter = this.dialectDefinition.createDMNInterpreter(new DMNModelRepository());
        this.feelInterpreter = new SFEELInterpreterImpl(dmnInterpreter);
    }
}
