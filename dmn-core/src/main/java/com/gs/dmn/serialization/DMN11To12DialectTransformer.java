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

import com.gs.dmn.ast.DMNVersionTransformerVisitor;
import com.gs.dmn.ast.TDefinitions;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.Pair;

import static com.gs.dmn.serialization.DMNVersion.DMN_11;
import static com.gs.dmn.serialization.DMNVersion.DMN_12;

public class DMN11To12DialectTransformer extends SimpleDMNDialectTransformer<TDefinitions<DMNContext>, TDefinitions<DMNContext>> {
    private final DMNVersionTransformerVisitor<DMNContext> visitor = new DMNVersionTransformerVisitor<>(DMN_11, DMN_12);

    public DMN11To12DialectTransformer(BuildLogger logger) {
        super(logger, DMN_11, DMN_12);
    }

    @Override
    public Pair<TDefinitions<DMNContext>, PrefixNamespaceMappings> transformDefinitions(TDefinitions<DMNContext> sourceDefinitions) {
        TDefinitions<DMNContext> definitions = transform(sourceDefinitions);
        return new Pair<>(definitions, this.prefixNamespaceMappings);
    }

    private TDefinitions<DMNContext> transform(TDefinitions<DMNContext> sourceDefinitions) {
        logger.info(String.format("Transforming '%s' from DMN 1.1 to DMN 1.2 ...", sourceDefinitions.getName()));
        sourceDefinitions.accept(visitor, null);
        return sourceDefinitions;
    }
}
