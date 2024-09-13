package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tMetric"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TMetricImpl : TMetric {
    @get:com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    @set:com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    override var lenderName: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rate")
    override var rate: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("points")
    @set:com.fasterxml.jackson.annotation.JsonGetter("points")
    override var points: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("fee")
    @set:com.fasterxml.jackson.annotation.JsonGetter("fee")
    override var fee: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    override var loanAmt: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    override var downPmtAmt: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    override var paymentAmt: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    @set:com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    override var equity36moPct: kotlin.Number? = null

    constructor() {
    }

    constructor (downPmtAmt: kotlin.Number?, equity36moPct: kotlin.Number?, fee: kotlin.Number?, lenderName: String?, loanAmt: kotlin.Number?, paymentAmt: kotlin.Number?, points: kotlin.Number?, rate: kotlin.Number?) {
        this.downPmtAmt = downPmtAmt
        this.equity36moPct = equity36moPct
        this.fee = fee
        this.lenderName = lenderName
        this.loanAmt = loanAmt
        this.paymentAmt = paymentAmt
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
