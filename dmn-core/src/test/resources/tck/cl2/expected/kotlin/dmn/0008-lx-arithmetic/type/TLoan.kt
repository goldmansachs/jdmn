package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tLoan"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TLoanImpl::class)
interface TLoan : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("principal")
    val principal: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    val rate: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("termMonths")
    val termMonths: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("principal", this.principal)
        context.put("rate", this.rate)
        context.put("termMonths", this.termMonths)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TLoan
        if (if (this.principal != null) this.principal != other.principal else other.principal != null) return false
        if (if (this.rate != null) this.rate != other.rate else other.rate != null) return false
        if (if (this.termMonths != null) this.termMonths != other.termMonths else other.termMonths != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.principal != null) this.principal.hashCode() else 0)
        result = 31 * result + (if (this.rate != null) this.rate.hashCode() else 0)
        result = 31 * result + (if (this.termMonths != null) this.termMonths.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("principal=" + principal)
        result_.append(", rate=" + rate)
        result_.append(", termMonths=" + termMonths)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTLoan(other: Any?): TLoan? {
            if (other == null) {
                return null
            } else if (other is TLoan?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TLoanImpl()
                result_.principal = other.get("principal") as java.math.BigDecimal?
                result_.rate = other.get("rate") as java.math.BigDecimal?
                result_.termMonths = other.get("termMonths") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTLoan(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TLoan::class.java.getSimpleName()))
            }
        }
    }
}
