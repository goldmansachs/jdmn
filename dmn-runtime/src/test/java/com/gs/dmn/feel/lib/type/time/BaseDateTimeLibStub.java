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
package com.gs.dmn.feel.lib.type.time;

import java.time.LocalDate;
import java.time.OffsetTime;

class BaseDateTimeLibStub extends BaseDateTimeLib {
    @Override
    protected boolean isTime(String literal) {
        return super.isTime(literal);
    }

    @Override
    protected boolean hasTime(String literal) {
        return super.hasTime(literal);
    }

    @Override
    protected boolean hasZoneId(String literal) {
        return super.hasZoneId(literal);
    }

    @Override
    protected boolean hasZoneOffset(String literal) {
        return super.hasZoneOffset(literal);
    }

    @Override
    protected String fixDateTimeFormat(String literal) {
        return super.fixDateTimeFormat(literal);
    }

    @Override
    protected LocalDate makeLocalDate(String literal) {
        return super.makeLocalDate(literal);
    }

    @Override
    protected OffsetTime makeOffsetTime(String literal) {
        return super.makeOffsetTime(literal);
    }
}
