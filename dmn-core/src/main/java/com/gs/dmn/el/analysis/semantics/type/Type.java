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
package com.gs.dmn.el.analysis.semantics.type;

import com.gs.dmn.runtime.DMNRuntimeException;

import java.util.List;

import static com.gs.dmn.el.analysis.semantics.type.AnyType.ANY;

public interface Type {
    //
    // Undefined types
    //
    static boolean isNull(Type type) {
        return type == null;
    }

    static boolean isNullType(Type type) {
        return type == NullType.NULL;
    }

    static boolean isAny(Type type) {
        return type == AnyType.ANY;
    }

    static boolean isNullOrAny(Type type) {
        return isNull(type) || isAny(type);
    }

    static boolean isNullOrAnyType(Type type) {
        return isNull(type) || isNullType(type) || isAny(type);
    }

    static boolean isNullOrAny(String typeRef) {
        return typeRef == null || "Any".equals(typeRef);
    }

    static boolean hasConstraint(Type type) {
        return type instanceof ConstraintType;
    }

    static Type extractTypeFromConstraint(Type type) {
        if (hasConstraint(type)) {
            type = ((ConstraintType) type).getType();
        }
        return type;
    }

    /*
            A type type1 is equivalent to type type2 when the types are either structurally or name equivalent. The types are compatible without coercion
    */
    static boolean equivalentTo(Type type1, Type type2) {
        type1 = extractTypeFromConstraint(type1);
        type2 = extractTypeFromConstraint(type2);
        if (type1 == null) {
            return type2 == null;
        } else {
            return type1.equivalentTo(type2);
        }
    }

    /*
        A type type1 conforms to type type2 when an instance of type1 can be substituted at each place where an instance of type2 is expected
    */
    static boolean conformsTo(Type type1, Type type2) {
        type1 = extractTypeFromConstraint(type1);
        type2 = extractTypeFromConstraint(type2);
        if (type2 == null || type2 == ANY) {
            return true;
        } else if (type1 == null) {
            return false;
        } else {
            return type1.conformsTo(type2);
        }
    }

    static boolean equivalentTo(List<Type> list1, List<Type> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!Type.equivalentTo(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    static boolean conformsTo(List<Type> list1, List<Type> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list1.size(); i++) {
            if (!Type.conformsTo(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

    // Check only types that share the same class
    boolean equivalentTo(Type other);

    // Check only types that share the same class
    boolean conformsTo(Type other);

    default void validate() {
        if (!isFullySpecified()) {
            throw new DMNRuntimeException("Type '%s' is partially specified".formatted(this));
        }
    }

    boolean isFullySpecified();

    default String serialize() {
        return this.toString();
    }
}
