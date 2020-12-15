package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tLoan"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TLoanImpl : TLoan {
    @get:com.fasterxml.jackson.annotation.JsonGetter("amount")
    @set:com.fasterxml.jackson.annotation.JsonGetter("amount")
    override var amount: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rate")
    override var rate: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("term")
    @set:com.fasterxml.jackson.annotation.JsonGetter("term")
    override var term: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (amount: java.math.BigDecimal?, rate: java.math.BigDecimal?, term: java.math.BigDecimal?) {
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
