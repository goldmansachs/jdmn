package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tLoan"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TLoanImpl : TLoan {
    @get:com.fasterxml.jackson.annotation.JsonGetter("amount")
    @set:com.fasterxml.jackson.annotation.JsonGetter("amount")
    override var amount: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rate")
    override var rate: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("term")
    @set:com.fasterxml.jackson.annotation.JsonGetter("term")
    override var term: java.lang.Number? = null

    constructor() {
    }

    constructor (amount: java.lang.Number?, rate: java.lang.Number?, term: java.lang.Number?) {
        this.amount = amount
        this.rate = rate
        this.term = term
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
