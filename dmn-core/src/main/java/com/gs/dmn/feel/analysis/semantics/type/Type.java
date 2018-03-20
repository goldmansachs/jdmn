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
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.runtime.DMNRuntimeException;

public abstract class Type {
    public abstract boolean equivalentTo(Type other);

    /*
        A type type1 conforms to a type type2 when an instance of type1 can be substituted at each place where an instance of type2 is expected
    */
    public boolean conformsTo(Type other) {
        return equivalentTo(other) || other == AnyType.ANY;
    }

    public void validate() {
        if (!isValid()) {
            throw new DMNRuntimeException(String.format("Illegal type '%s'", this));
        }
    }

    public abstract boolean isValid();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
