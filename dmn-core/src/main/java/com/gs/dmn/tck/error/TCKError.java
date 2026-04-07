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
package com.gs.dmn.tck.error;

import com.gs.dmn.error.Error;
import com.gs.dmn.error.SeverityLevel;

public class TCKError extends Error {
    public TCKError(SeverityLevel severityLevel, TestLocation testLocation, String errorMessage) {
        super(severityLevel, testLocation, errorMessage);
    }
}
