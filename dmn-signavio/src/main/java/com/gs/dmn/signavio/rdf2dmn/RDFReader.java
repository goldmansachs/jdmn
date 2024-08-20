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
package com.gs.dmn.signavio.rdf2dmn;

import com.gs.dmn.log.BuildLogger;
import org.apache.commons.lang3.time.StopWatch;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class RDFReader {
    protected final BuildLogger logger;

    public RDFReader(BuildLogger logger) {
        this.logger = logger;
    }

    public RDFModel readModel(String modelName, InputStream inputStream) throws Exception {
        this.logger.info(String.format("Reading model '%s'", modelName));
        StopWatch watch = new StopWatch();
        watch.start();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant
        dbf.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(inputStream);
        RDFModel rdfModel = new RDFModel(document);

        watch.stop();
        this.logger.info("RDF reading time: " + watch);

        return rdfModel;
    }
}
