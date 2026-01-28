package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tLoanProduct"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TLoanProductImpl::class)
interface TLoanProduct : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("lenderName")
    val lenderName: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    val rate: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("points")
    val points: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("fee")
    val fee: kotlin.Number?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.add("lenderName", this.lenderName)
        context.add("rate", this.rate)
        context.add("points", this.points)
        context.add("fee", this.fee)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TLoanProduct
        if (if (this.fee != null) this.fee != other.fee else other.fee != null) return false
        if (if (this.lenderName != null) this.lenderName != other.lenderName else other.lenderName != null) return false
        if (if (this.points != null) this.points != other.points else other.points != null) return false
        if (if (this.rate != null) this.rate != other.rate else other.rate != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.fee != null) this.fee.hashCode() else 0)
        result = 31 * result + (if (this.lenderName != null) this.lenderName.hashCode() else 0)
        result = 31 * result + (if (this.points != null) this.points.hashCode() else 0)
        result = 31 * result + (if (this.rate != null) this.rate.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("fee=" + fee)
        result_.append(", lenderName=" + lenderName)
        result_.append(", points=" + points)
        result_.append(", rate=" + rate)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTLoanProduct(other: Any?): TLoanProduct? {
            if (other == null) {
                return null
            } else if (other is TLoanProduct?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TLoanProductImpl()
                var lenderName = other.get("lenderName")
                result_.lenderName = lenderName as String?
                var rate = other.get("rate")
                result_.rate = rate as kotlin.Number?
                var points = other.get("points")
                result_.points = points as kotlin.Number?
                var fee = other.get("fee")
                result_.fee = fee as kotlin.Number?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTLoanProduct(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TLoanProduct::class.java.getSimpleName()))
            }
        }
    }
}
