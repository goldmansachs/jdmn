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
package com.gs.dmn.example_credit_decision;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@javax.annotation.Generated(value={"inputData.ftl", "person"})
public class Person {
    public static Person convert(Object other) {
        if (Person.class.isAssignableFrom(other.getClass())) {
            return (Person)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            Person result_ = new Person();
            result_.setFirstName((String)((com.gs.dmn.runtime.Context)other).get("firstName"));
            result_.setLastName((String)((com.gs.dmn.runtime.Context)other).get("lastName"));
            result_.setId((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("id"));
            result_.setGender((String)((com.gs.dmn.runtime.Context)other).get("gender"));
            result_.setDateOfBirth((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("dateOfBirth"));
            result_.setTimeOfBirth((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("timeOfBirth"));
            result_.setDateTimeOfBirth((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("dateTimeOfBirth"));
            result_.setList((List<String>)((com.gs.dmn.runtime.Context)other).get("list"));
            result_.setMarried((Boolean)((com.gs.dmn.runtime.Context)other).get("married"));
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Person.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonProperty("firstName")
    private String firstName;

    @com.fasterxml.jackson.annotation.JsonProperty("lastName")
    private String lastName;

    @com.fasterxml.jackson.annotation.JsonProperty("id")
    private java.math.BigDecimal id;

    @com.fasterxml.jackson.annotation.JsonProperty("gender")
    private String gender;

    @com.fasterxml.jackson.annotation.JsonProperty("dateOfBirth")
    private javax.xml.datatype.XMLGregorianCalendar dateOfBirth;

    @com.fasterxml.jackson.annotation.JsonProperty("timeOfBirth")
    private javax.xml.datatype.XMLGregorianCalendar timeOfBirth;

    @com.fasterxml.jackson.annotation.JsonProperty("dateTimeOfBirth")
    private javax.xml.datatype.XMLGregorianCalendar dateTimeOfBirth;

    @com.fasterxml.jackson.annotation.JsonProperty("list")
    private List<String> list;

    @com.fasterxml.jackson.annotation.JsonProperty("married")
    private Boolean married;

    @com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ")
    @com.fasterxml.jackson.annotation.JsonProperty("dateTimeList")
    private List<javax.xml.datatype.XMLGregorianCalendar> dateTimeList;

    @com.fasterxml.jackson.annotation.JsonProperty("yearsAndMonthsDuration")
    private Duration yearsAndMonthsDuration;

    @com.fasterxml.jackson.annotation.JsonProperty("daysAndTimeDuration")
    private Duration daysAndTimeDuration;

    public Person() {
    }

    public Person(javax.xml.datatype.XMLGregorianCalendar dateOfBirth, javax.xml.datatype.XMLGregorianCalendar dateTimeOfBirth, String firstName, String gender, java.math.BigDecimal id, String lastName, List<String> list, Boolean married, javax.xml.datatype.XMLGregorianCalendar timeOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.dateTimeOfBirth = dateTimeOfBirth;
        this.firstName = firstName;
        this.gender = gender;
        this.id = id;
        this.lastName = lastName;
        this.list = list;
        this.married = married;
        this.timeOfBirth = timeOfBirth;
    }

    public javax.xml.datatype.XMLGregorianCalendar getDateOfBirth() {
        return this.dateOfBirth;
    }
    public void setDateOfBirth(javax.xml.datatype.XMLGregorianCalendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public javax.xml.datatype.XMLGregorianCalendar getDateTimeOfBirth() {
        return this.dateTimeOfBirth;
    }
    public void setDateTimeOfBirth(javax.xml.datatype.XMLGregorianCalendar dateTimeOfBirth) {
        this.dateTimeOfBirth = dateTimeOfBirth;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public java.math.BigDecimal getId() {
        return this.id;
    }
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }
    public String getLastName() {
        return this.lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<String> getList() {
        return this.list;
    }
    public void setList(List<String> list) {
        this.list = list;
    }
    public Boolean getMarried() {
        return this.married;
    }
    public void setMarried(Boolean married) {
        this.married = married;
    }
    public javax.xml.datatype.XMLGregorianCalendar getTimeOfBirth() {
        return this.timeOfBirth;
    }
    public void setTimeOfBirth(javax.xml.datatype.XMLGregorianCalendar timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
    }

    public List<XMLGregorianCalendar> getDateTimeList() {
        return dateTimeList;
    }

    public void setDateTimeList(List<XMLGregorianCalendar> dateTimeList) {
        this.dateTimeList = dateTimeList;
    }

    public Duration getYearsAndMonthsDuration() {
        return yearsAndMonthsDuration;
    }

    public void setYearsAndMonthsDuration(Duration yearsAndMonthsDuration) {
        this.yearsAndMonthsDuration = yearsAndMonthsDuration;
    }

    public Duration getDaysAndTimeDuration() {
        return daysAndTimeDuration;
    }

    public void setDaysAndTimeDuration(Duration daysAndTimeDuration) {
        this.daysAndTimeDuration = daysAndTimeDuration;
    }
}
