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

import javax.xml.datatype.Duration;

public class DefaultSignavioDurationComparator extends DefaultDurationComparator {
    public static DefaultSignavioDurationComparator COMPARATOR = new DefaultSignavioDurationComparator();

    protected DefaultSignavioDurationComparator() {
    }

    @Override
    public Boolean lessEqualThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.lessEqualThan(first, second);
        }
    }

    @Override
    public Boolean greaterEqualThan(Duration first, Duration second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.greaterEqualThan(first, second);
        }
    }
}
