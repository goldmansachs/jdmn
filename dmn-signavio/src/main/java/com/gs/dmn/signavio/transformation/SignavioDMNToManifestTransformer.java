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
package com.gs.dmn.signavio.transformation;

import com.gs.dmn.ast.TDRGElement;
import com.gs.dmn.ast.TDecision;
import com.gs.dmn.context.DMNContext;
import com.gs.dmn.log.BuildLogger;
import com.gs.dmn.runtime.metadata.ExtensionElement;
import com.gs.dmn.signavio.SignavioDMNModelRepository;
import com.gs.dmn.signavio.transformation.basic.BasicSignavioDMNToJavaTransformer;
import com.gs.dmn.transformation.DMNToManifestTransformer;
import com.gs.dmn.transformation.basic.BasicDMNToNativeTransformer;

import java.util.List;

public class SignavioDMNToManifestTransformer extends DMNToManifestTransformer {
    public SignavioDMNToManifestTransformer(BasicDMNToNativeTransformer<com.gs.dmn.el.analysis.semantics.type.Type, DMNContext> dmnTransformer, BuildLogger logger) {
        super(dmnTransformer, logger);
    }

    @Override
    protected List<ExtensionElement> getExtensions(TDecision decision) {
        return ((BasicSignavioDMNToJavaTransformer) this.dmnTransformer).makeMetadataExtensions(decision);
    }

    @Override
    protected String getDiagramId(TDRGElement element) {
        return ((SignavioDMNModelRepository) this.dmnModelRepository).getDiagramId(element);
    }

    @Override
    protected String getShapeId(TDRGElement element) {
        return ((SignavioDMNModelRepository) this.dmnModelRepository).getShapeId(element);
    }
}
