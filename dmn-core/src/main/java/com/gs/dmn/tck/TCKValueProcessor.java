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
package com.gs.dmn.tck;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.el.analysis.semantics.type.Type;
import com.gs.dmn.feel.lib.StandardFEELLib;
import com.gs.dmn.tck.ast.AnySimpleType;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;
import com.gs.dmn.transformation.native_.NativeFactory;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

public class TCKValueProcessor<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    protected final BasicDMNToNativeTransformer<Type, DMNContext> transformer;
    protected final StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib;
    protected final DMNModelRepository repository;
    protected final NativeFactory nativeFactory;

    public TCKValueProcessor(BasicDMNToNativeTransformer<Type, DMNContext> transformer, StandardFEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> feelLib) {
        this.transformer = transformer;
        this.feelLib = feelLib;
        this.repository = transformer.getDMNModelRepository();
        this.nativeFactory = this.transformer.getNativeFactory();
    }

    protected String getTextContent(Object value) {
        if (value instanceof String string) {
            return string;
        } else if (value instanceof Number) {
            return this.feelLib.string(value);
        } else if (value instanceof Boolean) {
            return value.toString();
        } else if (value instanceof XMLGregorianCalendar) {
            return this.feelLib.string(value);
        } else if (value instanceof Duration) {
            return this.feelLib.string(value);
        } else if (value instanceof org.w3c.dom.Element element) {
            return element.getTextContent();
        } else if (value instanceof AnySimpleType type) {
            return type.getText();
        } else {
            return null;
        }
    }

    protected Object anySimpleTypeValue(Object value) {
        if (value instanceof AnySimpleType type) {
            return type.getValue();
        }
        return value;
    }
}
