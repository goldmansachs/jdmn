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
package com.gs.dmn.ast;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "label",
        "otherAttributes",
        "description",
        "associationDirection",
        "sourceRef",
        "targetRef",
        "extensionElements"
})
public class TAssociation extends TArtifact implements Visitable {
    private TDMNElementReference sourceRef;
    private TDMNElementReference targetRef;
    private TAssociationDirection associationDirection;

    public TDMNElementReference getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(TDMNElementReference value) {
        this.sourceRef = value;
    }

    public TDMNElementReference getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(TDMNElementReference value) {
        this.targetRef = value;
    }

    public TAssociationDirection getAssociationDirectionField() {
        return associationDirection;
    }

    public TAssociationDirection getAssociationDirection() {
        if (associationDirection == null) {
            return TAssociationDirection.NONE;
        } else {
            return associationDirection;
        }
    }

    public void setAssociationDirection(TAssociationDirection value) {
        this.associationDirection = value;
    }

    @Override
    public <C, R> R accept(Visitor<C, R> visitor, C context) {
        return visitor.visit(this, context);
    }
}
