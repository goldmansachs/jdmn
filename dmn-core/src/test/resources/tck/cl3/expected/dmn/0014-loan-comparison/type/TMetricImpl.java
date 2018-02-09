package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tMetric"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TMetricImpl implements TMetric {
        private String lenderName;
        private java.math.BigDecimal rate;
        private java.math.BigDecimal points;
        private java.math.BigDecimal fee;
        private java.math.BigDecimal loanAmt;
        private java.math.BigDecimal downPmtAmt;
        private java.math.BigDecimal paymentAmt;
        private java.math.BigDecimal equity36moPct;

    public TMetricImpl() {
    }

    public TMetricImpl(java.math.BigDecimal downPmtAmt, java.math.BigDecimal equity36moPct, java.math.BigDecimal fee, String lenderName, java.math.BigDecimal loanAmt, java.math.BigDecimal paymentAmt, java.math.BigDecimal points, java.math.BigDecimal rate) {
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
    public java.math.BigDecimal getDownPmtAmt() {
        return this.downPmtAmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("downPmtAmt")
    public void setDownPmtAmt(java.math.BigDecimal downPmtAmt) {
        this.downPmtAmt = downPmtAmt;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    public java.math.BigDecimal getEquity36moPct() {
        return this.equity36moPct;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("equity36moPct")
    public void setEquity36moPct(java.math.BigDecimal equity36moPct) {
        this.equity36moPct = equity36moPct;
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

    @com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    public java.math.BigDecimal getLoanAmt() {
        return this.loanAmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("loanAmt")
    public void setLoanAmt(java.math.BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    public java.math.BigDecimal getPaymentAmt() {
        return this.paymentAmt;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("paymentAmt")
    public void setPaymentAmt(java.math.BigDecimal paymentAmt) {
        this.paymentAmt = paymentAmt;
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
