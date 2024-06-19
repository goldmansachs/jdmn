package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tBureauData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TBureauDataImpl implements TBureauData {
        private java.lang.Number creditScore;
        private Boolean bankrupt;

    public TBureauDataImpl() {
    }

    public TBureauDataImpl(Boolean bankrupt, java.lang.Number creditScore) {
        this.setBankrupt(bankrupt);
        this.setCreditScore(creditScore);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Bankrupt")
    public Boolean getBankrupt() {
        return this.bankrupt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Bankrupt")
    public void setBankrupt(Boolean bankrupt) {
        this.bankrupt = bankrupt;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("CreditScore")
    public java.lang.Number getCreditScore() {
        return this.creditScore;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("CreditScore")
    public void setCreditScore(java.lang.Number creditScore) {
        this.creditScore = creditScore;
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
