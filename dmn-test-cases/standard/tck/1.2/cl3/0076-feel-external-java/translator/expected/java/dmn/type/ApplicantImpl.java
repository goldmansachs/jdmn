package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "Applicant"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ApplicantImpl implements Applicant {
        private String name;
        private java.lang.Number age;

    public ApplicantImpl() {
    }

    public ApplicantImpl(java.lang.Number age, String name) {
        this.setAge(age);
        this.setName(name);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("age")
    public java.lang.Number getAge() {
        return this.age;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("age")
    public void setAge(java.lang.Number age) {
        this.age = age;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    public String getName() {
        return this.name;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("name")
    public void setName(String name) {
        this.name = name;
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
