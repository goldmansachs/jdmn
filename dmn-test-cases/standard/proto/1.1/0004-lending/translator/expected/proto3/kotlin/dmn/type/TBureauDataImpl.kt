package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tBureauData"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TBureauDataImpl : TBureauData {
    @get:com.fasterxml.jackson.annotation.JsonGetter("CreditScore")
    @set:com.fasterxml.jackson.annotation.JsonGetter("CreditScore")
    override var creditScore: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Bankrupt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Bankrupt")
    override var bankrupt: Boolean? = null

    constructor() {
    }

    constructor (bankrupt: Boolean?, creditScore: kotlin.Number?) {
        this.bankrupt = bankrupt
        this.creditScore = creditScore
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
