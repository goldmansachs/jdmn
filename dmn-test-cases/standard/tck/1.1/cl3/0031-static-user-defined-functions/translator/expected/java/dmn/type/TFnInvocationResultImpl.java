package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tFnInvocationResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TFnInvocationResultImpl implements TFnInvocationResult {
        private java.lang.Number sumResult;
        private java.lang.Number multiplicationResultPositional;
        private java.lang.Number divisionResultPositional;

    public TFnInvocationResultImpl() {
    }

    public TFnInvocationResultImpl(java.lang.Number divisionResultPositional, java.lang.Number multiplicationResultPositional, java.lang.Number sumResult) {
        this.setDivisionResultPositional(divisionResultPositional);
        this.setMultiplicationResultPositional(multiplicationResultPositional);
        this.setSumResult(sumResult);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    public java.lang.Number getDivisionResultPositional() {
        return this.divisionResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("divisionResultPositional")
    public void setDivisionResultPositional(java.lang.Number divisionResultPositional) {
        this.divisionResultPositional = divisionResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    public java.lang.Number getMultiplicationResultPositional() {
        return this.multiplicationResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("multiplicationResultPositional")
    public void setMultiplicationResultPositional(java.lang.Number multiplicationResultPositional) {
        this.multiplicationResultPositional = multiplicationResultPositional;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    public java.lang.Number getSumResult() {
        return this.sumResult;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("sumResult")
    public void setSumResult(java.lang.Number sumResult) {
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
