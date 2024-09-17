package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PersonImpl implements Person {
        private String firstName;
        private String lastName;
        private java.lang.Number id;
        private String gender;
        private java.time.LocalDate dateOfBirth;
        private java.time.temporal.TemporalAccessor timeOfBirth;
        private java.time.temporal.TemporalAccessor dateTimeOfBirth;
        private List<String> list;
        private Boolean married;

    public PersonImpl() {
    }

    public PersonImpl(java.time.LocalDate dateOfBirth, java.time.temporal.TemporalAccessor dateTimeOfBirth, String firstName, String gender, java.lang.Number id, String lastName, List<String> list, Boolean married, java.time.temporal.TemporalAccessor timeOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        this.setDateTimeOfBirth(dateTimeOfBirth);
        this.setFirstName(firstName);
        this.setGender(gender);
        this.setId(id);
        this.setLastName(lastName);
        this.setList(list);
        this.setMarried(married);
        this.setTimeOfBirth(timeOfBirth);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateOfBirth")
    public java.time.LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateOfBirth")
    public void setDateOfBirth(java.time.LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeOfBirth")
    public java.time.temporal.TemporalAccessor getDateTimeOfBirth() {
        return this.dateTimeOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateTimeOfBirth")
    public void setDateTimeOfBirth(java.time.temporal.TemporalAccessor dateTimeOfBirth) {
        this.dateTimeOfBirth = dateTimeOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("firstName")
    public String getFirstName() {
        return this.firstName;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("gender")
    public String getGender() {
        return this.gender;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("id")
    public java.lang.Number getId() {
        return this.id;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("id")
    public void setId(java.lang.Number id) {
        this.id = id;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("lastName")
    public String getLastName() {
        return this.lastName;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("list")
    public List<String> getList() {
        return this.list;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("list")
    public void setList(List<String> list) {
        this.list = list;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("married")
    public Boolean getMarried() {
        return this.married;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("married")
    public void setMarried(Boolean married) {
        this.married = married;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("timeOfBirth")
    public java.time.temporal.TemporalAccessor getTimeOfBirth() {
        return this.timeOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("timeOfBirth")
    public void setTimeOfBirth(java.time.temporal.TemporalAccessor timeOfBirth) {
        this.timeOfBirth = timeOfBirth;
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
