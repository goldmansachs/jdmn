package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tLoan"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TLoanImpl.class)
public interface TLoan extends com.gs.dmn.runtime.DMNType {
    static TLoan toTLoan(Object other) {
        if (other == null) {
            return null;
        } else if (TLoan.class.isAssignableFrom(other.getClass())) {
            return (TLoan)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TLoanImpl result_ = new TLoanImpl();
            Object principal = ((com.gs.dmn.runtime.Context)other).get("principal");
            result_.setPrincipal((java.lang.Number)principal);
            Object rate = ((com.gs.dmn.runtime.Context)other).get("rate");
            result_.setRate((java.lang.Number)rate);
            Object termMonths = ((com.gs.dmn.runtime.Context)other).get("termMonths");
            result_.setTermMonths((java.lang.Number)termMonths);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTLoan(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TLoan.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("principal")
    java.lang.Number getPrincipal();

    @com.fasterxml.jackson.annotation.JsonGetter("rate")
    java.lang.Number getRate();

    @com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    java.lang.Number getTermMonths();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("principal", getPrincipal());
        context.put("rate", getRate());
        context.put("termMonths", getTermMonths());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TLoan other = (TLoan) o;
        if (this.getPrincipal() != null ? !this.getPrincipal().equals(other.getPrincipal()) : other.getPrincipal() != null) return false;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;
        if (this.getTermMonths() != null ? !this.getTermMonths().equals(other.getTermMonths()) : other.getTermMonths() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getPrincipal() != null ? this.getPrincipal().hashCode() : 0);
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);
        result = 31 * result + (this.getTermMonths() != null ? this.getTermMonths().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("principal=" + getPrincipal());
        result_.append(", rate=" + getRate());
        result_.append(", termMonths=" + getTermMonths());
        result_.append("}");
        return result_.toString();
    }
}
