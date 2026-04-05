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
import com.gs.dmn.signavio.testlab.TestLab;
import com.gs.dmn.signavio.testlab.TestLabValidator;

@SuppressWarnings("CanBeFinal")
public abstract class AbstractTestLabToJunitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractTestToJunitMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestLab> {

    @Override
    protected TestLabValidator makeTestValidator(String[] testValidatorClassNames, BuildLogger logger) {
        return new TestLabValidator();
    }
}
