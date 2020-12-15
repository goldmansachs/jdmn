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
            } else if (other is proto.TBureauData) {
                var result_: TBureauDataImpl = TBureauDataImpl()
                result_.creditScore = java.math.BigDecimal.valueOf((other as proto.TBureauData).getCreditScore())
                result_.bankrupt = (other as proto.TBureauData).getBankrupt()
                return result_
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TBureauData::class.java.getSimpleName()))
            }
        }

        @JvmStatic
        fun toProto(other: TBureauData?): proto.TBureauData {
            var result_: proto.TBureauData.Builder = proto.TBureauData.newBuilder()
            if (other != null) {
                var creditScoreProto_: Double = (if ((other as TBureauData).creditScore == null) 0.0 else (other as TBureauData).creditScore!!.toDouble())
                result_.setCreditScore(creditScoreProto_)
                var bankruptProto_: Boolean = (if ((other as TBureauData).bankrupt == null) false else (other as TBureauData).bankrupt!!)
                result_.setBankrupt(bankruptProto_)
            }
            return result_.build()
        }

        @JvmStatic
        fun toProto(other: List<TBureauData?>?): List<proto.TBureauData>? {
            if (other == null) {
                return null
            } else {
                return other.stream().map({o -> toProto(o)}).collect(java.util.stream.Collectors.toList())
            }
        }
    }
}
