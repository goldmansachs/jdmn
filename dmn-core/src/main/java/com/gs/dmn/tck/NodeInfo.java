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
package com.gs.dmn.tck;

import com.gs.dmn.DRGElementReference;
import org.omg.spec.dmn._20191111.model.TDRGElement;

public class NodeInfo {
    protected static final String INPUT_TYPE = "inputData";
    protected static final String BKM_TYPE = "bkm";
    protected static final String DS_TYPE = "decisionService";
    protected static final String DECISION_TYPE = "decision";

    protected final String rootModelName;
    protected final String nodeType;
    protected final String nodeName;
    protected final DRGElementReference<? extends TDRGElement> reference;

    public NodeInfo(String rootModelName, String nodeType, String nodeName, DRGElementReference<? extends TDRGElement> reference) {
        this.rootModelName = rootModelName;
        this.nodeType = nodeType;
        this.nodeName = nodeName;
        this.reference = reference;
    }

    public String getRootModelName() {
        return rootModelName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeType() {
        return nodeType;
    }

    public DRGElementReference<? extends TDRGElement> getReference() {
        return reference;
    }
}
