package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tLoan"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TLoanImpl : TLoan {
    @get:com.fasterxml.jackson.annotation.JsonGetter("principal")
    @set:com.fasterxml.jackson.annotation.JsonGetter("principal")
    override var principal: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rate")
    override var rate: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    @set:com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    override var termMonths: kotlin.Number? = null

    constructor() {
    }

    constructor (principal: kotlin.Number?, rate: kotlin.Number?, termMonths: kotlin.Number?) {
        this.principal = principal
        this.rate = rate
        this.termMonths = termMonths
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
