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

import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.error.ErrorFactory;
import com.gs.dmn.error.SemanticErrorException;
import com.gs.dmn.error.ValidationError;
import com.gs.dmn.feel.ModelLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class XSDSchemaValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(XSDSchemaValidator.class);
    static final String RULE_NAME = "xsd-validation";

    public List<ValidationError> validateXSDSchema(Source source, DMNVersion dmnVersion) {
        return validateXSDSchema(source, dmnVersion.getSchemaLocation());
    }

    private List<ValidationError> validateXSDSchema(Source source, String schemaPath) {
        try {
            Validator validator = makeValidator(schemaPath);
            XsdErrorHandler errorHandler = new XsdErrorHandler();
            validator.setErrorHandler(errorHandler);
            validator.validate(source);

            List<String> errors = new ArrayList<>();
            for (SAXParseException e : errorHandler.getExceptions()) {
                String errorMessage = String.format("Line %d, Column %d: %s", e.getLineNumber(), e.getColumnNumber(), e.getMessage());
                if (!errors.contains(errorMessage)) {
                    errors.add(errorMessage);
                }
            }
            ModelLocation modelLocation = makeModelLocation(source);
            return errors.stream().map(e -> new ValidationError(ErrorFactory.makeDMNError(modelLocation, e), RULE_NAME)).collect(Collectors.toList());
        } catch (Exception e) {
            String errorMessage = "Validation failed due to a critical error: " + e.getMessage();
            LOGGER.error(errorMessage);
            throw new SemanticErrorException(errorMessage, e);
        }
    }

    private ModelLocation makeModelLocation(Source source) {
        String systemId = source.getSystemId();
        ModelLocation modelLocation = null;
        if (systemId != null && systemId.endsWith(DMNConstants.DMN_FILE_EXTENSION)) {
            // Determine file name
            String fileName;
            int index = systemId.lastIndexOf("/");
            if (index != -1) {
                fileName = systemId.substring(index + 1);
            } else {
                fileName = systemId;
            }
            // Determine model name
            String modelName = fileName.substring(0, fileName.indexOf(DMNConstants.DMN_FILE_EXTENSION));
            // Make dummy model
            TDefinitions model = new TDefinitions();
            model.setName(modelName);

            modelLocation = new ModelLocation(model, null);
        }
        return modelLocation;
    }

    private Validator makeValidator(String schemaPath) throws MalformedURLException, URISyntaxException, SAXException {
        URL schemaURL = Objects.requireNonNull(XSDSchemaValidator.class.getClassLoader().getResource(schemaPath)).toURI().toURL();
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        // Prohibit the use of all protocols by external entities:
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");
        Schema schema = factory.newSchema(schemaURL);
        return schema.newValidator();
    }
}
