package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "loan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class LoanImpl implements Loan {
        private java.math.BigDecimal principal;
        private java.math.BigDecimal rate;
        private java.math.BigDecimal term;

    public LoanImpl() {
    }

    public LoanImpl(java.math.BigDecimal principal, java.math.BigDecimal rate, java.math.BigDecimal term) {
        this.setPrincipal(principal);
        this.setRate(rate);
        this.setTerm(term);
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

    @com.fasterxml.jackson.annotation.JsonGetter("term")
    public java.math.BigDecimal getTerm() {
        return this.term;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("term")
    public void setTerm(java.math.BigDecimal term) {
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
