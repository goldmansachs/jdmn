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
import com.gs.dmn.log.BuildLogger;

public abstract class SimpleDMNDialectTransformer {
    protected final BuildLogger logger;
    protected final DMNVersion sourceVersion;
    protected final DMNVersion targetVersion;
    protected final DMNVersionTransformerVisitor visitor;

    public SimpleDMNDialectTransformer(BuildLogger logger, DMNVersion sourceVersion, DMNVersion targetVersion) {
        this.logger = logger;
        this.sourceVersion = sourceVersion;
        this.targetVersion = targetVersion;
        this.visitor = new DMNVersionTransformerVisitor(sourceVersion, targetVersion);
    }

    public TDefinitions transformDefinitions(TDefinitions sourceDefinitions) {
        logger.info(String.format("Transforming '%s' from DMN %s to DMN %s ...", sourceDefinitions.getName(), sourceVersion.getVersion(), targetVersion.getVersion()));
        if (this.sourceVersion != this.targetVersion) {
            sourceDefinitions.accept(visitor, null);
        }
        return sourceDefinitions;
    }
}
