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
package com.gs.dmn.log;

import org.slf4j.Logger;

public final class Slf4jBuildLogger implements BuildLogger {
    private final Logger logger;

    public Slf4jBuildLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String charSequence) {
        this.logger.debug(charSequence);
    }

    @Override
    public void info(String charSequence) {
        this.logger.info(charSequence);
    }

    @Override
    public void warn(String charSequence) {
        this.logger.warn(charSequence);
    }

    @Override
    public void error(String charSequence) {
        this.logger.error(charSequence);
    }
}
