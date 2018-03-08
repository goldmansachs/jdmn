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
package com.gs.dmn.feel.lib.type.string;

import com.gs.dmn.feel.lib.type.BaseType;
import com.gs.dmn.feel.lib.type.StringType;
import org.slf4j.Logger;

public class DefaultStringType extends BaseType implements StringType {
    public DefaultStringType(Logger logger) {
        super(logger);
    }

    @Override
    public Boolean stringEqual(String first, String second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return false;
        } else if (second == null) {
            return false;
        } else {
            int result = first.compareTo(second);
            return result == 0;
        }
    }

    @Override
    public Boolean stringNotEqual(String first, String second) {
        Boolean equal = stringEqual(first, second);
        return equal == null ? null : !equal;
    }

    @Override
    public String stringAdd(String first, String second) {
        if (first == null && second == null) {
            return "";
        } else if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        } else {
            return first + second;
        }
    }

    @Override
    public Boolean stringLessThan(String first, String second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result < 0;
        }
    }

    @Override
    public Boolean stringGreaterThan(String first, String second) {
        if (first == null && second == null) {
            return null;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result > 0;
        }
    }

    @Override
    public Boolean stringLessEqualThan(String first, String second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result <= 0;
        }
    }

    @Override
    public Boolean stringGreaterEqualThan(String first, String second) {
        if (first == null && second == null) {
            return true;
        } else if (first == null) {
            return null;
        } else if (second == null) {
            return null;
        } else {
            int result = first.compareTo(second);
            return result >= 0;
        }
    }
}
