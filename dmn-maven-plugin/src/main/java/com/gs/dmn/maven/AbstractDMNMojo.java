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
package com.gs.dmn.maven;

import com.gs.dmn.dialect.DMNDialectDefinition;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.maven.configuration.components.DMNTransformerComponent;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.CompositeDMNTransformer;
import com.gs.dmn.transformation.DMNTransformer;
import com.gs.dmn.transformation.InputParameters;
import com.gs.dmn.transformation.NopDMNTransformer;
import com.gs.dmn.transformation.lazy.CompositeLazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import com.gs.dmn.transformation.template.TemplateProvider;
import com.gs.dmn.validation.CompositeDMNValidator;
import com.gs.dmn.validation.DMNValidator;
import com.gs.dmn.validation.NopDMNValidator;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractDMNMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> extends AbstractFileTransformerMojo<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> {
    @Parameter(required = false)
    public String[] dmnValidators;

    @Parameter(required = false)
    public DMNTransformerComponent[] dmnTransformers;

    @Parameter(required = false)
    public String[] lazyEvaluationDetectors;

    @Parameter(required = false, defaultValue = "com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer")
    public String typeDeserializationConfigurer;

    @Parameter(required = false)
    public Map<String, String> inputParameters;

    protected DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST> makeDialect(Class<?> dialectClass) throws InstantiationException, IllegalAccessException {
        try {
            return (DMNDialectDefinition<NUMBER, DATE, TIME, DATE_TIME, DURATION, TEST>) dialectClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot instantiate dialect '%s'", dialectClass == null ? null : dialectClass.getName()));
        }
    }

    protected DMNValidator makeDMNValidator(String[] dmnValidatorClassNames, BuildLogger logger) throws Exception {
        if (dmnValidatorClassNames == null) {
            return new NopDMNValidator();
        }
        List<DMNValidator> dmnValidators = new ArrayList<>();
        for(String dmnValidatorClassName: dmnValidatorClassNames) {
            Class<?> dmnValidatorClass = Class.forName(dmnValidatorClassName);
            try {
                dmnValidators.add((DMNValidator) dmnValidatorClass.getConstructor(new Class[]{BuildLogger.class}).newInstance(new Object[]{logger}));
            } catch (Exception e) {
                dmnValidators.add((DMNValidator) dmnValidatorClass.getDeclaredConstructor().newInstance());
            }
        }
        return new CompositeDMNValidator(dmnValidators);
    }

    protected DMNTransformer<TEST> makeDMNTransformer(DMNTransformerComponent[] dmnTransformerComponents, BuildLogger logger) throws Exception {
        if (dmnTransformerComponents == null) {
            return new NopDMNTransformer<TEST>();
        }
        List<DMNTransformer<TEST>> dmnTransformers = new ArrayList<>();
        for(DMNTransformerComponent dmnTransformerComponent : dmnTransformerComponents) {
            DMNTransformer<TEST> transformer;
            Class<?> dmnTransformerClass = Class.forName(dmnTransformerComponent.getName());
            try {
                transformer = (DMNTransformer<TEST>) dmnTransformerClass.getConstructor(new Class[]{BuildLogger.class}).newInstance(new Object[]{logger});
            } catch (Exception e) {
                transformer = (DMNTransformer<TEST>) dmnTransformerClass.getDeclaredConstructor().newInstance();
            }

            transformer.configure(dmnTransformerComponent.getConfiguration());
            dmnTransformers.add(transformer);
        }
        return new CompositeDMNTransformer<TEST>(dmnTransformers);
    }

    protected LazyEvaluationDetector makeLazyEvaluationDetector(String[] detectorClassNames, BuildLogger logger, InputParameters inputParameters) throws Exception {
        if (detectorClassNames == null) {
            return new NopLazyEvaluationDetector();
        }
        List<LazyEvaluationDetector> detectors = new ArrayList<>();
        for(String detectorClassName: detectorClassNames) {
            Class<?> detectorClass = Class.forName(detectorClassName);
            try {
                detectors.add((LazyEvaluationDetector) detectorClass.getConstructor(new Class[]{InputParameters.class, BuildLogger.class}).newInstance(new Object[]{inputParameters, logger}));
            } catch (Exception e) {
                detectors.add((LazyEvaluationDetector) detectorClass.getDeclaredConstructor().newInstance());
            }
        }
        return new CompositeLazyEvaluationDetector(detectors);
    }

    protected TypeDeserializationConfigurer makeTypeDeserializationConfigurer(String deserializerClassName, BuildLogger logger) throws Exception {
        if (deserializerClassName == null) {
            return new DefaultTypeDeserializationConfigurer();
        }

        Class<?> deserializerClass = Class.forName(deserializerClassName);
        try {
            return (TypeDeserializationConfigurer)deserializerClass.getConstructor(new Class[]{BuildLogger.class}).newInstance(new Object[]{logger});
        }
        catch (Exception ex) {
            return (TypeDeserializationConfigurer)deserializerClass.getDeclaredConstructor().newInstance();
        }
    }

    protected TemplateProvider makeTemplateProvider(String templateProviderClassName, BuildLogger logger) throws Exception {
        Class<?> templateProviderClass = Class.forName(templateProviderClassName);
        try {
            return (TemplateProvider) templateProviderClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Cannot build template provider '%s'", templateProviderClass));
        }
    }

    @Override
    protected InputParameters makeInputParameters() {
        return new InputParameters(this.inputParameters);
    }
}
