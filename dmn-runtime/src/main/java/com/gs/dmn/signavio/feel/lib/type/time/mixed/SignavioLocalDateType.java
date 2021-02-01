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
package com.gs.dmn.signavio.feel.lib.type.time.mixed;

import com.gs.dmn.feel.lib.type.DateType;
import com.gs.dmn.feel.lib.type.time.mixed.LocalDateType;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import java.time.LocalDate;

public class SignavioLocalDateType extends LocalDateType implements DateType<LocalDate, Duration> {
    public SignavioLocalDateType(DatatypeFactory datatypeFactory) {
        super(datatypeFactory, new SignavioLocalDateComparator());
    }
}
