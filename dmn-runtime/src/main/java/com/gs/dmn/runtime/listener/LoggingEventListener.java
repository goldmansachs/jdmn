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
package com.gs.dmn.runtime.listener;

import org.slf4j.Logger;

public class LoggingEventListener implements SimpleEventListener {
    private final Logger logger;

    public LoggingEventListener(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void startDRGElement(DRGElement element, Arguments arguments) {
        logger.info("Start element '{}' with inputs '{}'", element.getName(), arguments);
    }

    @Override
    public void endDRGElement(DRGElement element, Arguments arguments, Object output, long duration) {
        logger.info("End element '{}' with output '{}' in {}ms", element.getName(), output, duration);
    }

    @Override
    public void startRule(DRGElement element, Rule rule) {
    }

    @Override
    public void matchRule(DRGElement element, Rule rule) {
    }

    @Override
    public void endRule(DRGElement element, Rule rule, Object output) {
        logger.debug("Rule {} fired with output '{}'", rule.getIndex(), output);
    }

    @Override
    public void matchColumn(Rule rule, int columnIndex, Object result) {
        logger.debug("Test {} checked with output '{}'", columnIndex, result);
    }
}
