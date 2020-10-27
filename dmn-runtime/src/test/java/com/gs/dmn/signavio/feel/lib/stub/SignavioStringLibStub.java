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
package com.gs.dmn.signavio.feel.lib.stub;

import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.signavio.feel.lib.type.string.SignavioStringLib;

import java.util.List;

public class SignavioStringLibStub implements SignavioStringLib {
    @Override
    public String stringAdd(String first, String second) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String concat(List<String> texts) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String mid(String text, Number start, Number numChar) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String left(String text, Number numChar) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String right(String text, Number numChar) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String text(Number num, String formatText) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Integer textOccurrences(String findText, String withinText) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean isAlpha(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean isAlphanumeric(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean isNumeric(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public Boolean isSpaces(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String lower(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String trim(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }

    @Override
    public String upper(String text) {
        throw new DMNRuntimeException("Not supported yet");
    }
}
