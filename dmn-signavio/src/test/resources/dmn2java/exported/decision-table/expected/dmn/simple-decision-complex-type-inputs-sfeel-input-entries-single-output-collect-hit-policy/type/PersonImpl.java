package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PersonImpl implements Person {
        private String firstName;
        private String lastName;
        private java.math.BigDecimal id;
        private String gender;
        private javax.xml.datatype.XMLGregorianCalendar dateOfBirth;
        private javax.xml.datatype.XMLGregorianCalendar timeOfBirth;
        private javax.xml.datatype.XMLGregorianCalendar dateTimeOfBirth;
        private List<String> list;
        private Boolean married;

    public PersonImpl() {
    }

    public PersonImpl(javax.xml.datatype.XMLGregorianCalendar dateOfBirth, javax.xml.datatype.XMLGregorianCalendar dateTimeOfBirth, String firstName, String gender, java.math.BigDecimal id, String lastName, List<String> list, Boolean married, javax.xml.datatype.XMLGregorianCalendar timeOfBirth) {
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
    public javax.xml.datatype.XMLGregorianCalendar getDateOfBirth() {
        return this.dateOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateOfBirth")
    public void setDateOfBirth(javax.xml.datatype.XMLGregorianCalendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dateTimeOfBirth")
    public javax.xml.datatype.XMLGregorianCalendar getDateTimeOfBirth() {
        return this.dateTimeOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dateTimeOfBirth")
    public void setDateTimeOfBirth(javax.xml.datatype.XMLGregorianCalendar dateTimeOfBirth) {
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
    public java.math.BigDecimal getId() {
        return this.id;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("id")
    public void setId(java.math.BigDecimal id) {
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
    public javax.xml.datatype.XMLGregorianCalendar getTimeOfBirth() {
        return this.timeOfBirth;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("timeOfBirth")
    public void setTimeOfBirth(javax.xml.datatype.XMLGregorianCalendar timeOfBirth) {
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
