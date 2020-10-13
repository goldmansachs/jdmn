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
package com.gs.dmn.signavio.feel.lib.type.time.xml;

import com.gs.dmn.feel.lib.type.time.xml.DefaultDurationComparator;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.Duration;

public class DefaultSignavioDurationComparator extends DefaultDurationComparator {
    @Override
    public Boolean lessThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) == DatatypeConstants.LESSER;
        }
    }

    @Override
    public Boolean greaterThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            return compare(first, second) == DatatypeConstants.GREATER;
        }
    }

    @Override
    public Boolean lessEqualThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int compare = compare(first, second);
            return compare == DatatypeConstants.LESSER || compare == DatatypeConstants.EQUAL;
        }
    }

    @Override
    public Boolean greaterEqualThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int compare = compare(first, second);
            return compare == DatatypeConstants.GREATER || compare == DatatypeConstants.EQUAL;
        }
    }
}
