package com.gs.dmn.jmh;

import java.util.List;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "applicant"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = ApplicantImpl.class)
public interface Applicant extends com.gs.dmn.runtime.DMNType {
    static Applicant toApplicant(Object other) {
        if (other == null) {
            return null;
        } else if (Applicant.class.isAssignableFrom(other.getClass())) {
            return (Applicant)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ApplicantImpl result_ = new ApplicantImpl();
            result_.setName((String)((com.gs.dmn.runtime.Context)other).get("name", "Name"));
            result_.setAge((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("age", "Age"));
            result_.setCreditScore((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("creditScore", "Credit score"));
            result_.setPriorIssues((List<String>)((com.gs.dmn.runtime.Context)other).get("priorIssues", "Prior issues"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toApplicant(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Applicant.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("Age")
    java.math.BigDecimal getAge();

    @com.fasterxml.jackson.annotation.JsonGetter("Credit score")
    java.math.BigDecimal getCreditScore();

    @com.fasterxml.jackson.annotation.JsonGetter("Prior issues")
    List<String> getPriorIssues();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("name", getName());
        context.put("age", getAge());
        context.put("creditScore", getCreditScore());
        context.put("priorIssues", getPriorIssues());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Applicant other = (Applicant) o;
        if (this.getAge() != null ? !this.getAge().equals(other.getAge()) : other.getAge() != null) return false;
        if (this.getCreditScore() != null ? !this.getCreditScore().equals(other.getCreditScore()) : other.getCreditScore() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;
        if (this.getPriorIssues() != null ? !this.getPriorIssues().equals(other.getPriorIssues()) : other.getPriorIssues() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAge() != null ? this.getAge().hashCode() : 0);
        result = 31 * result + (this.getCreditScore() != null ? this.getCreditScore().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = 31 * result + (this.getPriorIssues() != null ? this.getPriorIssues().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Age=" + getAge());
        result_.append(", Credit score=" + getCreditScore());
        result_.append(", Name=" + getName());
        result_.append(", Prior issues=" + getPriorIssues());
        result_.append("}");
        return result_.toString();
    }
}
