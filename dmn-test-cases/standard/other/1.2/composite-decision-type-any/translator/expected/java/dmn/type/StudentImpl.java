package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "Student"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class StudentImpl implements Student {
        private java.lang.Number age;
        private String classification;

    public StudentImpl() {
    }

    public StudentImpl(java.lang.Number age, String classification) {
        this.setAge(age);
        this.setClassification(classification);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("age")
    public java.lang.Number getAge() {
        return this.age;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("age")
    public void setAge(java.lang.Number age) {
        this.age = age;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("classification")
    public String getClassification() {
        return this.classification;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("classification")
    public void setClassification(String classification) {
        this.classification = classification;
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
