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
package com.gs.dmn.generated.npe_validation_2;

import com.gs.dmn.generated.AbstractHandwrittenDecisionTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandwrittenZipTest extends AbstractHandwrittenDecisionTest {
    private final Zip decision = new Zip();

    private final Zip zip = new Zip();

    @Test
    public void testCase1() {
        Number month = decision.number("12");
        Number day = decision.number("25");
        List<String> names = decision.asList("Fred", "Jim", "Tom", "Sarah", "Kate");
        Number hour = decision.number("8");
        Number year = decision.number("2016");
        Number second = decision.number("10");
        List<Number> ages = decision.asList(
                decision.number("25"), decision.number("40"), decision.number("65"), decision.number("80"), decision.number("105")
        );
        Number minute = decision.number("5");
        List<?> zipOutput = applyDecision(ages, day, hour, minute, month, names, second, year);

        List<com.gs.dmn.generated.npe_validation_2.type.Zip> expectedOutput = decision.asList(new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("25"), "not exactly 1 to 5", decision.number("0"), decision.number("0"), "Fred", decision.number("12")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("40"), "non of the numbers 1 to 5", decision.number("0"), decision.number("0"), "Jim", decision.number("2016")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("65"), null, decision.number("0"), decision.number("0"), "Tom", decision.number("7")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("80"), null, decision.numericUnaryMinus(decision.number("359")), decision.numericUnaryMinus(decision.number("8612")), "Sarah", decision.number("25")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("105"), null, null, null, "Kate", decision.number("5")));
        checkValues(expectedOutput, zipOutput);
    }

    @Test
    public void testCase2() {
        Number month = decision.number("2");
        Number day = decision.number("1");
        List<String> names = decision.asList("John", "Amy", "Tim", "James", "Ewa");
        Number hour = decision.number("1");
        Number year = decision.number("3");
        Number second = decision.number("3");
        List<Number> ages = decision.asList(
                decision.number("3"), decision.number("4"), decision.number("5"), decision.number("2"), decision.number("1")
        );
        Number minute = decision.number("2");
        List<?> zipOutput = applyDecision(ages, day, hour, minute, month, names, second, year);

        List<com.gs.dmn.generated.npe_validation_2.type.Zip> expectedOutput = decision.asList(new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("3"), "not exactly 1 to 5", decision.number("0"), decision.number("0"), "John", decision.number("2")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("4"), "only numbers between 1 and 5", decision.number("0"), decision.number("0"), "Amy", decision.number("3")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("5"), "at least one number betwen 1 and 5", decision.number("0"), decision.number("0"), "Tim", decision.number("6")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("2"), "only numbers between 1 and 5", decision.number("735202"), decision.number("17644858"), "James", decision.number("1")), new com.gs.dmn.generated.npe_validation_2.type.ZipImpl(decision.number("1"), null, null, null, "Ewa", decision.number("2")));
        checkValues(expectedOutput, zipOutput);
    }

    private void checkValues(Object expected, Object actual) {
        assertEquals(expected == null ? null : expected.toString(), actual == null ? null : actual.toString());
    }

    @Override
    protected void applyDecision() {
        List<Number> ages = decision.asList(
                decision.number("3"),
                decision.number("4"),
                decision.number("5"),
                decision.number("2"),
                decision.number("1")
        );
        Number day = decision.number("1");
        Number hour = decision.number("1");
        Number minute = decision.number("2");
        Number month = decision.number("2");
        List<String> names = decision.asList("John", "Amy", "Tim", "James", "Eva");
        Number second = decision.number("3");
        Number year = decision.number("3");
        applyDecision(ages, day, hour, minute, month, names, second, year);
    }

    private List<?> applyDecision(List<Number> ages, Number day, Number hour, Number minute, Number month, List<String> names, Number second, Number year) {
        return zip.apply(ages, day, hour, minute, month, names, second, year, context);
    }
}