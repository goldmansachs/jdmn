package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tLoan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TLoanImpl implements TLoan {
        private java.math.BigDecimal principal;
        private java.math.BigDecimal rate;
        private java.math.BigDecimal termMonths;

    public TLoanImpl() {
    }

    public TLoanImpl(java.math.BigDecimal principal, java.math.BigDecimal rate, java.math.BigDecimal termMonths) {
        this.setPrincipal(principal);
        this.setRate(rate);
        this.setTermMonths(termMonths);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("principal")
    public java.math.BigDecimal getPrincipal() {
        return this.principal;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("principal")
    public void setPrincipal(java.math.BigDecimal principal) {
        this.principal = principal;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    public java.math.BigDecimal getRate() {
        return this.rate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rate")
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    public java.math.BigDecimal getTermMonths() {
        return this.termMonths;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("termMonths")
    public void setTermMonths(java.math.BigDecimal termMonths) {
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
