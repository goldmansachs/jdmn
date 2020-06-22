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

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.List;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = PersonImpl.class)
public interface Person extends com.gs.dmn.runtime.DMNType {
    static Person toPerson(Object other) {
        if (other == null) {
            return null;
        } else if (Person.class.isAssignableFrom(other.getClass())) {
            return (Person)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            PersonImpl result_ = new PersonImpl();
            result_.setDateOfBirth((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("dateOfBirth", "Date of Birth"));
            result_.setDateTimeOfBirth((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("dateTimeOfBirth", "Date and Time of Birth"));
            result_.setFirstName((String)((com.gs.dmn.runtime.Context)other).get("firstName", "First Name"));
            result_.setGender((String)((com.gs.dmn.runtime.Context)other).get("gender", "Gender"));
            result_.setId((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("id", "ID"));
            result_.setLastName((String)((com.gs.dmn.runtime.Context)other).get("lastName", "Last Name"));
            result_.setList((List<String>)((com.gs.dmn.runtime.Context)other).get("list", "List"));
            result_.setMarried((Boolean)((com.gs.dmn.runtime.Context)other).get("married", "Married"));
            result_.setTimeOfBirth((javax.xml.datatype.XMLGregorianCalendar)((com.gs.dmn.runtime.Context)other).get("timeOfBirth", "Time of Birth"));
            result_.setDateTimeList((List<XMLGregorianCalendar>)((com.gs.dmn.runtime.Context)other).get("dateTimeList", "Date and Time List"));
            result_.setYearsAndMonthsDuration((Duration)((com.gs.dmn.runtime.Context)other).get("yearsAndMonthsDuration", "Years and Months Duration"));
            result_.setDaysAndTimeDuration((Duration)((com.gs.dmn.runtime.Context)other).get("daysAndTimeDuration", "Days and Time Duration"));
            return result_;
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), PersonImpl.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("ID")
    java.math.BigDecimal getId();

    @com.fasterxml.jackson.annotation.JsonGetter("First Name")
    String getFirstName();

    @com.fasterxml.jackson.annotation.JsonGetter("Last Name")
    String getLastName();

    @com.fasterxml.jackson.annotation.JsonGetter("Date of Birth")
    javax.xml.datatype.XMLGregorianCalendar getDateOfBirth();

    @com.fasterxml.jackson.annotation.JsonGetter("Time of Birth")
    javax.xml.datatype.XMLGregorianCalendar getTimeOfBirth();

    @com.fasterxml.jackson.annotation.JsonGetter("Date and Time of Birth")
    javax.xml.datatype.XMLGregorianCalendar getDateTimeOfBirth();

    @com.fasterxml.jackson.annotation.JsonGetter("Gender")
    String getGender();

    @com.fasterxml.jackson.annotation.JsonGetter("Married")
    Boolean getMarried();

    @com.fasterxml.jackson.annotation.JsonGetter("List")
    List<String> getList();

    @com.fasterxml.jackson.annotation.JsonGetter("Date and Time List")
    List<XMLGregorianCalendar> getDateTimeList();

    @com.fasterxml.jackson.annotation.JsonGetter("Years and Months Duration")
    Duration getYearsAndMonthsDuration();

    @com.fasterxml.jackson.annotation.JsonGetter("Days and Time Duration")
    Duration getDaysAndTimeDuration();

    @com.fasterxml.jackson.annotation.JsonGetter("514 AT")
    String getAt();

    @com.fasterxml.jackson.annotation.JsonGetter("AT")
    String getAT();

    @com.fasterxml.jackson.annotation.JsonGetter("Addresses")
    List<Address> getAddresses();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("id", getId());
        context.put("firstName", getFirstName());
        context.put("lastName", getLastName());
        context.put("dateOfBirth", getDateOfBirth());
        context.put("timeOfBirth", getTimeOfBirth());
        context.put("dateTimeOfBirth", getDateTimeOfBirth());
        context.put("gender", getGender());
        context.put("married", getMarried());
        context.put("list", getList());
        context.put("dateTimeList", getDateTimeList());
        context.put("yearsAndMonthsDuration", getYearsAndMonthsDuration());
        context.put("daysAndTimeDuration", getDaysAndTimeDuration());
        context.put("at", getAt());
        context.put("aT", getAT());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonImpl person = (PersonImpl) o;
        if (getFirstName() != null ? !getFirstName().equals(person.getFirstName()) : person.getFirstName() != null) return false;
        if (getLastName() != null ? !getLastName().equals(person.getLastName()) : person.getLastName() != null) return false;
        if (getId() != null ? !getId().equals(person.getId()) : person.getId() != null) return false;
        if (getGender() != null ? !getGender().equals(person.getGender()) : person.getGender() != null) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(person.getDateOfBirth()) : person.getDateOfBirth() != null) return false;
        if (getTimeOfBirth() != null ? !getTimeOfBirth().equals(person.getTimeOfBirth()) : person.getTimeOfBirth() != null) return false;
        if (getDateTimeOfBirth() != null ? !getDateTimeOfBirth().equals(person.getDateTimeOfBirth()) : person.getDateTimeOfBirth() != null) return false;
        if (getList() != null ? !getList().equals(person.getList()) : person.getList() != null) return false;
        if (getMarried() != null ? !getMarried().equals(person.getMarried()) : person.getMarried() != null) return false;
        if (getDateTimeList() != null ? !getDateTimeList().equals(person.getDateTimeList()) : person.getDateTimeList() != null) return false;
        if (getYearsAndMonthsDuration() != null ? !getYearsAndMonthsDuration().equals(person.getYearsAndMonthsDuration()) : person.getYearsAndMonthsDuration() != null) return false;
        if (getDaysAndTimeDuration() != null ? !getDaysAndTimeDuration().equals(person.getDaysAndTimeDuration()) : person.getDaysAndTimeDuration() != null) return false;
        if (getAt() != null ? !getAt().equals(person.getAt()) : person.getAt() != null) return false;
        if (getAT() != null ? !getAT().equals(person.getAT()) : person.getAT() != null) return false;
        if (getAddresses() != null ? !getAddresses().equals(person.getAddresses()) : person.getAddresses() != null) return false;
        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getDateOfBirth() != null ? getDateOfBirth().hashCode() : 0);
        result = 31 * result + (getTimeOfBirth() != null ? getTimeOfBirth().hashCode() : 0);
        result = 31 * result + (getDateTimeOfBirth() != null ? getDateTimeOfBirth().hashCode() : 0);
        result = 31 * result + (getList() != null ? getList().hashCode() : 0);
        result = 31 * result + (getMarried() != null ? getMarried().hashCode() : 0);
        result = 31 * result + (getDateTimeList() != null ? getDateTimeList().hashCode() : 0);
        result = 31 * result + (getYearsAndMonthsDuration() != null ? getYearsAndMonthsDuration().hashCode() : 0);
        result = 31 * result + (getDaysAndTimeDuration() != null ? getDaysAndTimeDuration().hashCode() : 0);
        result = 31 * result + (getAt() != null ? getAt().hashCode() : 0);
        result = 31 * result + (getAT() != null ? getAT().hashCode() : 0);
        result = 31 * result + (getAddresses() != null ? getAddresses().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("ID=" + getId());
        result_.append(", First Name=" + getFirstName());
        result_.append(", Last Name=" + getLastName());
        result_.append(", Date of Birth=" + getDateOfBirth());
        result_.append(", Time of Birth=" + getTimeOfBirth());
        result_.append(", Date and Time of Birth=" + getDateTimeOfBirth());
        result_.append(", Gender=" + getGender());
        result_.append(", Married=" + getMarried());
        result_.append(", List=" + getList());
        result_.append(", Date and Time List=" + getDateTimeList());
        result_.append(", Years and Months Duration=" + getYearsAndMonthsDuration());
        result_.append(", Days and Time Duration=" + getDaysAndTimeDuration());
        result_.append(", 514 AT=" + getAt());
        result_.append(", AT=" + getAT());
        result_.append(", Addresses=" + getAddresses());
        result_.append("}");
        return result_.toString();
    }
}
