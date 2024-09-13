package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tFnInvocationNamedResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TFnInvocationNamedResultImpl : TFnInvocationNamedResult {
    @get:com.fasterxml.jackson.annotation.JsonGetter("subResult")
    @set:com.fasterxml.jackson.annotation.JsonGetter("subResult")
    override var subResult: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("subResultMixed")
    @set:com.fasterxml.jackson.annotation.JsonGetter("subResultMixed")
    override var subResultMixed: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("divisionResultNamed")
    @set:com.fasterxml.jackson.annotation.JsonGetter("divisionResultNamed")
    override var divisionResultNamed: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultNamed")
    @set:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultNamed")
    override var multiplicationResultNamed: kotlin.Number? = null

    constructor() {
    }

    constructor (divisionResultNamed: kotlin.Number?, multiplicationResultNamed: kotlin.Number?, subResult: kotlin.Number?, subResultMixed: kotlin.Number?) {
        this.divisionResultNamed = divisionResultNamed
        this.multiplicationResultNamed = multiplicationResultNamed
        this.subResult = subResult
        this.subResultMixed = subResultMixed
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
