package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tFnInvocationNamedResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TFnInvocationNamedResultImpl implements TFnInvocationNamedResult {
        private java.math.BigDecimal subResult;
        private java.math.BigDecimal subResultMixed;
        private java.math.BigDecimal divisionResultNamed;
        private java.math.BigDecimal multiplicationResultNamed;

    public TFnInvocationNamedResultImpl() {
    }

    public TFnInvocationNamedResultImpl(java.math.BigDecimal divisionResultNamed, java.math.BigDecimal multiplicationResultNamed, java.math.BigDecimal subResult, java.math.BigDecimal subResultMixed) {
        this.setDivisionResultNamed(divisionResultNamed);
        this.setMultiplicationResultNamed(multiplicationResultNamed);
        this.setSubResult(subResult);
        this.setSubResultMixed(subResultMixed);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("divisionResultNamed")
    public java.math.BigDecimal getDivisionResultNamed() {
        return this.divisionResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("divisionResultNamed")
    public void setDivisionResultNamed(java.math.BigDecimal divisionResultNamed) {
        this.divisionResultNamed = divisionResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultNamed")
    public java.math.BigDecimal getMultiplicationResultNamed() {
        return this.multiplicationResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("multiplicationResultNamed")
    public void setMultiplicationResultNamed(java.math.BigDecimal multiplicationResultNamed) {
        this.multiplicationResultNamed = multiplicationResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("subResult")
    public java.math.BigDecimal getSubResult() {
        return this.subResult;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("subResult")
    public void setSubResult(java.math.BigDecimal subResult) {
        this.subResult = subResult;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("subResultMixed")
    public java.math.BigDecimal getSubResultMixed() {
        return this.subResultMixed;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("subResultMixed")
    public void setSubResultMixed(java.math.BigDecimal subResultMixed) {
        this.subResultMixed = subResultMixed;
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
