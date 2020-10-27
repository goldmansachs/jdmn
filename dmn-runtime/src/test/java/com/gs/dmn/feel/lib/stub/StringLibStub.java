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
package com.gs.dmn.feel.lib.stub;

import com.gs.dmn.feel.lib.type.string.StringLib;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.List;

public class StringLibStub implements StringLib {
    @Override
    public String string(Object from) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean contains(String string, String match) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean startsWith(String string, String match) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean endsWith(String string, String match) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer stringLength(String string) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String substring(String string, Number startPosition) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String substring(String string, Number startPosition, Number length) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String upperCase(String string) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String lowerCase(String string) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String substringBefore(String string, String match) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String substringAfter(String string, String match) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String replace(String input, String pattern, String replacement, String flags) throws Exception {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean matches(String input, String pattern, String flags) throws Exception {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public List split(String string, String delimiter) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
