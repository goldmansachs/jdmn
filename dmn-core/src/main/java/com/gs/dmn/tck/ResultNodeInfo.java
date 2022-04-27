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
import com.gs.dmn.ast.TDRGElement;
import org.omg.dmn.tck.marshaller._20160719.ValueType;

public class ResultNodeInfo extends NodeInfo {
    private final ValueType expectedValue;

    public ResultNodeInfo(String rootModelName, String nodeType, String nodeName, DRGElementReference<? extends TDRGElement> reference, ValueType expectedValue) {
        super(rootModelName, nodeType, nodeName, reference);
        this.expectedValue = expectedValue;
    }

    public ValueType getExpectedValue() {
        return expectedValue;
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
}
