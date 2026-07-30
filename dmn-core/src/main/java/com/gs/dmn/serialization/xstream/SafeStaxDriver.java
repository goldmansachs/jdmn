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
package com.gs.dmn.serialization.xstream;

import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;

public class SafeStaxDriver extends StaxDriver {
    private static final Logger LOGGER = LoggerFactory.getLogger(SafeStaxDriver.class);

    @Override
    protected XMLInputFactory createInputFactory() {
        final XMLInputFactory instance = XMLInputFactory.newInstance();

        // Disable DTD and external entity support to mitigate XXE-style parser attacks
        setPropertyIfSupported(instance, XMLInputFactory.SUPPORT_DTD, Boolean.FALSE);
        setPropertyIfSupported(instance, XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
        setPropertyIfSupported(instance, XMLConstants.ACCESS_EXTERNAL_DTD, "");
        setPropertyIfSupported(instance, XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

        return instance;
    }

    private void setPropertyIfSupported(XMLInputFactory instance, String propertyName, Object value) {
        try {
            instance.setProperty(propertyName, value);
        } catch (IllegalArgumentException e) {
            LOGGER.debug("XMLInputFactory property '{}' is not supported by implementation", propertyName);
        }
    }
}
