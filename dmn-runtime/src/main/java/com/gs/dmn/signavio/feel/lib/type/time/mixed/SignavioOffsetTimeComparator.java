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
package com.gs.dmn.signavio.feel.lib.type.time.mixed;

import com.gs.dmn.feel.lib.type.time.mixed.OffsetTimeComparator;
import org.slf4j.Logger;

import java.time.OffsetTime;

public class SignavioOffsetTimeComparator extends OffsetTimeComparator {
    public SignavioOffsetTimeComparator(Logger logger) {
        super(logger);
    }

    @Override
    public  Boolean lessThan(OffsetTime first, OffsetTime second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.lessThan(first, second);
        }
    }

    @Override
    public  Boolean greaterThan(OffsetTime first, OffsetTime second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.greaterThan(first, second);
        }
    }

    @Override
    public  Boolean lessEqualThan(OffsetTime first, OffsetTime second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.lessEqualThan(first, second);
        }
    }

    @Override
    public  Boolean greaterEqualThan(OffsetTime first, OffsetTime second) {
        if (first == null && second == null) {
            return null;
        } else {
            return super.greaterEqualThan(first, second);
        }
    }
}
