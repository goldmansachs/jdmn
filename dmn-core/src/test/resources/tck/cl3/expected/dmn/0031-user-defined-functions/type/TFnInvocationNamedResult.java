package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tFnInvocationNamedResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TFnInvocationNamedResult extends com.gs.dmn.runtime.DMNType {
    static TFnInvocationNamedResult toTFnInvocationNamedResult(Object other) {
        if (other == null) {
            return null;
        } else if (TFnInvocationNamedResult.class.isAssignableFrom(other.getClass())) {
            return (TFnInvocationNamedResult)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TFnInvocationNamedResultImpl result_ = new TFnInvocationNamedResultImpl();
            result_.setSubResult((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("subResult"));
            result_.setSubResultMixed((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("subResultMixed"));
            result_.setDivisionResultNamed((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("divisionResultNamed"));
            result_.setMultiplicationResultNamed((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("multiplicationResultNamed"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTFnInvocationNamedResult(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TFnInvocationNamedResult.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("subResult")
    java.math.BigDecimal getSubResult();

    @com.fasterxml.jackson.annotation.JsonGetter("subResultMixed")
    java.math.BigDecimal getSubResultMixed();

    @com.fasterxml.jackson.annotation.JsonGetter("divisionResultNamed")
    java.math.BigDecimal getDivisionResultNamed();

    @com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultNamed")
    java.math.BigDecimal getMultiplicationResultNamed();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("subResult", getSubResult());
        context.put("subResultMixed", getSubResultMixed());
        context.put("divisionResultNamed", getDivisionResultNamed());
        context.put("multiplicationResultNamed", getMultiplicationResultNamed());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFnInvocationNamedResult other = (TFnInvocationNamedResult) o;
        if (this.getDivisionResultNamed() != null ? !this.getDivisionResultNamed().equals(other.getDivisionResultNamed()) : other.getDivisionResultNamed() != null) return false;
        if (this.getMultiplicationResultNamed() != null ? !this.getMultiplicationResultNamed().equals(other.getMultiplicationResultNamed()) : other.getMultiplicationResultNamed() != null) return false;
        if (this.getSubResult() != null ? !this.getSubResult().equals(other.getSubResult()) : other.getSubResult() != null) return false;
        if (this.getSubResultMixed() != null ? !this.getSubResultMixed().equals(other.getSubResultMixed()) : other.getSubResultMixed() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDivisionResultNamed() != null ? this.getDivisionResultNamed().hashCode() : 0);
        result = 31 * result + (this.getMultiplicationResultNamed() != null ? this.getMultiplicationResultNamed().hashCode() : 0);
        result = 31 * result + (this.getSubResult() != null ? this.getSubResult().hashCode() : 0);
        result = 31 * result + (this.getSubResultMixed() != null ? this.getSubResultMixed().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("divisionResultNamed=" + getDivisionResultNamed());
        result_.append(", multiplicationResultNamed=" + getMultiplicationResultNamed());
        result_.append(", subResult=" + getSubResult());
        result_.append(", subResultMixed=" + getSubResultMixed());
        result_.append("}");
        return result_.toString();
    }
}
