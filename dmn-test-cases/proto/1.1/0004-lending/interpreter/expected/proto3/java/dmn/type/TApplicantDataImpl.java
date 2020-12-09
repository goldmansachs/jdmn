package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tApplicantData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TApplicantDataImpl implements TApplicantData {
        private type.Monthly monthly;
        private java.math.BigDecimal age;
        private Boolean existingCustomer;
        private String maritalStatus;
        private String employmentStatus;

    public TApplicantDataImpl() {
    }

    public TApplicantDataImpl(java.math.BigDecimal age, String employmentStatus, Boolean existingCustomer, String maritalStatus, type.Monthly monthly) {
        this.setAge(age);
        this.setEmploymentStatus(employmentStatus);
        this.setExistingCustomer(existingCustomer);
        this.setMaritalStatus(maritalStatus);
        this.setMonthly(monthly);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Age")
    public java.math.BigDecimal getAge() {
        return this.age;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Age")
    public void setAge(java.math.BigDecimal age) {
        this.age = age;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("EmploymentStatus")
    public String getEmploymentStatus() {
        return this.employmentStatus;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("EmploymentStatus")
    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("ExistingCustomer")
    public Boolean getExistingCustomer() {
        return this.existingCustomer;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("ExistingCustomer")
    public void setExistingCustomer(Boolean existingCustomer) {
        this.existingCustomer = existingCustomer;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("MaritalStatus")
    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("MaritalStatus")
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Monthly")
    public type.Monthly getMonthly() {
        return this.monthly;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Monthly")
    public void setMonthly(type.Monthly monthly) {
        this.monthly = monthly;
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
