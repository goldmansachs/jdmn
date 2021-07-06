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
package com.gs.dmn.ast.dmndi;

import java.util.ArrayList;
import java.util.List;

public class DMNDI {
    private List<DMNDiagram> dmnDiagram;
    private List<DMNStyle> dmnStyle;

    public List<DMNDiagram> getDMNDiagram() {
        if (dmnDiagram == null) {
            dmnDiagram = new ArrayList<>();
        }
        return this.dmnDiagram;
    }

    public List<DMNStyle> getDMNStyle() {
        if (dmnStyle == null) {
            dmnStyle = new ArrayList<>();
        }
        return this.dmnStyle;
    }
}
