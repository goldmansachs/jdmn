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
package com.gs.dmn.transformation;

import com.gs.dmn.serialization.DMNConstants;
import com.gs.dmn.serialization.SerializationFormat;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InputParametersTest {
    @Test
    public void testGetRequiredParamWherePresent() {
        Map<String, String> params = new HashMap<String, String>() {{
            put("paramKey", "paramValue");
        }};
        assertEquals("paramValue", InputParameters.getRequiredParam(params, "paramKey"));
    }

    @Test
    public void testGetRequiredParamWhereNotPresent() {
        Map<String, String> params = new HashMap<>();
        try {
            InputParameters.getRequiredParam(params, "paramKey");
            fail();
        } catch (RuntimeException e) {
            assertEquals("A 'paramKey' parameter is required.", e.getMessage());
        }
    }

    @Test
    public void testGetOptionalParamWherePresent() {
        Map<String, String> params = new HashMap<String, String>() {{
            put("paramKey", "paramValue");
        }};
        assertEquals("paramValue", InputParameters.getOptionalParam(params, "paramKey"));
    }

    @Test
    public void testGetOptionalParamWhereNotPresent() {
        Map<String, String> params = new HashMap<>();
        assertNull(InputParameters.getOptionalParam(params, "paramKey"));
    }

    @Test
    public void testGetOptionalBooleanParamWherePresent() {
        Map<String, String> params = new HashMap<String, String>() {{
            put("paramKey", "true");
        }};
        assertTrue(InputParameters.getOptionalBooleanParam(params, "paramKey"));
    }

    @Test
    public void testGetOptionalBooleanParamWhereNotPresent() {
        Map<String, String> params = new HashMap<>();
        assertFalse(InputParameters.getOptionalBooleanParam(params, "paramKey"));
    }

    @Test
    public void testDefaultValues() {
        Map<String, String> params = new HashMap<>();
        InputParameters inputParameters = new InputParameters(params);
        assertNull(inputParameters.getDmnVersion());
        assertNull(inputParameters.getModelVersion());
        assertNull(inputParameters.getPlatformVersion());

        assertFalse(inputParameters.isXsdValidation());
        assertNull(inputParameters.getSchemaNamespace());
        assertNull(inputParameters.getNamespace());
        assertNull(inputParameters.getPrefix());
        assertEquals(SerializationFormat.XML, inputParameters.getFormat());
        assertEquals(DMNConstants.DMN_FILE_EXTENSION, inputParameters.getDmnFileExtension());
        assertEquals(DMNConstants.TCK_FILE_EXTENSION, inputParameters.getTckFileExtension());
        assertEquals("UTF-8", inputParameters.getCharset().displayName());

        assertNull(inputParameters.getJavaRootPackage());
        assertFalse(inputParameters.isOnePackage());
        assertTrue(inputParameters.isSingletonInputData());
        assertFalse(inputParameters.isSingletonDecision());
        assertTrue(inputParameters.isStrongTyping());

        assertFalse(inputParameters.isCaching());
        assertEquals(1, inputParameters.getCachingThreshold());
        assertEquals(0.0, inputParameters.getSparsityThreshold());
        assertFalse(inputParameters.isParallelStream());

        assertFalse(inputParameters.isGenerateProtoMessages());
        assertFalse(inputParameters.isGenerateProtoServices());
        assertEquals("proto3", inputParameters.getProtoVersion());

        assertFalse(inputParameters.isMockTesting());
        assertFalse(inputParameters.isGenerateExtra());

        assertFalse(inputParameters.isCheckConstraints());
    }

    @Test
    public void testSetValues() {
        String dmnVersion = "dmnVersion";
        String modelVersion = "modelVersion";
        String platformVersion = "platformVersion";

        String xsdValidation = "true";
        String schemaNamespace = "schemaNamespace";
        String namespace = "namespace";
        String prefix = "prefix";
        String format = "JSON";
        String dmnFileExtension = "dmnFileExtension";
        String tckFileExtension = "tckFileExtension";
        String charset = "charset";

        String javaRootPackage = "javaRootPackage";
        String onePackage = "false";
        String singletonInputData = "false";
        String singletonDecision = "false";
        String strongTyping = "false";

        String caching = "false";
        String cachingThreshold = "0";
        String sparsityThreshold = "0.2";
        String parallelStream = "false";

        String generateProtoMessages = "true";
        String generateProtoServices = "true";
        String protoVersion = "protoVersion";

        String mockTesting = "true";
        String generateExtra = "true";

        String checkConstraints = "true";

        Map<String, String> params = new HashMap<String, String>() {{
            put("dmnVersion", dmnVersion);
            put("modelVersion", modelVersion);
            put("platformVersion", platformVersion);

            put("xsdValidation", xsdValidation);
            put("signavioSchemaNamespace", schemaNamespace);
            put("namespace", namespace);
            put("prefix", prefix);
            put("format", format);
            put("dmnFileExtension", dmnFileExtension);
            put("tckFileExtension", tckFileExtension);
            put("charset", charset);

            put("javaRootPackage", javaRootPackage);
            put("onePackage", onePackage);
            put("singletonInputData", singletonInputData);
            put("singletonDecision", singletonDecision);
            put("strongTyping", strongTyping);

            put("caching", caching);
            put("cachingThreshold", cachingThreshold);
            put("sparsityThreshold", sparsityThreshold);
            put("parallelStream", parallelStream);

            put("generateProtoMessages", generateProtoMessages);
            put("generateProtoServices", generateProtoServices);
            put("protoVersion", protoVersion);

            put("mockTesting", mockTesting);
            put("generateExtra", generateExtra);

            put("checkConstraints", checkConstraints);
        }};
        InputParameters inputParameters = new InputParameters(params);
        assertEquals(dmnVersion, inputParameters.getDmnVersion());
        assertEquals(modelVersion, inputParameters.getModelVersion());
        assertEquals(platformVersion, inputParameters.getPlatformVersion());

        assertTrue(inputParameters.isXsdValidation());
        assertEquals(schemaNamespace, inputParameters.getSchemaNamespace());
        assertEquals(namespace, inputParameters.getNamespace());
        assertEquals(prefix, inputParameters.getPrefix());
        assertEquals(SerializationFormat.JSON, inputParameters.getFormat());
        assertEquals(dmnFileExtension, inputParameters.getDmnFileExtension());
        assertEquals(tckFileExtension, inputParameters.getTckFileExtension());
        assertEquals("UTF-8", inputParameters.getCharset().displayName());

        assertEquals(javaRootPackage, inputParameters.getJavaRootPackage());
        assertFalse(inputParameters.isOnePackage());
        assertFalse(inputParameters.isSingletonInputData());
        assertFalse(inputParameters.isSingletonDecision());
        assertFalse(inputParameters.isStrongTyping());

        assertFalse(inputParameters.isCaching());
        assertEquals(0, inputParameters.getCachingThreshold());
        assertEquals(0.2, inputParameters.getSparsityThreshold());
        assertFalse(inputParameters.isParallelStream());

        assertTrue(inputParameters.isGenerateProtoMessages());
        assertTrue(inputParameters.isGenerateProtoServices());
        assertEquals("protoVersion", inputParameters.getProtoVersion());

        assertTrue(inputParameters.isMockTesting());
        assertTrue(inputParameters.isGenerateExtra());

        assertTrue(inputParameters.isCheckConstraints());
    }
}