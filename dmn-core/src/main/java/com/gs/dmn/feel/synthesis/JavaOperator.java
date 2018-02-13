/**
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
package com.gs.dmn.feel.synthesis;

public class JavaOperator {
    public enum Associativity {
        LEFT_RIGHT, RIGHT_LEFT
    }

    public enum Notation {
        INFIX, FUNCTIONAL
    }

    private final String name;
    private final int cardinality;
    private final boolean commutative;
    private final Associativity associativity;
    private final Notation notation;

    public JavaOperator(String name, int cardinality, boolean commutative, Associativity associativity, Notation notation) {
        this.name = name;
        this.cardinality = cardinality;
        this.commutative = commutative;
        this.associativity = associativity;
        this.notation = notation;
    }

    public String getName() {
        return name;
    }

    public int getCardinality() {
        return cardinality;
    }

    public boolean isCommutative() {
        return commutative;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public Notation getNotation() {
        return notation;
    }
}
