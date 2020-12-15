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
import org.omg.dmn.tck.marshaller._20160719.ValueType;
import org.omg.spec.dmn._20191111.model.TDRGElement;

public class InputNodeInfo extends NodeInfo {
    private final ValueType value;

    public InputNodeInfo(String rootModelName, String nodeName, DRGElementReference<? extends TDRGElement> reference, ValueType value) {
        super(rootModelName, nodeName, reference);
        this.value = value;
    }

    public ValueType getValue() {
        return value;
    }
}
