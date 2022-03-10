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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gs.dmn.ast.Visitable;
import com.gs.dmn.ast.Visitor;
import com.gs.dmn.context.DMNContext;

import javax.xml.namespace.QName;

@JsonPropertyOrder({
        "id",
        "sharedStyle",
        "otherAttributes",
        "style",
        "extension",
        "waypoint",
        "dmnLabel"
})
public class DMNEdge extends Edge implements Visitable {
    private DMNLabel dmnLabel;
    private QName dmnElementRef;
    private QName sourceElement;
    private QName targetElement;

    public DMNLabel getDMNLabel() {
        return dmnLabel;
    }

    public void setDMNLabel(DMNLabel value) {
        this.dmnLabel = value;
    }

    public QName getDmnElementRef() {
        return dmnElementRef;
    }

    public void setDmnElementRef(QName value) {
        this.dmnElementRef = value;
    }

    public QName getSourceElement() {
        return sourceElement;
    }

    public void setSourceElement(QName value) {
        this.sourceElement = value;
    }

    public QName getTargetElement() {
        return targetElement;
    }

    public void setTargetElement(QName value) {
        this.targetElement = value;
    }

    @Override
    public Object accept(Visitor visitor, DMNContext context) {
        return visitor.visit(this, context);
    }
}
