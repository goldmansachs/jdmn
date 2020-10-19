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
package com.gs.dmn.feel.lib.type.numeric;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public abstract class BaseNumericLib<NUMBER> implements NumericLib<NUMBER> {
    @Override
    public NUMBER number(String from, String groupingSeparator, String decimalSeparator) {
        if (StringUtils.isBlank(from)) {
            return null;
        }
        if (! (" ".equals(groupingSeparator) || ".".equals(groupingSeparator) || ",".equals(groupingSeparator) || null == groupingSeparator)) {
            return null;
        }
        if (! (".".equals(decimalSeparator) || ",".equals(decimalSeparator) || null == decimalSeparator)) {
            return null;
        }
        if (groupingSeparator != null && groupingSeparator.equals(decimalSeparator)) {
            return null;
        }

        if (groupingSeparator != null) {
            if (groupingSeparator.equals(".")) {
                groupingSeparator = "\\" + groupingSeparator;
            }
            from = from.replaceAll(groupingSeparator, "");
        }
        if (decimalSeparator != null && !decimalSeparator.equals(".")) {
            from = from.replaceAll(decimalSeparator, ".");
        }
        return number(from);
    }

    //
    // List functions
    //
    @Override
    public NUMBER min(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return min(Arrays.asList(args));
    }

    @Override
    public NUMBER max(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return max(Arrays.asList(args));
    }

    @Override
    public NUMBER sum(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return sum(Arrays.asList(args));
    }

    @Override
    public NUMBER mean(Object... args) {
        if (args == null || args.length < 1) {
            return null;
        }

        return mean(Arrays.asList(args));
    }

    @Override
    public NUMBER product(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        return product(Arrays.asList(numbers));
    }

    @Override
    public NUMBER median(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        return median(Arrays.asList(numbers));
    }

    @Override
    public NUMBER stddev(Object... numbers) {
        if (numbers == null || numbers.length < 1) {
            return null;
        }

        return stddev(Arrays.asList(numbers));
    }

    @Override
    public List mode(Object... numbers) {
        if (numbers == null) {
            return null;
        }

        return mode(Arrays.asList(numbers));
    }
}
