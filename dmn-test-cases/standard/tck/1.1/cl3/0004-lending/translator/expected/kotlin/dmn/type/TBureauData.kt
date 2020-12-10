package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tBureauData"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TBureauDataImpl::class)
interface TBureauData : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("CreditScore")
    val creditScore: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Bankrupt")
    val bankrupt: Boolean?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("creditScore", this.creditScore)
        context.put("bankrupt", this.bankrupt)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TBureauData
        if (if (this.bankrupt != null) this.bankrupt != other.bankrupt else other.bankrupt != null) return false
        if (if (this.creditScore != null) this.creditScore != other.creditScore else other.creditScore != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.bankrupt != null) this.bankrupt.hashCode() else 0)
        result = 31 * result + (if (this.creditScore != null) this.creditScore.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Bankrupt=" + bankrupt)
        result_.append(", CreditScore=" + creditScore)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTBureauData(other: Any?): TBureauData? {
            if (other == null) {
                return null
            } else if (other is TBureauData?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TBureauDataImpl()
                result_.creditScore = other.get("CreditScore") as java.math.BigDecimal?
                result_.bankrupt = other.get("Bankrupt") as Boolean?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTBureauData(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TBureauData::class.java.getSimpleName()))
            }
        }
    }
}
