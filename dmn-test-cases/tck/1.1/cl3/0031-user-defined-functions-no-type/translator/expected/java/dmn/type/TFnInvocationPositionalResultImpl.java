package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tFnInvocationPositionalResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TFnInvocationPositionalResultImpl implements TFnInvocationPositionalResult {
        private java.math.BigDecimal sumResult;
        private java.math.BigDecimal divisionResultPositional;
        private java.math.BigDecimal multiplicationResultPositional;

    public TFnInvocationPositionalResultImpl() {
    }

    public TFnInvocationPositionalResultImpl(java.math.BigDecimal divisionResultPositional, java.math.BigDecimal multiplicationResultPositional, java.math.BigDecimal sumResult) {
        this.setDivisionResultPositional(divisionResultPositional);
        this.setMultiplicationResultPositional(multiplicationResultPositional);
        this.setSumResult(sumResult);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    public java.math.BigDecimal getDivisionResultPositional() {
        return this.divisionResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("divisionResultPositional")
    public void setDivisionResultPositional(java.math.BigDecimal divisionResultPositional) {
        this.divisionResultPositional = divisionResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    public java.math.BigDecimal getMultiplicationResultPositional() {
        return this.multiplicationResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("multiplicationResultPositional")
    public void setMultiplicationResultPositional(java.math.BigDecimal multiplicationResultPositional) {
        this.multiplicationResultPositional = multiplicationResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    public java.math.BigDecimal getSumResult() {
        return this.sumResult;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("sumResult")
    public void setSumResult(java.math.BigDecimal sumResult) {
        this.sumResult = sumResult;
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
