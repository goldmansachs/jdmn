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
package com.gs.dmn.transformation.proto;

import com.gs.dmn.runtime.DMNRuntimeException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Service {
    private final String name;
    private final String requestMessageType;
    private final String responseMessageType;

    public Service(String name, String requestMessageType, String responseMessageType) {
        if (StringUtils.isBlank(name)) {
            throw new DMNRuntimeException(String.format("Mandatory proto service name. Found '%s'", name));
        }
        this.name = name;
        this.requestMessageType = requestMessageType;
        this.responseMessageType = responseMessageType;
    }

    public String getName() {
        return name;
    }

    public String getRequestMessageType() {
        return requestMessageType;
    }

    public String getResponseMessageType() {
        return responseMessageType;
    }
}
