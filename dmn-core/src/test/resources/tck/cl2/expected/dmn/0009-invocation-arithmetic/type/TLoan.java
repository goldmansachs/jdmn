package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tLoan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TLoan extends com.gs.dmn.runtime.DMNType {
    static TLoan toTLoan(Object other) {
        if (other == null) {
            return null;
        } else if (TLoan.class.isAssignableFrom(other.getClass())) {
            return (TLoan)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TLoanImpl result_ = new TLoanImpl();
            result_.setAmount((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("amount"));
            result_.setRate((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("rate"));
            result_.setTerm((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("term"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTLoan(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TLoan.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("amount")
    java.math.BigDecimal getAmount();

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    java.math.BigDecimal getRate();

    @com.fasterxml.jackson.annotation.JsonGetter("term")
    java.math.BigDecimal getTerm();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("amount", getAmount());
        context.put("rate", getRate());
        context.put("term", getTerm());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TLoan other = (TLoan) o;
        if (this.getAmount() != null ? !this.getAmount().equals(other.getAmount()) : other.getAmount() != null) return false;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;
        if (this.getTerm() != null ? !this.getTerm().equals(other.getTerm()) : other.getTerm() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAmount() != null ? this.getAmount().hashCode() : 0);
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);
        result = 31 * result + (this.getTerm() != null ? this.getTerm().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("amount=" + getAmount());
        result_.append(", rate=" + getRate());
        result_.append(", term=" + getTerm());
        result_.append("}");
        return result_.toString();
    }
}
