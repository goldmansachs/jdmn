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
package com.gs.dmn.transformation;

import com.gs.dmn.DMNModelRepository;
import com.gs.dmn.runtime.Pair;

import java.util.ArrayList;
import java.util.List;

public class CompositeDMNTransformer<T> implements DMNTransformer<T> {
    private final List<DMNTransformer> transformers = new ArrayList<>();

    public CompositeDMNTransformer(List<DMNTransformer> transformers) {
        if (transformers != null) {
            this.transformers.addAll(transformers);
        }
    }

    @Override
    public DMNModelRepository transform(DMNModelRepository repository) {
        for(DMNTransformer transformer: this.transformers) {
            repository = transformer.transform(repository);
        }
        return repository;
    }

    @Override
    public Pair<DMNModelRepository, T> transform(DMNModelRepository repository, T testCases) {
        Pair<DMNModelRepository, T> result = new Pair(repository, testCases);
        for(DMNTransformer transformer: this.transformers) {
            result = transformer.transform(result.getLeft(), result.getRight());
        }
        return result;
    }
}
