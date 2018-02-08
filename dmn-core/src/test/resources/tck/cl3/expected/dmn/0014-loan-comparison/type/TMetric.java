package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tMetric"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TMetric extends com.gs.dmn.runtime.DMNType {
    static TMetric toTMetric(Object other) {
        if (other == null) {
            return null;
        } else if (TMetric.class.isAssignableFrom(other.getClass())) {
            return (TMetric)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TMetricImpl result_ = new TMetricImpl();
            result_.setLenderName((String)((com.gs.dmn.runtime.Context)other).get("lenderName"));
            result_.setRate((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("rate"));
            result_.setPoints((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("points"));
            result_.setFee((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("fee"));
            result_.setLoanAmt((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("loanAmt"));
            result_.setDownPmtAmt((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("downPmtAmt"));
            result_.setPaymentAmt((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("paymentAmt"));
            result_.setEquity36moPct((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("equity36moPct"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTMetric(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TMetric.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    String getLenderName();

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    java.math.BigDecimal getRate();

    @com.fasterxml.jackson.annotation.JsonGetter("points")
    java.math.BigDecimal getPoints();

    @com.fasterxml.jackson.annotation.JsonGetter("fee")
    java.math.BigDecimal getFee();

    @com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    java.math.BigDecimal getLoanAmt();

    @com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    java.math.BigDecimal getDownPmtAmt();

    @com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    java.math.BigDecimal getPaymentAmt();

    @com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    java.math.BigDecimal getEquity36moPct();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("lenderName", getLenderName());
        context.put("rate", getRate());
        context.put("points", getPoints());
        context.put("fee", getFee());
        context.put("loanAmt", getLoanAmt());
        context.put("downPmtAmt", getDownPmtAmt());
        context.put("paymentAmt", getPaymentAmt());
        context.put("equity36moPct", getEquity36moPct());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TMetric other = (TMetric) o;
        if (this.getDownPmtAmt() != null ? !this.getDownPmtAmt().equals(other.getDownPmtAmt()) : other.getDownPmtAmt() != null) return false;
        if (this.getEquity36moPct() != null ? !this.getEquity36moPct().equals(other.getEquity36moPct()) : other.getEquity36moPct() != null) return false;
        if (this.getFee() != null ? !this.getFee().equals(other.getFee()) : other.getFee() != null) return false;
        if (this.getLenderName() != null ? !this.getLenderName().equals(other.getLenderName()) : other.getLenderName() != null) return false;
        if (this.getLoanAmt() != null ? !this.getLoanAmt().equals(other.getLoanAmt()) : other.getLoanAmt() != null) return false;
        if (this.getPaymentAmt() != null ? !this.getPaymentAmt().equals(other.getPaymentAmt()) : other.getPaymentAmt() != null) return false;
        if (this.getPoints() != null ? !this.getPoints().equals(other.getPoints()) : other.getPoints() != null) return false;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDownPmtAmt() != null ? this.getDownPmtAmt().hashCode() : 0);
        result = 31 * result + (this.getEquity36moPct() != null ? this.getEquity36moPct().hashCode() : 0);
        result = 31 * result + (this.getFee() != null ? this.getFee().hashCode() : 0);
        result = 31 * result + (this.getLenderName() != null ? this.getLenderName().hashCode() : 0);
        result = 31 * result + (this.getLoanAmt() != null ? this.getLoanAmt().hashCode() : 0);
        result = 31 * result + (this.getPaymentAmt() != null ? this.getPaymentAmt().hashCode() : 0);
        result = 31 * result + (this.getPoints() != null ? this.getPoints().hashCode() : 0);
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("downPmtAmt=" + getDownPmtAmt());
        result_.append(", equity36moPct=" + getEquity36moPct());
        result_.append(", fee=" + getFee());
        result_.append(", lenderName=" + getLenderName());
        result_.append(", loanAmt=" + getLoanAmt());
        result_.append(", paymentAmt=" + getPaymentAmt());
        result_.append(", points=" + getPoints());
        result_.append(", rate=" + getRate());
        result_.append("}");
        return result_.toString();
    }
}
