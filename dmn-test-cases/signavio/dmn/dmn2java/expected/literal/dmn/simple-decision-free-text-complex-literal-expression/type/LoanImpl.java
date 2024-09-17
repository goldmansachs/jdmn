package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "loan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class LoanImpl implements Loan {
        private java.lang.Number principal;
        private java.lang.Number rate;
        private java.lang.Number term;

    public LoanImpl() {
    }

    public LoanImpl(java.lang.Number principal, java.lang.Number rate, java.lang.Number term) {
        this.setPrincipal(principal);
        this.setRate(rate);
        this.setTerm(term);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("principal")
    public java.lang.Number getPrincipal() {
        return this.principal;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("principal")
    public void setPrincipal(java.lang.Number principal) {
        this.principal = principal;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    public java.lang.Number getRate() {
        return this.rate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rate")
    public void setRate(java.lang.Number rate) {
        this.rate = rate;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("term")
    public java.lang.Number getTerm() {
        return this.term;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("term")
    public void setTerm(java.lang.Number term) {
        this.term = term;
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
