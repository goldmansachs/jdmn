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
import com.gs.dmn.ast.*;

public class NodeInfo {
    protected static final String INPUT_TYPE = "inputData";
    protected static final String BKM_TYPE = "bkm";
    protected static final String DS_TYPE = "decisionService";
    protected static final String DECISION_TYPE = "decision";
    protected static final String OTHER_TYPE = "other";

    public static String nodeTypeFrom(DRGElementReference<? extends TDRGElement> reference) {
        if (reference != null) {
            TDRGElement element = reference.getElement();
            if (element instanceof TInputData) {
                return INPUT_TYPE;
            } else if (element instanceof TDecision) {
                return DECISION_TYPE;
            } else if (element instanceof TBusinessKnowledgeModel) {
                return BKM_TYPE;
            } else if (element instanceof TDecisionService) {
                return DS_TYPE;
            }
        }
        return OTHER_TYPE;
    }

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

    public boolean isInputData() {
        return INPUT_TYPE.equals(nodeType);
    }

    public boolean isDecision() {
        return DECISION_TYPE.equals(nodeType);
    }

    public boolean isBKM() {
        return BKM_TYPE.equals(nodeType);
    }

    public boolean isDS() {
        return DS_TYPE.equals(nodeType);
    }

    public DRGElementReference<? extends TDRGElement> getReference() {
        return reference;
    }
}
