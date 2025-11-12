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

public class SemanticError {
    private final LocationInfo locationInfo;
    private final SeverityLevel severityLevel;
    private final String errorMessage;

    public SemanticError(SeverityLevel severityLevel, String errorMessage) {
        this(severityLevel, null, errorMessage);
    }

    public SemanticError(SeverityLevel severityLevel, LocationInfo locationInfo, String errorMessage) {
        this.locationInfo = locationInfo;
        this.severityLevel = severityLevel;
        this.errorMessage = errorMessage;
    }

    public LocationInfo getLocationInfo() {
        return locationInfo;
    }

    public SeverityLevel getSeverityLevel() {
        return severityLevel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String toText() {
        if (locationInfo == null) {
            return String.format("[%s] %s", severityLevel.name(), errorMessage);
        } else {
            return String.format("[%s] %s: %s", severityLevel.name(), locationInfo.toText(), errorMessage);
        }
    }

    @Override
    public String toString() {
        return this.toText();
    }
}
