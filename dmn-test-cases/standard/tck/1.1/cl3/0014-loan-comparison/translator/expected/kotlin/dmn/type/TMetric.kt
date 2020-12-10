package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tMetric"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TMetricImpl::class)
interface TMetric : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    val lenderName: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    val rate: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("points")
    val points: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("fee")
    val fee: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("loanAmt")
    val loanAmt: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("downPmtAmt")
    val downPmtAmt: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("paymentAmt")
    val paymentAmt: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("equity36moPct")
    val equity36moPct: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("lenderName", this.lenderName)
        context.put("rate", this.rate)
        context.put("points", this.points)
        context.put("fee", this.fee)
        context.put("loanAmt", this.loanAmt)
        context.put("downPmtAmt", this.downPmtAmt)
        context.put("paymentAmt", this.paymentAmt)
        context.put("equity36moPct", this.equity36moPct)
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
                result_.lenderName = other.get("lenderName") as String?
                result_.rate = other.get("rate") as java.math.BigDecimal?
                result_.points = other.get("points") as java.math.BigDecimal?
                result_.fee = other.get("fee") as java.math.BigDecimal?
                result_.loanAmt = other.get("loanAmt") as java.math.BigDecimal?
                result_.downPmtAmt = other.get("downPmtAmt") as java.math.BigDecimal?
                result_.paymentAmt = other.get("paymentAmt") as java.math.BigDecimal?
                result_.equity36moPct = other.get("equity36moPct") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTMetric(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TMetric::class.java.getSimpleName()))
            }
        }
    }
}
