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
package com.gs.dmn.feel.interpreter;

import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.runtime.interpreter.DMNInterpreter;

class StandardFEELInterpreterVisitor<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractFEELInterpreterVisitor<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    StandardFEELInterpreterVisitor(DMNInterpreter<NUMBER, DATE, TIME, DATE_TIME, DURATION> dmnInterpreter) {
        super(dmnInterpreter);
    }

    @Override
    protected Object evaluateDateTimeMember(Object source, String member) {
        StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib = (StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION>) getLib();
        if ("year".equals(member)) {
            return lib.year(source);
        } else if ("month".equals(member)) {
            return lib.month(source);
        } else if ("day".equals(member)) {
            return lib.day(source);
        } else if ("weekday".equals(member)) {
            return lib.weekday(source);
        } else if ("hour".equals(member)) {
            return lib.hour(source);
        } else if ("minute".equals(member)) {
            return lib.minute(source);
        } else if ("second".equals(member)) {
            return lib.second(source);
        } else if ("time offset".equals(member)) {
            return lib.timeOffset(source);
        } else if ("timezone".equals(member)) {
            return lib.timezone(source);

        } else if ("years".equals(member)) {
            return lib.years((DURATION) source);
        } else if ("months".equals(member)) {
            return lib.months((DURATION) source);
        } else if ("days".equals(member)) {
            return lib.days((DURATION) source);
        } else if ("hours".equals(member)) {
            return lib.hours((DURATION) source);
        } else if ("minutes".equals(member)) {
            return lib.minutes((DURATION) source);
        } else if ("seconds".equals(member)) {
            return lib.seconds((DURATION) source);
        } else {
            handleError(String.format("Cannot resolve method '%s' for date time", member));
            return null;
        }
    }
}

