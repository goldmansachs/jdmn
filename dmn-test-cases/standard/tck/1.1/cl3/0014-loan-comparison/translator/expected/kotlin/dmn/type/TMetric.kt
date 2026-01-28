package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tMetric"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TMetricImpl::class)
interface TMetric : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    val lenderName: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    val rate: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("points")
    val points: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("fee")
    val fee: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    val loanAmt: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    val downPmtAmt: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    val paymentAmt: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    val equity36moPct: kotlin.Number?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.add("lenderName", this.lenderName)
        context.add("rate", this.rate)
        context.add("points", this.points)
        context.add("fee", this.fee)
        context.add("loanAmt", this.loanAmt)
        context.add("downPmtAmt", this.downPmtAmt)
        context.add("paymentAmt", this.paymentAmt)
        context.add("equity36moPct", this.equity36moPct)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TMetric
        if (if (this.downPmtAmt != null) this.downPmtAmt != other.downPmtAmt else other.downPmtAmt != null) return false
        if (if (this.equity36moPct != null) this.equity36moPct != other.equity36moPct else other.equity36moPct != null) return false
        if (if (this.fee != null) this.fee != other.fee else other.fee != null) return false
        if (if (this.lenderName != null) this.lenderName != other.lenderName else other.lenderName != null) return false
        if (if (this.loanAmt != null) this.loanAmt != other.loanAmt else other.loanAmt != null) return false
        if (if (this.paymentAmt != null) this.paymentAmt != other.paymentAmt else other.paymentAmt != null) return false
        if (if (this.points != null) this.points != other.points else other.points != null) return false
        if (if (this.rate != null) this.rate != other.rate else other.rate != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.downPmtAmt != null) this.downPmtAmt.hashCode() else 0)
        result = 31 * result + (if (this.equity36moPct != null) this.equity36moPct.hashCode() else 0)
        result = 31 * result + (if (this.fee != null) this.fee.hashCode() else 0)
        result = 31 * result + (if (this.lenderName != null) this.lenderName.hashCode() else 0)
        result = 31 * result + (if (this.loanAmt != null) this.loanAmt.hashCode() else 0)
        result = 31 * result + (if (this.paymentAmt != null) this.paymentAmt.hashCode() else 0)
        result = 31 * result + (if (this.points != null) this.points.hashCode() else 0)
        result = 31 * result + (if (this.rate != null) this.rate.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("downPmtAmt=" + downPmtAmt)
        result_.append(", equity36moPct=" + equity36moPct)
        result_.append(", fee=" + fee)
        result_.append(", lenderName=" + lenderName)
        result_.append(", loanAmt=" + loanAmt)
        result_.append(", paymentAmt=" + paymentAmt)
        result_.append(", points=" + points)
        result_.append(", rate=" + rate)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTMetric(other: Any?): TMetric? {
            if (other == null) {
                return null
            } else if (other is TMetric?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TMetricImpl()
                var lenderName = other.get("lenderName")
                result_.lenderName = lenderName as String?
                var rate = other.get("rate")
                result_.rate = rate as kotlin.Number?
                var points = other.get("points")
                result_.points = points as kotlin.Number?
                var fee = other.get("fee")
                result_.fee = fee as kotlin.Number?
                var loanAmt = other.get("loanAmt")
                result_.loanAmt = loanAmt as kotlin.Number?
                var downPmtAmt = other.get("downPmtAmt")
                result_.downPmtAmt = downPmtAmt as kotlin.Number?
                var paymentAmt = other.get("paymentAmt")
                result_.paymentAmt = paymentAmt as kotlin.Number?
                var equity36moPct = other.get("equity36moPct")
                result_.equity36moPct = equity36moPct as kotlin.Number?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTMetric(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TMetric::class.java.getSimpleName()))
            }
        }
    }
}
