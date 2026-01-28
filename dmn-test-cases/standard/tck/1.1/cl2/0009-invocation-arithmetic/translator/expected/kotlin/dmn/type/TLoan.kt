package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tLoan"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TLoanImpl::class)
interface TLoan : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("amount")
    val amount: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rate")
    val rate: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("term")
    val term: kotlin.Number?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.add("amount", this.amount)
        context.add("rate", this.rate)
        context.add("term", this.term)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TLoan
        if (if (this.amount != null) this.amount != other.amount else other.amount != null) return false
        if (if (this.rate != null) this.rate != other.rate else other.rate != null) return false
        if (if (this.term != null) this.term != other.term else other.term != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.amount != null) this.amount.hashCode() else 0)
        result = 31 * result + (if (this.rate != null) this.rate.hashCode() else 0)
        result = 31 * result + (if (this.term != null) this.term.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("amount=" + amount)
        result_.append(", rate=" + rate)
        result_.append(", term=" + term)
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
                var amount = other.get("amount")
                result_.amount = amount as kotlin.Number?
                var rate = other.get("rate")
                result_.rate = rate as kotlin.Number?
                var term = other.get("term")
                result_.term = term as kotlin.Number?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTLoan(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TLoan::class.java.getSimpleName()))
            }
        }
    }
}
