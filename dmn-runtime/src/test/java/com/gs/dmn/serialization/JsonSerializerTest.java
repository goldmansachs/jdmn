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
import com.gs.dmn.serialization.data.Address;
import com.gs.dmn.serialization.data.AddressImpl;
import com.gs.dmn.serialization.data.Person;
import com.gs.dmn.serialization.data.PersonImpl;
import com.gs.dmn.signavio.feel.lib.DefaultSignavioLib;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonSerializerTest {
    private final DefaultSignavioLib lib = new DefaultSignavioLib();
    private final String personText = "{\"514 AT\":\"at\",\"AT\":\"AT\",\"Addresses\":null,\"Date and Time List\":[\"2016-08-01T12:00:00Z\"],\"Date and Time of Birth\":\"2016-08-01T12:00:00Z\",\"Date of Birth\":\"2017-01-03\",\"Days and Time Duration\":\"P2DT20H\",\"First Name\":\"Amy\",\"Gender\":\"female\",\"ID\":38,\"Last Name\":\"Smith\",\"List\":[\"Late payment\"],\"Married\":true,\"Time of Birth\":\"11:20:30Z\",\"Years and Months Duration\":\"P1Y1M\"}";
    private final String listOfPersonText = "[{\"514 AT\":null,\"AT\":null,\"Addresses\":[{\"Line\":\"11\",\"Postcode\":\"11\"},{\"Line\":\"12\",\"Postcode\":\"12\"}],\"Date and Time List\":null,\"Date and Time of Birth\":null,\"Date of Birth\":null,\"Days and Time Duration\":null,\"First Name\":null,\"Gender\":null,\"ID\":1,\"Last Name\":null,\"List\":null,\"Married\":null,\"Time of Birth\":null,\"Years and Months Duration\":null}]";
    private final String listOfListOfPersonText = "[[{\"514 AT\":null,\"AT\":null,\"Addresses\":[{\"Line\":\"11\",\"Postcode\":\"11\"},{\"Line\":\"12\",\"Postcode\":\"12\"}],\"Date and Time List\":null,\"Date and Time of Birth\":null,\"Date of Birth\":null,\"Days and Time Duration\":null,\"First Name\":null,\"Gender\":null,\"ID\":1,\"Last Name\":null,\"List\":null,\"Married\":null,\"Time of Birth\":null,\"Years and Months Duration\":null}]]";
    private String numberListListText = "[[1,2]]";

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

        assertEquals(personText, OBJECT_MAPPER.writeValueAsString(person));
    }

    @Test
    public void testPersonDeserialization() throws Exception {
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
    }

    @Test
    public void testListOfPersonSerialization() throws Exception {
        List<Person> personList = new ArrayList<>();
        personList.add(makePerson("1"));

        assertEquals(listOfPersonText, OBJECT_MAPPER.writeValueAsString(personList));
    }

    @Test
    public void testListOfPersonDeserialization() throws Exception {
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

        assertEquals(listOfListOfPersonText, OBJECT_MAPPER.writeValueAsString(personListList));
    }

    @Test
    public void testListOfListOfPersonDeserialization() throws Exception {
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

        assertEquals(numberListListText, OBJECT_MAPPER.writeValueAsString(numberListList));
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
}
