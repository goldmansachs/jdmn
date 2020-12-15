package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "applicant"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ApplicantImpl implements Applicant {
        private String name;
        private java.math.BigDecimal age;
        private java.math.BigDecimal creditScore;
        private List<String> priorIssues;

    public ApplicantImpl() {
    }

    public ApplicantImpl(java.math.BigDecimal age, java.math.BigDecimal creditScore, String name, List<String> priorIssues) {
        this.setAge(age);
        this.setCreditScore(creditScore);
        this.setName(name);
        this.setPriorIssues(priorIssues);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Age")
    public java.math.BigDecimal getAge() {
        return this.age;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Age")
    public void setAge(java.math.BigDecimal age) {
        this.age = age;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Credit score")
    public java.math.BigDecimal getCreditScore() {
        return this.creditScore;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Credit score")
    public void setCreditScore(java.math.BigDecimal creditScore) {
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
