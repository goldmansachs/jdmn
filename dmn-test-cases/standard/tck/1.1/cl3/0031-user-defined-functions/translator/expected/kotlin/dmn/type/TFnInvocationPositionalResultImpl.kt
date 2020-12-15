package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tFnInvocationPositionalResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TFnInvocationPositionalResultImpl : TFnInvocationPositionalResult {
    @get:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    @set:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    override var sumResult: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    @set:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    override var divisionResultPositional: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    @set:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    override var multiplicationResultPositional: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (divisionResultPositional: java.math.BigDecimal?, multiplicationResultPositional: java.math.BigDecimal?, sumResult: java.math.BigDecimal?) {
        this.divisionResultPositional = divisionResultPositional
        this.multiplicationResultPositional = multiplicationResultPositional
        this.sumResult = sumResult
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
