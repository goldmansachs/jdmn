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

@javax.annotation.Generated(value={"inputData.ftl", "person"})
public class AddressImpl implements Address {
    private String line;
    private String postcode;

    public AddressImpl() {
    }

    public AddressImpl(String line, String postcode) {
        this.line = line;
        this.postcode = postcode;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Line")
    public String getLine() {
        return this.line;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Line")
    public void setLine(String line) {
        this.line = line;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Postcode")
    public String getPostcode() {
        return this.line;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Postcode")
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
