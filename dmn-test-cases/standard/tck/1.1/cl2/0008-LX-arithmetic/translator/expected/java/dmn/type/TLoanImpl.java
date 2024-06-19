package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tLoan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TLoanImpl implements TLoan {
        private java.lang.Number principal;
        private java.lang.Number rate;
        private java.lang.Number termMonths;

    public TLoanImpl() {
    }

    public TLoanImpl(java.lang.Number principal, java.lang.Number rate, java.lang.Number termMonths) {
        this.setPrincipal(principal);
        this.setRate(rate);
        this.setTermMonths(termMonths);
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

    @com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    public java.lang.Number getTermMonths() {
        return this.termMonths;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("termMonths")
    public void setTermMonths(java.lang.Number termMonths) {
        this.termMonths = termMonths;
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
