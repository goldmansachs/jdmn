/**
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

public class DMNConstants {
    public static final String DMN_FILE_EXTENSION = ".dmn";

    // DMN 1.1
    public static final String DMN_11_NS = "http://www.omg.org/spec/DMN/20151101/dmn.xsd";
    public static final String DMN_11_PACKAGE = "org.omg.spec.dmn._20151101.model";
    public static final String FEEL_11_NS = "http://www.omg.org/spec/FEEL/20140401";
    public static final String FEEL_11_PREFIX = "feel";

    // DMN 1.2
    public static final String DMN_12_NS = "http://www.omg.org/spec/DMN/20180521/MODEL/";
    public static final String DMN_12_PREFIX = "dmn";
    public static final String DMN_12_DI_NS = "http://www.omg.org/spec/DMN/20180521/DI/";
    public static final String DMN_12_DI_PREFIX = "di";
    public static final String DMN_12_DMNDI_NS = "http://www.omg.org/spec/DMN/20180521/DMNDI/";
    public static final String DMN_12_DMNDI_PREFIX = "dmndi";
    public static final String DMN_12_DC_NS = "http://www.omg.org/spec/DMN/20180521/DC/";
    public static final String DMN_12_DC_PREFIX = "dc";
    public static final String DMN_12_PACKAGE = "org.omg.spec.dmn._20180521.model";
    public static final String FEEL_12_NS = "http://www.omg.org/spec/DMN/20180521/FEEL/";
    public static final String FEEL_12_PREFIX = "feel";

    // TCK
    public static final String TCK_NS = "http://www.omg.org/spec/DMN/20160719/testcase";
    public static final String TCK_PACKAGE = "org.omg.dmn.tck.marshaller._20160719";

    // XSD
    public static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
    public static final String XSD_PREFIX = "xsd";
}
