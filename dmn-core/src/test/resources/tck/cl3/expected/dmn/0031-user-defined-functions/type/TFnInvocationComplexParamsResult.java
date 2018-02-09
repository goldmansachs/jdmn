package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tFnInvocationComplexParamsResult"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TFnInvocationComplexParamsResult extends com.gs.dmn.runtime.DMNType {
    static TFnInvocationComplexParamsResult toTFnInvocationComplexParamsResult(Object other) {
        if (other == null) {
            return null;
        } else if (TFnInvocationComplexParamsResult.class.isAssignableFrom(other.getClass())) {
            return (TFnInvocationComplexParamsResult)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TFnInvocationComplexParamsResultImpl result_ = new TFnInvocationComplexParamsResultImpl();
            result_.setFunctionInvocationLiteralExpressionInParameter((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("functionInvocationLiteralExpressionInParameter"));
            result_.setFunctionInvocationInParameter((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("functionInvocationInParameter"));
            result_.setCircumference((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("circumference"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTFnInvocationComplexParamsResult(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TFnInvocationComplexParamsResult.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    java.math.BigDecimal getFunctionInvocationLiteralExpressionInParameter();

    @com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    java.math.BigDecimal getFunctionInvocationInParameter();

    @com.fasterxml.jackson.annotation.JsonGetter("circumference")
    java.math.BigDecimal getCircumference();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("functionInvocationLiteralExpressionInParameter", getFunctionInvocationLiteralExpressionInParameter());
        context.put("functionInvocationInParameter", getFunctionInvocationInParameter());
        context.put("circumference", getCircumference());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TFnInvocationComplexParamsResult other = (TFnInvocationComplexParamsResult) o;
        if (this.getCircumference() != null ? !this.getCircumference().equals(other.getCircumference()) : other.getCircumference() != null) return false;
        if (this.getFunctionInvocationInParameter() != null ? !this.getFunctionInvocationInParameter().equals(other.getFunctionInvocationInParameter()) : other.getFunctionInvocationInParameter() != null) return false;
        if (this.getFunctionInvocationLiteralExpressionInParameter() != null ? !this.getFunctionInvocationLiteralExpressionInParameter().equals(other.getFunctionInvocationLiteralExpressionInParameter()) : other.getFunctionInvocationLiteralExpressionInParameter() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getCircumference() != null ? this.getCircumference().hashCode() : 0);
        result = 31 * result + (this.getFunctionInvocationInParameter() != null ? this.getFunctionInvocationInParameter().hashCode() : 0);
        result = 31 * result + (this.getFunctionInvocationLiteralExpressionInParameter() != null ? this.getFunctionInvocationLiteralExpressionInParameter().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("circumference=" + getCircumference());
        result_.append(", functionInvocationInParameter=" + getFunctionInvocationInParameter());
        result_.append(", functionInvocationLiteralExpressionInParameter=" + getFunctionInvocationLiteralExpressionInParameter());
        result_.append("}");
        return result_.toString();
    }
}
