package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tBureauData"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TBureauDataImpl implements TBureauData {
        private java.math.BigDecimal creditScore;
        private Boolean bankrupt;

    public TBureauDataImpl() {
    }

    public TBureauDataImpl(Boolean bankrupt, java.math.BigDecimal creditScore) {
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
    public java.math.BigDecimal getCreditScore() {
        return this.creditScore;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("CreditScore")
    public void setCreditScore(java.math.BigDecimal creditScore) {
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
