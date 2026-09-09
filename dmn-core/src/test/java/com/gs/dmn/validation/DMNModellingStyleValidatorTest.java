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
package com.gs.dmn.validation;

import com.gs.dmn.error.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DMNModellingStyleValidatorTest extends AbstractValidatorTest {
    private final DMNModellingStyleValidator validator = new DMNModellingStyleValidator();

    @Test
    public void testValidateWhenCorrect() {
        validate(validator, tckResource("tck/1.2/cl3/0020-vacation-days/0020-vacation-days.dmn"), new ArrayList<>());
    }

    @Test
    public void testValidateWhenIncorrect() {
        List<String> expectedErrors = List.of(
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style', elementName = 'model-a'): Import name 'model-a' contains invalid characters. Import names should contain only alphanumeric characters and underscores, and should start with a letter.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style', elementName = 'model-b'): Import name 'model-b' contains invalid characters. Import names should contain only alphanumeric characters and underscores, and should start with a letter.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style', elementName = 'model-c'): Import name 'model-c' contains invalid characters. Import names should contain only alphanumeric characters and underscores, and should start with a letter.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style', elementName = 'person', elementId = 'person'): Complex type 'person' contains nested complex type 'address' which is not allowed. Complex types should be modelled separately.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style', elementName = 'UseImportedDecisionService'): DecisionService 'UseImportedDecisionService' should not expose any input decision.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): All context entries should be literal expressions. Context entry 'address' is a not a literal expression",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Cannot find model for namespace 'http://test.example.com/model-a'.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Cannot find model for namespace 'http://test.example.com/model-b'.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Cannot find model for namespace 'http://test.example.com/model-c'.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Model 'http://test.example.com/model-a' is imported but not used in model 'http://test.example.com/test-dmn-modelling-style'.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Model 'http://test.example.com/model-e' is used but not imported in model 'http://test.example.com/test-dmn-modelling-style'.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Model 'http://test.example.com/model-g' is used but not imported in model 'http://test.example.com/test-dmn-modelling-style'.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Model 'http://test.example.com/model-f' is used but not imported in model 'http://test.example.com/test-dmn-modelling-style'.",
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style', modelName = 'test-dmn-modelling-style', modelId = 'test-dmn-modelling-style'): Model 'model-d' is used but not imported in model 'http://test.example.com/test-dmn-modelling-style'."
        );
        validate(validator, resource("dmn/input/1.5/test-dmn-modelling-style.dmn"), expectedErrors);
    }

    @Test
    public void testValidateNamesWhenIncorrect() {
        List<String> expectedErrors = List.of(
                "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'test-dmn-modelling-style-names ', elementId = 'test-dmn-modelling-style-names'): Name 'test-dmn-modelling-style-names ' ends with a whitespace.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badImport.name'): Import name 'badImport.name' contains invalid characters. Import names should contain only alphanumeric characters and underscores, and should start with a letter.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'Bad Import Name'): Import name 'Bad Import Name' contains invalid characters. Import names should contain only alphanumeric characters and underscores, and should start with a letter.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'end space ', elementId = 'id03'): Name 'end space ' ends with a whitespace.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = ' start space', elementId = 'id02'): Name ' start space' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'string', elementId = 'it01'): Item definition name 'string' is a FEEL type name which is not allowed.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = '-invalidStart', elementId = 'i001'): Name '-invalidStart' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = '-invalidVarStart', elementId = 'i001var'): Name '-invalidVarStart' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'bad.name', elementId = 'd001'): Name 'bad.name' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badVar.name', elementId = 'decision1_var'): Name 'badVar.name' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badBKM.name', elementId = 'bkm001'): Name 'badBKM.name' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badBKM.name', elementId = 'bkm001'): Label 'Deprecated Label' is deprecated and should not be used.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badBKMVar.name', elementId = 'bkm001var'): Name 'badBKMVar.name' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badBKMVar.name', elementId = 'bkm001var'): Label 'Deprecated Variable Label' is deprecated and should not be used.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badDS.name', elementId = 'ds001'): Name 'badDS.name' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badDS.name', elementId = 'ds001'): DecisionService 'badDS.name' should have only one output decision.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'badDSVar.name', elementId = 'ds001var'): Name 'badDSVar.name' contains invalid characters. Names should contain only alphanumeric characters, underscores, dashes and spaces, and should start with an alphanumeric character.",
        "[WARNING] (namespace = 'http://test.example.com/test-dmn-modelling-style-names', modelName = 'test-dmn-modelling-style-names ', modelId = 'test-dmn-modelling-style-names', elementName = 'Bad Import Name'): Model '' is imported more than once in model 'http://test.example.com/test-dmn-modelling-style-names'."
        );
        validate(validator, resource("dmn/input/1.5/test-dmn-modelling-style-names.dmn"), expectedErrors);
    }

    @Test
    public void testValidateDefinitionsWhenNull() {
        List<ValidationError> actualErrors = validator.validate(null);
        assertTrue(actualErrors.isEmpty());
    }
}
