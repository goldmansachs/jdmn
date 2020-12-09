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

import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.serialization.DefaultTypeDeserializationConfigurer;
import com.gs.dmn.serialization.TypeDeserializationConfigurer;
import com.gs.dmn.transformation.lazy.LazyEvaluationDetector;
import com.gs.dmn.transformation.lazy.NopLazyEvaluationDetector;
import org.omg.dmn.tck.marshaller._20160719.TestCases;

import java.net.URI;

public abstract class AbstractTckDMNToNativeTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> extends AbstractDMNTransformerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION, TestCases> {
    @Override
    protected DMNTransformer<TestCases> makeDMNTransformer(BuildLogger logger) {
        return new ToSimpleNameTransformer(logger);
    }

    @Override
    protected LazyEvaluationDetector makeLazyEvaluationDetector(InputParameters inputParameters, BuildLogger logger) {
        return new NopLazyEvaluationDetector();
    }

    @Override
    protected TypeDeserializationConfigurer makeTypeDeserializationConfigurer(BuildLogger logger) {
        return new DefaultTypeDeserializationConfigurer();
    }

    @Override
    protected URI resource(String path) {
        return tckResource(path);
    }
}
