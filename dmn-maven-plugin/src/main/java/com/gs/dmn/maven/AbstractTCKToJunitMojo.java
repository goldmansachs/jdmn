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
package com.gs.dmn.maven;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.tck.ast.TestCases;
import com.gs.dmn.tck.validation.CompositeTCKValidator;
import com.gs.dmn.tck.validation.NopTCKValidator;
import com.gs.dmn.tck.validation.TCKValidator;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CanBeFinal")
public abstract class AbstractTCKToJunitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestToJunitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {

    @Override
    protected TCKValidator makeTestValidator(String[] testValidatorClassNames, BuildLogger logger) throws Exception {
        if (testValidatorClassNames == null) {
            return new NopTCKValidator();
        }
        List<TCKValidator> testValidators = new ArrayList<>();
        for(String tckValidatorClassName: testValidatorClassNames) {
            Class<?> dmnValidatorClass = Class.forName(tckValidatorClassName);
            try {
                testValidators.add((TCKValidator) dmnValidatorClass.getConstructor(new Class[]{BuildLogger.class}).newInstance(new Object[]{logger}));
            } catch (Exception e) {
                testValidators.add((TCKValidator) dmnValidatorClass.getDeclaredConstructor().newInstance());
            }
        }
        return new CompositeTCKValidator(testValidators);
    }
}
