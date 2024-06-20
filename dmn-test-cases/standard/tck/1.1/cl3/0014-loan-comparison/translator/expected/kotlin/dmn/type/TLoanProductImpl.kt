package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tLoanProduct"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TLoanProductImpl : TLoanProduct {
    @get:com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    @set:com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    override var lenderName: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rate")
    override var rate: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("points")
    @set:com.fasterxml.jackson.annotation.JsonGetter("points")
    override var points: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("fee")
    @set:com.fasterxml.jackson.annotation.JsonGetter("fee")
    override var fee: java.lang.Number? = null

    constructor() {
    }

    constructor (fee: java.lang.Number?, lenderName: String?, points: java.lang.Number?, rate: java.lang.Number?) {
        this.fee = fee
        this.lenderName = lenderName
        this.points = points
        this.rate = rate
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
