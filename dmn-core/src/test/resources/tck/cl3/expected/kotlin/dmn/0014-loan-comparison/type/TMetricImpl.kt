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
    override var rate: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("points")
    @set:com.fasterxml.jackson.annotation.JsonGetter("points")
    override var points: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("fee")
    @set:com.fasterxml.jackson.annotation.JsonGetter("fee")
    override var fee: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    override var loanAmt: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    override var downPmtAmt: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    override var paymentAmt: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    @set:com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    override var equity36moPct: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (downPmtAmt: java.math.BigDecimal?, equity36moPct: java.math.BigDecimal?, fee: java.math.BigDecimal?, lenderName: String?, loanAmt: java.math.BigDecimal?, paymentAmt: java.math.BigDecimal?, points: java.math.BigDecimal?, rate: java.math.BigDecimal?) {
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
