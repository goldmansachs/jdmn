package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tLoan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TLoanImpl implements TLoan {
        private java.lang.Number amount;
        private java.lang.Number rate;
        private java.lang.Number term;

    public TLoanImpl() {
    }

    public TLoanImpl(java.lang.Number amount, java.lang.Number rate, java.lang.Number term) {
        this.setAmount(amount);
        this.setRate(rate);
        this.setTerm(term);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("amount")
    public java.lang.Number getAmount() {
        return this.amount;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("amount")
    public void setAmount(java.lang.Number amount) {
        this.amount = amount;
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
