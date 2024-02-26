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
package com.gs.dmn.serialization;

import com.gs.dmn.feel.lib.FEELLib;
import com.gs.dmn.runtime.Assert;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractJsonSerializerTest<NUMBER, DATE, TIME, DATE_TIME, DURATION> {
    private final FEELLib<NUMBER, DATE, TIME, DATE_TIME, DURATION> lib = makeFEELLib();

    @Test
    public void testNumber() throws Exception {
        List<Pair<String, String>> pairs = getNumberTestData();

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = pair.getRight();
            String errorMessage = "Testing '%s'".formatted(literal);

            // Serialization
            NUMBER obj1 = lib.number(literal);
            checkSerialization(errorMessage, serialization, write(obj1));

            // Deserialization
            NUMBER obj2 = readNumber(serialization);
            Assert.assertEquals(errorMessage, obj1, obj2);

            // Round-trip serialization
            checkSerialization(errorMessage, serialization, write(obj2));
        }
    }

    @Test
    public void testDate() throws Exception {
        List<Pair<String, String>> pairs = getDateTestData();

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = "\"%s\"".formatted(pair.getRight());
            String errorMessage = "Testing '%s'".formatted(literal);

            // Serialization
            DATE obj1 = lib.date(literal);
            checkSerialization(errorMessage, serialization, write(obj1));

            // Deserialization
            DATE obj2 = readDate(serialization);
            Assert.assertEquals(errorMessage, obj1, obj2);

            // Round-trip serialization
            checkSerialization(errorMessage, serialization, write(obj2));
        }
    }

    @Test
    public void testTime() throws Exception {
        List<Pair<String, String>> pairs = getTimeTestData();

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = "\"%s\"".formatted(pair.getRight());
            String errorMessage = "Testing '%s'".formatted(literal);

            // Serialization
            TIME obj1 = lib.time(literal);
            checkSerialization(errorMessage, serialization, write(obj1));

            // Deserialization
            TIME obj2 = readTime(serialization);
            Assert.assertEquals(errorMessage, obj1, obj2);

            // Round-trip serialization
            checkSerialization(errorMessage, serialization, write(obj2));
        }
    }

    @Test
    public void testDateAndTime() throws Exception {
        List<Pair<String, String>> pairs = getDateTimeTestData();

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = "\"%s\"".formatted(pair.getRight());
            String errorMessage = "Testing '%s'".formatted(literal);

            // Serialization
            DATE_TIME obj1 = lib.dateAndTime(literal);
            checkSerialization(errorMessage, serialization, write(obj1));

            // Deserialization
            DATE_TIME obj2 = readDateTime(serialization);
            Assert.assertEquals(errorMessage, obj1, obj2);

            // Round-trip serialization
            checkSerialization(errorMessage, serialization, write(obj2));
        }
    }

    @Test
    public void testDuration() throws Exception {
        List<Pair<String, String>> pairs = getDurationTestData();

        for (Pair<String, String> pair : pairs) {
            String literal = pair.getLeft();
            String serialization = "\"%s\"".formatted(pair.getRight());
            String errorMessage = "Testing '%s'".formatted(literal);

            // Serialization
            DURATION obj1 = lib.duration(literal);
            checkSerialization(errorMessage, serialization, write(obj1));

            // Deserialization
            DURATION obj2 = readDuration(serialization);
            Assert.assertEquals(errorMessage, obj1, obj2);

            // Round-trip serialization
            checkSerialization(errorMessage, serialization, write(obj2));
        }
    }

    private void checkSerialization(String literal, String expectedSerialization, String actualSerialization) {
        assertEquals(expectedSerialization, actualSerialization, literal);
    }

    protected abstract FEELLib<NUMBER,DATE,TIME,DATE_TIME,DURATION> makeFEELLib();

    protected abstract NUMBER readNumber(String literal) throws Exception;
    protected abstract DATE readDate(String literal) throws Exception;
    protected abstract TIME readTime(String literal) throws Exception;
    protected abstract DATE_TIME readDateTime(String literal) throws Exception;
    protected abstract DURATION readDuration(String literal) throws Exception;

    protected String write(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            throw new DMNRuntimeException("Cannot serialize '%s'".formatted(obj), e);
        }
    }

    protected List<Pair<String, String>> getNumberTestData() {
        return Arrays.asList(
                new Pair<>("123", "123"),
                new Pair<>("-123", "-123"),
                new Pair<>("123.45", "123.45"),
                new Pair<>("-123.45", "-123.45")
        );
    }

    protected List<Pair<String, String>> getDateTestData() {
        return Arrays.asList(
                new Pair<>("2019-03-12", "2019-03-12"),
                new Pair<>("9999-12-31", "9999-12-31")
        );
    }

    protected abstract List<Pair<String, String>> getTimeTestData();

    protected abstract List<Pair<String, String>> getDateTimeTestData();

    protected List<Pair<String, String>> getDurationTestData() {
        return Arrays.asList(
                new Pair<>("P1Y2M", "P1Y2M"),
                new Pair<>("P1DT2H3M", "P1DT2H3M")
        );
    }
}