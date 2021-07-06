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

import javax.xml.namespace.QName;

public class DMNShape extends Shape {
    private DMNLabel dmnLabel;
    private DMNDecisionServiceDividerLine dmnDecisionServiceDividerLine;
    private QName dmnElementRef;
    private Boolean isListedInputData;
    private Boolean isCollapsed;

    public DMNLabel getDMNLabel() {
        return dmnLabel;
    }

    public void setDMNLabel(DMNLabel value) {
        this.dmnLabel = value;
    }

    public DMNDecisionServiceDividerLine getDMNDecisionServiceDividerLine() {
        return dmnDecisionServiceDividerLine;
    }

    public void setDMNDecisionServiceDividerLine(DMNDecisionServiceDividerLine value) {
        this.dmnDecisionServiceDividerLine = value;
    }

    public QName getDmnElementRef() {
        return dmnElementRef;
    }

    public void setDmnElementRef(QName value) {
        this.dmnElementRef = value;
    }

    public Boolean isIsListedInputData() {
        return isListedInputData;
    }

    public void setIsListedInputData(Boolean value) {
        this.isListedInputData = value;
    }

    public boolean isIsCollapsed() {
        if (isCollapsed == null) {
            return false;
        } else {
            return isCollapsed;
        }
    }

    public void setIsCollapsed(Boolean value) {
        this.isCollapsed = value;
    }
}
