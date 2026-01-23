package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tFnInvocationResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TFnInvocationResultImpl.class)
public interface TFnInvocationResult extends com.gs.dmn.runtime.DMNType {
    static TFnInvocationResult toTFnInvocationResult(Object other) {
        if (other == null) {
            return null;
        } else if (TFnInvocationResult.class.isAssignableFrom(other.getClass())) {
            return (TFnInvocationResult)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TFnInvocationResultImpl result_ = new TFnInvocationResultImpl();
            Object sumResult = ((com.gs.dmn.runtime.Context)other).get("sumResult");
            result_.setSumResult((java.lang.Number)sumResult);
            Object multiplicationResultPositional = ((com.gs.dmn.runtime.Context)other).get("multiplicationResultPositional");
            result_.setMultiplicationResultPositional((java.lang.Number)multiplicationResultPositional);
            Object divisionResultPositional = ((com.gs.dmn.runtime.Context)other).get("divisionResultPositional");
            result_.setDivisionResultPositional((java.lang.Number)divisionResultPositional);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTFnInvocationResult(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TFnInvocationResult.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    java.lang.Number getSumResult();

    @com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    java.lang.Number getMultiplicationResultPositional();

    @com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    java.lang.Number getDivisionResultPositional();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("sumResult", getSumResult());
        context.put("multiplicationResultPositional", getMultiplicationResultPositional());
        context.put("divisionResultPositional", getDivisionResultPositional());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFnInvocationResult other = (TFnInvocationResult) o;
        if (this.getDivisionResultPositional() != null ? !this.getDivisionResultPositional().equals(other.getDivisionResultPositional()) : other.getDivisionResultPositional() != null) return false;
        if (this.getMultiplicationResultPositional() != null ? !this.getMultiplicationResultPositional().equals(other.getMultiplicationResultPositional()) : other.getMultiplicationResultPositional() != null) return false;
        if (this.getSumResult() != null ? !this.getSumResult().equals(other.getSumResult()) : other.getSumResult() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDivisionResultPositional() != null ? this.getDivisionResultPositional().hashCode() : 0);
        result = 31 * result + (this.getMultiplicationResultPositional() != null ? this.getMultiplicationResultPositional().hashCode() : 0);
        result = 31 * result + (this.getSumResult() != null ? this.getSumResult().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("divisionResultPositional=" + getDivisionResultPositional());
        result_.append(", multiplicationResultPositional=" + getMultiplicationResultPositional());
        result_.append(", sumResult=" + getSumResult());
        result_.append("}");
        return result_.toString();
    }
}
