package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tLoanProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TLoanProductImpl implements TLoanProduct {
        private String lenderName;
        private java.lang.Number rate;
        private java.lang.Number points;
        private java.lang.Number fee;

    public TLoanProductImpl() {
    }

    public TLoanProductImpl(java.lang.Number fee, String lenderName, java.lang.Number points, java.lang.Number rate) {
        this.setFee(fee);
        this.setLenderName(lenderName);
        this.setPoints(points);
        this.setRate(rate);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("fee")
    public java.lang.Number getFee() {
        return this.fee;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("fee")
    public void setFee(java.lang.Number fee) {
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
    public java.lang.Number getPoints() {
        return this.points;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("points")
    public void setPoints(java.lang.Number points) {
        this.points = points;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    public java.lang.Number getRate() {
        return this.rate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("rate")
    public void setRate(java.lang.Number rate) {
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
