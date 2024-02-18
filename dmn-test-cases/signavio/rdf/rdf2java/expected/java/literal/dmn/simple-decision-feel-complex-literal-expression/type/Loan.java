package type;

import java.util.*;

@jakarta.annotation.Generated(value = {"itemDefinitionInterface.ftl", "loan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.LoanImpl.class)
public interface Loan extends com.gs.dmn.runtime.DMNType {
    static Loan toLoan(Object other) {
        if (other == null) {
            return null;
        } else if (Loan.class.isAssignableFrom(other.getClass())) {
            return (Loan)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            LoanImpl result_ = new LoanImpl();
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("principal") || ((com.gs.dmn.runtime.Context)other).keySet().contains("principal")) {
                result_.setPrincipal((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("principal", "principal"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("rate") || ((com.gs.dmn.runtime.Context)other).keySet().contains("rate")) {
                result_.setRate((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("rate", "rate"));
            } else {
                return  null;
            }
            if (((com.gs.dmn.runtime.Context)other).keySet().contains("term") || ((com.gs.dmn.runtime.Context)other).keySet().contains("term")) {
                result_.setTerm((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("term", "term"));
            } else {
                return  null;
            }
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toLoan(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Loan.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("principal")
    java.math.BigDecimal getPrincipal();

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    java.math.BigDecimal getRate();

    @com.fasterxml.jackson.annotation.JsonGetter("term")
    java.math.BigDecimal getTerm();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("principal", getPrincipal());
        context.put("rate", getRate());
        context.put("term", getTerm());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loan other = (Loan) o;
        if (this.getPrincipal() != null ? !this.getPrincipal().equals(other.getPrincipal()) : other.getPrincipal() != null) return false;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;
        if (this.getTerm() != null ? !this.getTerm().equals(other.getTerm()) : other.getTerm() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getPrincipal() != null ? this.getPrincipal().hashCode() : 0);
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);
        result = 31 * result + (this.getTerm() != null ? this.getTerm().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("principal=" + getPrincipal());
        result_.append(", rate=" + getRate());
        result_.append(", term=" + getTerm());
        result_.append("}");
        return result_.toString();
    }
}
