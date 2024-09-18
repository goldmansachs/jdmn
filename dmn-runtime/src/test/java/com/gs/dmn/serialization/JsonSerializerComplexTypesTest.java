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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gs.dmn.runtime.DMNRuntimeException;
import com.gs.dmn.runtime.Range;
import com.gs.dmn.serialization.data.Address;
import com.gs.dmn.serialization.data.AddressImpl;
import com.gs.dmn.serialization.data.Person;
import com.gs.dmn.serialization.data.PersonImpl;
import com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonSerializerComplexTypesTest {
    private final JavaTimeSignavioLib lib = new JavaTimeSignavioLib();
    private final String numberListListText = "[ [ 1, 2 ] ]";
    private final List<Range> rangeList = new ArrayList<>(Collections.singletonList(new Range(true, 0, false, 1)));
    private final List<Address> addressList = new ArrayList<>(Collections.singletonList(new AddressImpl("line", "post code")));

    @Test
    public void testPersonSerialization() throws Exception {
        PersonImpl person = new PersonImpl();
        person.setId(lib.number("38"));
        person.setFirstName("Amy");
        person.setLastName("Smith");
        person.setDateOfBirth(lib.date("2017-01-03"));
        person.setTimeOfBirth(lib.time("11:20:30Z"));
        person.setDateTimeOfBirth(lib.dateAndTime("2016-08-01T12:00:00Z"));
        person.setGender("female");
        person.setMarried(true);
        person.setList(Collections.singletonList("Late payment"));
        person.setDateTimeList(Collections.singletonList(lib.dateAndTime("2016-08-01T12:00:00Z")));
        person.setYearsAndMonthsDuration(lib.duration("P1Y1M"));
        person.setDaysAndTimeDuration(lib.duration("P2DT20H"));
        person.setAT("AT");
        person.setAt("at");
        person.setRanges(rangeList);
        person.setAddresses(addressList);

        String personText = readJson("expected/json/person.json");
        compareJson(personText, prettyPrinter().writeValueAsString(person));
    }

    @Test
    public void testPersonDeserialization() throws Exception {
        String personText = readJson("expected/json/person.json");
        Person person = readPerson(personText);

        assertTrue(lib.stringEqual("Amy", person.getFirstName()));
        assertTrue(lib.stringEqual("Smith", person.getLastName()));
        assertTrue(lib.numericEqual(lib.number("38"), person.getId()));
        assertTrue(lib.stringEqual("female", person.getGender()));
        assertTrue(lib.listEqual(Collections.singletonList("Late payment"), person.getList()));
        assertTrue(lib.booleanEqual(true, person.getMarried()));
        assertTrue(lib.dateEqual(lib.date("2017-01-03"), person.getDateOfBirth()));
        assertTrue(lib.timeEqual(lib.time("11:20:30Z"), person.getTimeOfBirth()));
        assertTrue(lib.dateTimeEqual(lib.dateAndTime("2016-08-01T12:00:00Z"), person.getDateTimeOfBirth()));
        assertEquals(Collections.singletonList(lib.dateAndTime("2016-08-01T12:00:00Z")), person.getDateTimeList());
        assertTrue(lib.durationEqual(lib.duration("P1Y1M"), person.getYearsAndMonthsDuration()));
        assertTrue(lib.durationEqual(lib.duration("P2DT20H"), person.getDaysAndTimeDuration()));
        assertTrue(lib.stringEqual("AT", person.getAT()));
        assertTrue(lib.stringEqual("at", person.getAt()));
        assertEquals(this.addressList, person.getAddresses());
        assertEquals(this.rangeList, person.getRanges());
    }

    @Test
    public void testListOfPersonSerialization() throws Exception {
        List<Person> personList = new ArrayList<>();
        personList.add(makePerson("1"));

        String listOfPersonText = readJson("expected/json/list-of-person.json");
        compareJson(listOfPersonText, prettyPrinter().writeValueAsString(personList));
    }

    @Test
    public void testListOfPersonDeserialization() throws Exception {
        String listOfPersonText = readJson("expected/json/list-of-person.json");
        List<Person> personList = readPersonList(listOfPersonText);

        assertEquals(1, personList.size());
        Person person = personList.get(0);
        assertTrue(lib.numericEqual(lib.number("1"), person.getId()));

        List<Address> addresses = person.getAddresses();
        assertEquals(2, addresses.size());
        assertTrue(new AddressImpl("11", null).equalTo(addresses.get(0)));
        assertEquals(new AddressImpl("12", null), addresses.get(1));
    }

    @Test
    public void testListOfListOfPersonSerialization() throws Exception {
        List<Person> personList = new ArrayList<>();
        personList.add(makePerson("1"));
        Set<List<Person>> personListList = Collections.singleton(personList);

        String listOfListOfPersonText = readJson("expected/json/list-of-list-of-person.json");
        compareJson(listOfListOfPersonText, prettyPrinter().writeValueAsString(personListList));
    }

    @Test
    public void testListOfListOfPersonDeserialization() throws Exception {
        String listOfListOfPersonText = readJson("expected/json/list-of-list-of-person.json");
        List<List<Person>> list = readPersonListList(listOfListOfPersonText);

        assertEquals(1, list.size());
        List<Person> personList = list.get(0);
        Person person = personList.get(0);
        assertTrue(lib.numericEqual(lib.number("1"), person.getId()));

        List<Address> addresses = person.getAddresses();
        assertEquals(2, addresses.size());
        assertTrue(new AddressImpl("11", null).equalTo(addresses.get(0)));
        assertEquals(new AddressImpl("12", null), addresses.get(1));
    }

    @Test
    public void testListOfListOfNumberSerialization() throws IOException {
        List<BigDecimal> numberList = new ArrayList<>();
        numberList.add(new BigDecimal("1"));
        numberList.add(new BigDecimal("2"));
        Set<List<BigDecimal>> numberListList = Collections.singleton(numberList);

        compareJson(numberListListText, prettyPrinter().writeValueAsString(numberListList));
    }

    @Test
    public void testListOfListOfNumberDeserialization() throws IOException {
        List<List<BigDecimal>> list = readNumberListList(numberListListText);

        assertEquals(1, list.size());
        List<BigDecimal> personList = list.get(0);
        assertEquals(new BigDecimal("1"), personList.get(0));
        assertEquals(new BigDecimal("2"), personList.get(1));
    }

    private Person makePerson(String id) {
        PersonImpl person = new PersonImpl();
        person.setId(lib.number(id));
        List<Address> addresses = new ArrayList<>();
        addresses.add(makeAddress(id + 1));
        addresses.add(makeAddress(id + 2));
        person.setAddresses(addresses);
        return person;
    }

    private Address makeAddress(String line) {
        return new AddressImpl(line, null);
    }

    private List<List<BigDecimal>> readNumberListList(String text) throws com.fasterxml.jackson.core.JsonProcessingException {
        return OBJECT_MAPPER.readValue(text, new TypeReference<List<List<BigDecimal>>>() {});
    }

    private Person readPerson(String personText) throws Exception {
        return OBJECT_MAPPER.readValue(personText, new TypeReference<Person>() {});
    }

    private List<Person> readPersonList(String personText) throws Exception {
        return OBJECT_MAPPER.readValue(personText, new TypeReference<List<Person>>() {});
    }

    private List<List<Person>> readPersonListList(String personText) throws Exception {
        return OBJECT_MAPPER.readValue(personText, new TypeReference<List<List<Person>>>() {});
    }

    private String readJson(String resourcePath) {
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(resourcePath).toURI());
            List<String> lines = Files.readAllLines(path);
            return String.join("\n", lines);
        } catch (Exception e) {
            throw new DMNRuntimeException(String.format("Cannot read resource '%s'", resourcePath), e);
        }
    }

    private void compareJson(String expectedValue, String actualValue) {
        expectedValue = expectedValue.replaceAll("\r", "");
        actualValue = actualValue.replaceAll("\r", "");
        assertEquals(expectedValue, actualValue);
    }

    private ObjectWriter prettyPrinter() {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
    }
}
