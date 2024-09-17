package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "applicant"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ApplicantImpl implements Applicant {
        private String name;
        private java.lang.Number age;
        private java.lang.Number creditScore;
        private List<String> priorIssues;

    public ApplicantImpl() {
    }

    public ApplicantImpl(java.lang.Number age, java.lang.Number creditScore, String name, List<String> priorIssues) {
        this.setAge(age);
        this.setCreditScore(creditScore);
        this.setName(name);
        this.setPriorIssues(priorIssues);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Age")
    public java.lang.Number getAge() {
        return this.age;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Age")
    public void setAge(java.lang.Number age) {
        this.age = age;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Credit score")
    public java.lang.Number getCreditScore() {
        return this.creditScore;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Credit score")
    public void setCreditScore(java.lang.Number creditScore) {
        this.creditScore = creditScore;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Name")
    public String getName() {
        return this.name;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Name")
    public void setName(String name) {
        this.name = name;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Prior issues")
    public List<String> getPriorIssues() {
        return this.priorIssues;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Prior issues")
    public void setPriorIssues(List<String> priorIssues) {
        this.priorIssues = priorIssues;
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
