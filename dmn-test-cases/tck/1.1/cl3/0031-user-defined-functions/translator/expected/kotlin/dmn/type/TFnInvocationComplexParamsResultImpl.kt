package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tFnInvocationComplexParamsResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TFnInvocationComplexParamsResultImpl : TFnInvocationComplexParamsResult {
    @get:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    @set:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    override var functionInvocationLiteralExpressionInParameter: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    @set:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    override var functionInvocationInParameter: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("circumference")
    @set:com.fasterxml.jackson.annotation.JsonGetter("circumference")
    override var circumference: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (circumference: java.math.BigDecimal?, functionInvocationInParameter: java.math.BigDecimal?, functionInvocationLiteralExpressionInParameter: java.math.BigDecimal?) {
        this.circumference = circumference
        this.functionInvocationInParameter = functionInvocationInParameter
        this.functionInvocationLiteralExpressionInParameter = functionInvocationLiteralExpressionInParameter
    }

    override fun equals(other: Any?): Boolean {
        return equalTo(other)
    }

    override fun hashCode(): Int {
        return hash()
    }

    @Override
    override fun toString(): String {
        return asString()
    }
}
