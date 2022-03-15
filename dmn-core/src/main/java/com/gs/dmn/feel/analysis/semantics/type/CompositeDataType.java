/*
 * Copyright 2016 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use self file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.gs.dmn.feel.analysis.semantics.type;

import com.gs.dmn.el.analysis.semantics.type.Type;

import java.util.List;
import java.util.Set;

public interface CompositeDataType {
    static boolean equivalentTo(CompositeDataType self, Type other) {
        if (other instanceof ContextType) {
            Set<String> selfNames = self.getMembers();
            Set<String> otherNames = ((ContextType) other).getMembers();
            if (!selfNames.equals(otherNames)) {
                return false;
            }
            for (String name : selfNames) {
                Type selfType = self.getMemberType(name);
                Type otherType = ((ContextType) other).getMemberType(name);
                if (!com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(selfType, otherType)) {
                    return false;
                }
            }
            return true;
        } else if (other instanceof ItemDefinitionType) {
            Set<String> selfNames = self.getMembers();
            Set<String> otherNames = ((ItemDefinitionType) other).getMembers();
            if (!selfNames.equals(otherNames)) {
                return false;
            }
            for (String name : selfNames) {
                Type selfType = self.getMemberType(name);
                Type otherType = ((ItemDefinitionType) other).getMemberType(name);
                if (!com.gs.dmn.el.analysis.semantics.type.Type.equivalentTo(selfType, otherType)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    static boolean conformsTo(CompositeDataType self, Type other) {
        if (other instanceof ContextType) {
            Set<String> selfNames = self.getMembers();
            Set<String> otherNames = ((ContextType) other).getMembers();
            if (!selfNames.containsAll(otherNames)) {
                return false;
            }
            for (String name : otherNames) {
                Type selfType = self.getMemberType(name);
                Type otherType = ((ContextType) other).getMemberType(name);
                if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(selfType, otherType)) {
                    return false;
                }
            }
            return true;
        } else if (other instanceof ItemDefinitionType) {
            Set<String> selfNames = self.getMembers();
            Set<String> otherNames = ((ItemDefinitionType) other).getMembers();
            if (!selfNames.containsAll(otherNames)) {
                return false;
            }
            for (String name : otherNames) {
                Type selfType = self.getMemberType(name);
                Type otherType = ((ItemDefinitionType) other).getMemberType(name);
                if (!com.gs.dmn.el.analysis.semantics.type.Type.conformsTo(selfType, otherType)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    CompositeDataType addMember(String name, List<String> aliases, Type type);

    Set<String> getMembers();

    Type getMemberType(String name);

    List<String> getAliases(String name);
}
