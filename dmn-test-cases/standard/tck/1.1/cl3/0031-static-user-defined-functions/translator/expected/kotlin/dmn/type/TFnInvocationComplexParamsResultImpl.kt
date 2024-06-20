package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tFnInvocationComplexParamsResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TFnInvocationComplexParamsResultImpl : TFnInvocationComplexParamsResult {
    @get:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    @set:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationLiteralExpressionInParameter")
    override var functionInvocationLiteralExpressionInParameter: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    @set:com.fasterxml.jackson.annotation.JsonGetter("functionInvocationInParameter")
    override var functionInvocationInParameter: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("circumference")
    @set:com.fasterxml.jackson.annotation.JsonGetter("circumference")
    override var circumference: java.lang.Number? = null

    constructor() {
    }

    constructor (circumference: java.lang.Number?, functionInvocationInParameter: java.lang.Number?, functionInvocationLiteralExpressionInParameter: java.lang.Number?) {
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
