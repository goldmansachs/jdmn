package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tFnInvocationPositionalResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TFnInvocationPositionalResultImpl : TFnInvocationPositionalResult {
    @get:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    @set:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    override var sumResult: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    @set:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    override var divisionResultPositional: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    @set:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    override var multiplicationResultPositional: kotlin.Number? = null

    constructor() {
    }

    constructor (divisionResultPositional: kotlin.Number?, multiplicationResultPositional: kotlin.Number?, sumResult: kotlin.Number?) {
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
