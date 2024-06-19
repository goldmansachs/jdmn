package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tMetric"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TMetricImpl implements TMetric {
        private String lenderName;
        private java.lang.Number rate;
        private java.lang.Number points;
        private java.lang.Number fee;
        private java.lang.Number loanAmt;
        private java.lang.Number downPmtAmt;
        private java.lang.Number paymentAmt;
        private java.lang.Number equity36moPct;

    public TMetricImpl() {
    }

    public TMetricImpl(java.lang.Number downPmtAmt, java.lang.Number equity36moPct, java.lang.Number fee, String lenderName, java.lang.Number loanAmt, java.lang.Number paymentAmt, java.lang.Number points, java.lang.Number rate) {
        this.setDownPmtAmt(downPmtAmt);
        this.setEquity36moPct(equity36moPct);
        this.setFee(fee);
        this.setLenderName(lenderName);
        this.setLoanAmt(loanAmt);
        this.setPaymentAmt(paymentAmt);
        this.setPoints(points);
        this.setRate(rate);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    public java.lang.Number getDownPmtAmt() {
        return this.downPmtAmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("downPmtAmt")
    public void setDownPmtAmt(java.lang.Number downPmtAmt) {
        this.downPmtAmt = downPmtAmt;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    public java.lang.Number getEquity36moPct() {
        return this.equity36moPct;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("equity36moPct")
    public void setEquity36moPct(java.lang.Number equity36moPct) {
        this.equity36moPct = equity36moPct;
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

    @com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    public java.lang.Number getLoanAmt() {
        return this.loanAmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("loanAmt")
    public void setLoanAmt(java.lang.Number loanAmt) {
        this.loanAmt = loanAmt;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    public java.lang.Number getPaymentAmt() {
        return this.paymentAmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("paymentAmt")
    public void setPaymentAmt(java.lang.Number paymentAmt) {
        this.paymentAmt = paymentAmt;
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
