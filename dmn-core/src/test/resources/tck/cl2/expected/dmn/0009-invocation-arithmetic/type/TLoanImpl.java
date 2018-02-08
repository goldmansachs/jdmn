package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tLoan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TLoanImpl implements TLoan {
        private java.math.BigDecimal amount;
        private java.math.BigDecimal rate;
        private java.math.BigDecimal term;

    public TLoanImpl() {
    }

    public TLoanImpl(java.math.BigDecimal amount, java.math.BigDecimal rate, java.math.BigDecimal term) {
        this.setAmount(amount);
        this.setRate(rate);
        this.setTerm(term);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("amount")
    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("amount")
    public void setAmount(java.math.BigDecimal amount) {
        this.amount = amount;
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
