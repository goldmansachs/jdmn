package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tLoan"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TLoanImpl : TLoan {
    @get:com.fasterxml.jackson.annotation.JsonGetter("principal")
    @set:com.fasterxml.jackson.annotation.JsonGetter("principal")
    override var principal: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rate")
    override var rate: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    @set:com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    override var termMonths: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (principal: java.math.BigDecimal?, rate: java.math.BigDecimal?, termMonths: java.math.BigDecimal?) {
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
