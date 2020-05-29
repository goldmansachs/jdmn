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
package com.gs.dmn.signavio.rdf2dmn.json.expression;

import com.gs.dmn.signavio.rdf2dmn.json.Context;
import com.gs.dmn.signavio.rdf2dmn.json.Visitor;

import java.util.List;

public class Reference extends Expression {
    private String id;
    private String shapeId;
    private List<String> pathElements;

    public String getId() {
        return id;
    }

    public String getShapeId() {
        return shapeId;
    }

    public List<String> getPathElements() {
        return pathElements;
    }

    @Override
    public String toString() {
        String list = String.join(", ", pathElements);
        return String.format("%s(%s, %s, %s)", this.getClass().getSimpleName(), id, shapeId, list);
    }

    @Override
    public String accept(Visitor visitor, Context params) {
        return visitor.visit(this, params);
    }

}
