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
package com.gs.dmn.serialization.data;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "address"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = AddressImpl.class)
public interface Address extends com.gs.dmn.runtime.DMNType {
    static Address toAddress(Object other) {
        if (other == null) {
            return null;
        } else if (Address.class.isAssignableFrom(other.getClass())) {
            return (Address)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            AddressImpl result_ = new AddressImpl();
            result_.setLine((String)((com.gs.dmn.runtime.Context)other).get("line", "Line"));
            result_.setPostcode((String)((com.gs.dmn.runtime.Context)other).get("postcode", "Postcode"));
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), AddressImpl.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Line")
    String getLine();

    @com.fasterxml.jackson.annotation.JsonGetter("Postcode")
    String getPostcode();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("line", getLine());
        context.put("postcode", getPostcode());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressImpl address = (AddressImpl) o;
        if (getLine() != null ? !getLine().equals(address.getLine()) : address.getLine() != null) return false;
        if (getPostcode() != null ? !getPostcode().equals(address.getPostcode()) : address.getPostcode() != null) return false;
        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (getLine() != null ? getLine().hashCode() : 0);
        result = 31 * result + (getPostcode() != null ? getPostcode().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Line=" + getLine());
        result_.append(", Psotcode=" + getPostcode());
        return result_.toString();
    }
}
