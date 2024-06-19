package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tFnInvocationNamedResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TFnInvocationNamedResultImpl implements TFnInvocationNamedResult {
        private java.lang.Number subResult;
        private java.lang.Number subResultMixed;
        private java.lang.Number divisionResultNamed;
        private java.lang.Number multiplicationResultNamed;

    public TFnInvocationNamedResultImpl() {
    }

    public TFnInvocationNamedResultImpl(java.lang.Number divisionResultNamed, java.lang.Number multiplicationResultNamed, java.lang.Number subResult, java.lang.Number subResultMixed) {
        this.setDivisionResultNamed(divisionResultNamed);
        this.setMultiplicationResultNamed(multiplicationResultNamed);
        this.setSubResult(subResult);
        this.setSubResultMixed(subResultMixed);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("divisionResultNamed")
    public java.lang.Number getDivisionResultNamed() {
        return this.divisionResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("divisionResultNamed")
    public void setDivisionResultNamed(java.lang.Number divisionResultNamed) {
        this.divisionResultNamed = divisionResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultNamed")
    public java.lang.Number getMultiplicationResultNamed() {
        return this.multiplicationResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("multiplicationResultNamed")
    public void setMultiplicationResultNamed(java.lang.Number multiplicationResultNamed) {
        this.multiplicationResultNamed = multiplicationResultNamed;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("subResult")
    public java.lang.Number getSubResult() {
        return this.subResult;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("subResult")
    public void setSubResult(java.lang.Number subResult) {
        this.subResult = subResult;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("subResultMixed")
    public java.lang.Number getSubResultMixed() {
        return this.subResultMixed;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("subResultMixed")
    public void setSubResultMixed(java.lang.Number subResultMixed) {
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
