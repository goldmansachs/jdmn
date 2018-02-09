package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tFnInvocationComplexParamsResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TFnInvocationComplexParamsResultImpl implements TFnInvocationComplexParamsResult {
        private java.math.BigDecimal functionInvocationLiteralExpressionInParameter;
        private java.math.BigDecimal functionInvocationInParameter;
        private java.math.BigDecimal circumference;

    public TFnInvocationComplexParamsResultImpl() {
    }

    public TFnInvocationComplexParamsResultImpl(java.math.BigDecimal circumference, java.math.BigDecimal functionInvocationInParameter, java.math.BigDecimal functionInvocationLiteralExpressionInParameter) {
        this.setCircumference(circumference);
        this.setFunctionInvocationInParameter(functionInvocationInParameter);
        this.setFunctionInvocationLiteralExpressionInParameter(functionInvocationLiteralExpressionInParameter);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("circumference")
    public java.math.BigDecimal getCircumference() {
        return this.circumference;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("circumference")
    public void setCircumference(java.math.BigDecimal circumference) {
        this.circumference = circumference;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    public java.math.BigDecimal getFunctionInvocationInParameter() {
        return this.functionInvocationInParameter;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("functionInvocationInParameter")
    public void setFunctionInvocationInParameter(java.math.BigDecimal functionInvocationInParameter) {
        this.functionInvocationInParameter = functionInvocationInParameter;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    public java.math.BigDecimal getFunctionInvocationLiteralExpressionInParameter() {
        return this.functionInvocationLiteralExpressionInParameter;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("functionInvocationLiteralExpressionInParameter")
    public void setFunctionInvocationLiteralExpressionInParameter(java.math.BigDecimal functionInvocationLiteralExpressionInParameter) {
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
