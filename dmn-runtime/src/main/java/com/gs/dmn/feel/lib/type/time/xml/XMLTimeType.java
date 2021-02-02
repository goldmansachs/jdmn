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
package com.gs.dmn.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.BaseType;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class XMLTimeType extends BaseType {
    public boolean isDate(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATE;
    }

    public boolean isTime(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.TIME;
    }

    public boolean isDateTime(Object value) {
        return value instanceof XMLGregorianCalendar
                && ((XMLGregorianCalendar) value).getXMLSchemaType() == DatatypeConstants.DATETIME;
    }
}
