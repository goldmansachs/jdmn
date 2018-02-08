package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tLoanProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TLoanProductImpl implements TLoanProduct {
        private String lenderName;
        private java.math.BigDecimal rate;
        private java.math.BigDecimal points;
        private java.math.BigDecimal fee;

    public TLoanProductImpl() {
    }

    public TLoanProductImpl(java.math.BigDecimal fee, String lenderName, java.math.BigDecimal points, java.math.BigDecimal rate) {
        this.setFee(fee);
        this.setLenderName(lenderName);
        this.setPoints(points);
        this.setRate(rate);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("fee")
    public java.math.BigDecimal getFee() {
        return this.fee;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("fee")
    public void setFee(java.math.BigDecimal fee) {
        this.fee = fee;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    public String getLenderName() {
        return this.lenderName;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("lenderName")
    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("points")
    public java.math.BigDecimal getPoints() {
        return this.points;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("points")
    public void setPoints(java.math.BigDecimal points) {
        this.points = points;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    public java.math.BigDecimal getRate() {
        return this.rate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rate")
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
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
