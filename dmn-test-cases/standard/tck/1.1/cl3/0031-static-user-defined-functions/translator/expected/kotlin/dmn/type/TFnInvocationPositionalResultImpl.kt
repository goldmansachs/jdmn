package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tFnInvocationPositionalResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TFnInvocationPositionalResultImpl : TFnInvocationPositionalResult {
    @get:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    @set:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    override var sumResult: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    @set:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    override var divisionResultPositional: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    @set:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    override var multiplicationResultPositional: java.lang.Number? = null

    constructor() {
    }

    constructor (divisionResultPositional: java.lang.Number?, multiplicationResultPositional: java.lang.Number?, sumResult: java.lang.Number?) {
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
