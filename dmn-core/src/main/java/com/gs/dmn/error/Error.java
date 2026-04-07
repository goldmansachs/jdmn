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

import org.apache.commons.lang3.StringUtils;

public abstract class Error {
    private final ErrorLocation errorLocation;
    private final SeverityLevel severityLevel;
    private final String errorMessage;

    public Error(SeverityLevel severityLevel, String errorMessage) {
        this(severityLevel, null, errorMessage);
    }

    public Error(SeverityLevel severityLevel, ErrorLocation errorLocation, String errorMessage) {
        this.errorLocation = errorLocation;
        this.severityLevel = severityLevel;
        this.errorMessage = errorMessage;
    }

    public ErrorLocation getErrorLocation() {
        return errorLocation;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String toText() {
        if (errorLocation == null || StringUtils.isBlank(errorLocation.toText())) {
            return String.format("[%s] %s", severityLevel.name(), errorMessage);
        } else {
            return String.format("[%s] %s: %s", severityLevel.name(), errorLocation.toText(), errorMessage);
        }
    }

    @Override
    public String toString() {
        return this.toText();
    }
}
