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
package com.gs.dmn.transformation.lazy;

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.log.Slf4jBuildLogger;
import com.gs.dmn.transformation.InputParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleLazyEvaluationDetector implements LazyEvaluationDetector {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SparseDecisionDetector.class);

    protected final InputParameters inputParameters;
    protected final BuildLogger logger;

    protected SimpleLazyEvaluationDetector() {
        this(new InputParameters(), new Slf4jBuildLogger(LOGGER));
    }

    protected SimpleLazyEvaluationDetector(InputParameters inputParameters, BuildLogger logger) {
        this.inputParameters = inputParameters;
        this.logger = logger;
    }
}
