package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tFnInvocationComplexParamsResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TFnInvocationComplexParamsResultImpl::class)
interface TFnInvocationComplexParamsResult : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    val functionInvocationLiteralExpressionInParameter: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    val functionInvocationInParameter: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("circumference")
    val circumference: kotlin.Number?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.add("functionInvocationLiteralExpressionInParameter", this.functionInvocationLiteralExpressionInParameter)
        context.add("functionInvocationInParameter", this.functionInvocationInParameter)
        context.add("circumference", this.circumference)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TFnInvocationComplexParamsResult
        if (if (this.circumference != null) this.circumference != other.circumference else other.circumference != null) return false
        if (if (this.functionInvocationInParameter != null) this.functionInvocationInParameter != other.functionInvocationInParameter else other.functionInvocationInParameter != null) return false
        if (if (this.functionInvocationLiteralExpressionInParameter != null) this.functionInvocationLiteralExpressionInParameter != other.functionInvocationLiteralExpressionInParameter else other.functionInvocationLiteralExpressionInParameter != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.circumference != null) this.circumference.hashCode() else 0)
        result = 31 * result + (if (this.functionInvocationInParameter != null) this.functionInvocationInParameter.hashCode() else 0)
        result = 31 * result + (if (this.functionInvocationLiteralExpressionInParameter != null) this.functionInvocationLiteralExpressionInParameter.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("circumference=" + circumference)
        result_.append(", functionInvocationInParameter=" + functionInvocationInParameter)
        result_.append(", functionInvocationLiteralExpressionInParameter=" + functionInvocationLiteralExpressionInParameter)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTFnInvocationComplexParamsResult(other: Any?): TFnInvocationComplexParamsResult? {
            if (other == null) {
                return null
            } else if (other is TFnInvocationComplexParamsResult?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TFnInvocationComplexParamsResultImpl()
                var functionInvocationLiteralExpressionInParameter = other.get("functionInvocationLiteralExpressionInParameter")
                result_.functionInvocationLiteralExpressionInParameter = functionInvocationLiteralExpressionInParameter as kotlin.Number?
                var functionInvocationInParameter = other.get("functionInvocationInParameter")
                result_.functionInvocationInParameter = functionInvocationInParameter as kotlin.Number?
                var circumference = other.get("circumference")
                result_.circumference = circumference as kotlin.Number?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTFnInvocationComplexParamsResult(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TFnInvocationComplexParamsResult::class.java.getSimpleName()))
            }
        }
    }
}
