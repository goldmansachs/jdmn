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
package com.gs.dmn.error;

import org.slf4j.Logger;

public class LogErrorHandler implements ErrorHandler {
    private final Logger logger;

    public LogErrorHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void reportError(String message) {
        this.logger.error(message);
    }

    @Override
    public void reportError(String message, Exception e) {
        this.logger.error(message, e);
    }
}
