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
package com.gs.dmn.transformation.formatter;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.JavaFormatterOptions;
import com.gs.dmn.runtime.DMNRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GoogleJavaFormatter implements JavaFormatter {
    private static final Formatter FORMATTER;

    static {
        try {
            JavaFormatterOptions options = JavaFormatterOptions.builder().style(JavaFormatterOptions.Style.GOOGLE).build();
            FORMATTER = new Formatter(options);
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot set max length in formatter", e);
        }
    }

    @Override
    public String formatSource(String code) {
        try {
            return FORMATTER.formatSource(code);
        } catch (FormatterException e) {
            throw new DMNRuntimeException("Failed to format java", e);
        }
    }
}
