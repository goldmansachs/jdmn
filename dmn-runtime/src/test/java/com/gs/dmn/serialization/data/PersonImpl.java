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
package com.gs.dmn.serialization.data;

import com.gs.dmn.runtime.Range;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.List;

@javax.annotation.Generated(value={"inputData.ftl", "person"})
public class PersonImpl implements Person {
    private Number id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private TemporalAccessor timeOfBirth;
    private TemporalAccessor dateTimeOfBirth;
    private String gender;
    private Boolean married;
    private List<String> list;
    private List<TemporalAccessor> dateTimeList;
    private TemporalAmount yearsAndMonthsDuration;
    private TemporalAmount daysAndTimeDuration;
    private String at;
    private String aT;
    private List<Address> addresses;
    private List<Range> ranges;

    public PersonImpl() {
    }

    public PersonImpl(Number id, String firstName, String lastName, LocalDate dateOfBirth, TemporalAccessor timeOfBirth, TemporalAccessor dateTimeOfBirth, String gender, Boolean married, List<String> list, List<TemporalAccessor> dateTimeList, TemporalAmount yearsAndMonthsDuration, TemporalAmount daysAndTimeDuration, List<Address> addresses, List<Range> ranges) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.timeOfBirth = timeOfBirth;
        this.dateTimeOfBirth = dateTimeOfBirth;
        this.gender = gender;
        this.married = married;
        this.list = list;
        this.dateTimeList = dateTimeList;
        this.yearsAndMonthsDuration = yearsAndMonthsDuration;
        this.daysAndTimeDuration = daysAndTimeDuration;
        this.addresses = addresses;
        this.ranges = ranges;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("ID")
    public Number getId() {
        return this.id;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("ID")
    public void setId(Number id) {
        this.id = id;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("First Name")
    public String getFirstName() {
        return this.firstName;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("First Name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Last Name")
    public String getLastName() {
        return this.lastName;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Last Name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Date of Birth")
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Date of Birth")
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Time of Birth")
    public TemporalAccessor getTimeOfBirth() {
        return this.timeOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Time of Birth")
    public void setTimeOfBirth(TemporalAccessor timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Date and Time of Birth")
    public TemporalAccessor getDateTimeOfBirth() {
        return this.dateTimeOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Date and Time of Birth")
    public void setDateTimeOfBirth(TemporalAccessor dateTimeOfBirth) {
        this.dateTimeOfBirth = dateTimeOfBirth;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Gender")
    public String getGender() {
        return this.gender;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Married")
    public Boolean getMarried() {
        return this.married;
    }
    public void setMarried(Boolean married) {
        this.married = married;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("List")
    public List<String> getList() {
        return this.list;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("List")
    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Date and Time List")
    public List<TemporalAccessor> getDateTimeList() {
        return dateTimeList;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Date and Time List")
    public void setDateTimeList(List<TemporalAccessor> dateTimeList) {
        this.dateTimeList = dateTimeList;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Years and Months Duration")
    public TemporalAmount getYearsAndMonthsDuration() {
        return yearsAndMonthsDuration;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Years and Months Duration")
    public void setYearsAndMonthsDuration(TemporalAmount yearsAndMonthsDuration) {
        this.yearsAndMonthsDuration = yearsAndMonthsDuration;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Days and Time Duration")
    public TemporalAmount getDaysAndTimeDuration() {
        return daysAndTimeDuration;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Days and Time Duration")
    public void setDaysAndTimeDuration(TemporalAmount daysAndTimeDuration) {
        this.daysAndTimeDuration = daysAndTimeDuration;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("514 AT")
    public String getAt() {
        return this.at;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("514 AT")
    public void setAt(String at) {
        this.at = at;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("AT")
    public String getAT() {
        return this.aT;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("AT")
    public void setAT(String aT) {
        this.aT = aT;
    }

    @Override
    @com.fasterxml.jackson.annotation.JsonGetter("Addresses")
    public List<Address> getAddresses() {
        return this.addresses;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Addresses")
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public List<Range> getRanges() {
        return this.ranges;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Ranges")
    public void setRanges(List<Range> ranges) {
        this.ranges = ranges;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
