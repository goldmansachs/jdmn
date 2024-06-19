package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tFnInvocationComplexParamsResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TFnInvocationComplexParamsResultImpl implements TFnInvocationComplexParamsResult {
        private java.lang.Number functionInvocationLiteralExpressionInParameter;
        private java.lang.Number functionInvocationInParameter;
        private java.lang.Number circumference;

    public TFnInvocationComplexParamsResultImpl() {
    }

    public TFnInvocationComplexParamsResultImpl(java.lang.Number circumference, java.lang.Number functionInvocationInParameter, java.lang.Number functionInvocationLiteralExpressionInParameter) {
        this.setCircumference(circumference);
        this.setFunctionInvocationInParameter(functionInvocationInParameter);
        this.setFunctionInvocationLiteralExpressionInParameter(functionInvocationLiteralExpressionInParameter);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("circumference")
    public java.lang.Number getCircumference() {
        return this.circumference;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("circumference")
    public void setCircumference(java.lang.Number circumference) {
        this.circumference = circumference;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    public java.lang.Number getFunctionInvocationInParameter() {
        return this.functionInvocationInParameter;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("functionInvocationInParameter")
    public void setFunctionInvocationInParameter(java.lang.Number functionInvocationInParameter) {
        this.functionInvocationInParameter = functionInvocationInParameter;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    public java.lang.Number getFunctionInvocationLiteralExpressionInParameter() {
        return this.functionInvocationLiteralExpressionInParameter;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("functionInvocationLiteralExpressionInParameter")
    public void setFunctionInvocationLiteralExpressionInParameter(java.lang.Number functionInvocationLiteralExpressionInParameter) {
        this.functionInvocationLiteralExpressionInParameter = functionInvocationLiteralExpressionInParameter;
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
