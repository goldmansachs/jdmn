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
package com.gs.dmn.signavio.testlab;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.signavio.dialect.KotlinSignavioDMNDialectDefinition;
import com.gs.dmn.signavio.transformation.template.KotlinSignavioTreeTemplateProvider;
import com.gs.dmn.transformation.FileTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.template.TemplateProvider;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.nio.file.Path;

public abstract class AbstractTestLabToKotlinJUnitTransformerTest extends AbstractTestLabToJUnitTransformerTest<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration> {
    @Override
    protected DMNDialectDefinition<BigDecimal, XMLGregorianCalendar, XMLGregorianCalendar, XMLGregorianCalendar, Duration, TestLab> makeDialectDefinition() {
        return new KotlinSignavioDMNDialectDefinition();
    }

    @Override
    public TemplateProvider makeTemplateProvider() {
        return new KotlinSignavioTreeTemplateProvider();
    }

    @Override
    protected FileTransformer makeTransformer(Path inputModelPath, InputParameters inputParameters, BuildLogger logger) {
        return new TestLabToKotlinJUnitTransformer<>(makeDialectDefinition(), makeDMNValidator(logger), makeDMNTransformer(logger), makeTemplateProvider(), makeLazyEvaluationDetector(inputParameters, logger), makeTypeDeserializationConfigurer(logger), inputModelPath, inputParameters, logger);
    }
}
