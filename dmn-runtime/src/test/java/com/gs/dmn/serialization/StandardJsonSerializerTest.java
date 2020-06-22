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
package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.DefaultFEELLib;
import com.gs.dmn.feel.lib.FEELLib;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;

public class StandardJsonSerializerTest extends AbstractJsonSerializerTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected FEELLib<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> makeFEELLib() {
        return new DefaultFEELLib();
    }

    @Override
    protected BigDecimal readNumber(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, BigDecimal.class);
    }

    @Override
    protected XMLGregorianCalendar readDate(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected XMLGregorianCalendar readTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected XMLGregorianCalendar readDateTime(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, XMLGregorianCalendar.class);
    }

    @Override
    protected Duration readDuration(String literal) throws Exception {
        return OBJECT_MAPPER.readValue(literal, Duration.class);
    }
}

