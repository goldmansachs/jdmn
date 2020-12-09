package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tRankedProducts"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TRankedProductsImpl::class)
interface TRankedProducts : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("metricsTable")
    val metricsTable: List<type.TMetric?>?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByRate")
    val rankByRate: List<type.TMetric?>?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByDownPmt")
    val rankByDownPmt: List<type.TMetric?>?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByMonthlyPmt")
    val rankByMonthlyPmt: List<type.TMetric?>?

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByEquityPct")
    val rankByEquityPct: List<type.TMetric?>?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("metricsTable", this.metricsTable)
        context.put("rankByRate", this.rankByRate)
        context.put("rankByDownPmt", this.rankByDownPmt)
        context.put("rankByMonthlyPmt", this.rankByMonthlyPmt)
        context.put("rankByEquityPct", this.rankByEquityPct)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TRankedProducts
        if (if (this.metricsTable != null) this.metricsTable != other.metricsTable else other.metricsTable != null) return false
        if (if (this.rankByDownPmt != null) this.rankByDownPmt != other.rankByDownPmt else other.rankByDownPmt != null) return false
        if (if (this.rankByEquityPct != null) this.rankByEquityPct != other.rankByEquityPct else other.rankByEquityPct != null) return false
        if (if (this.rankByMonthlyPmt != null) this.rankByMonthlyPmt != other.rankByMonthlyPmt else other.rankByMonthlyPmt != null) return false
        if (if (this.rankByRate != null) this.rankByRate != other.rankByRate else other.rankByRate != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.metricsTable != null) this.metricsTable.hashCode() else 0)
        result = 31 * result + (if (this.rankByDownPmt != null) this.rankByDownPmt.hashCode() else 0)
        result = 31 * result + (if (this.rankByEquityPct != null) this.rankByEquityPct.hashCode() else 0)
        result = 31 * result + (if (this.rankByMonthlyPmt != null) this.rankByMonthlyPmt.hashCode() else 0)
        result = 31 * result + (if (this.rankByRate != null) this.rankByRate.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("metricsTable=" + metricsTable)
        result_.append(", rankByDownPmt=" + rankByDownPmt)
        result_.append(", rankByEquityPct=" + rankByEquityPct)
        result_.append(", rankByMonthlyPmt=" + rankByMonthlyPmt)
        result_.append(", rankByRate=" + rankByRate)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTRankedProducts(other: Any?): TRankedProducts? {
            if (other == null) {
                return null
            } else if (other is TRankedProducts?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TRankedProductsImpl()
                result_.metricsTable = other.get("metricsTable") as List<type.TMetric?>?
                result_.rankByRate = other.get("rankByRate") as List<type.TMetric?>?
                result_.rankByDownPmt = other.get("rankByDownPmt") as List<type.TMetric?>?
                result_.rankByMonthlyPmt = other.get("rankByMonthlyPmt") as List<type.TMetric?>?
                result_.rankByEquityPct = other.get("rankByEquityPct") as List<type.TMetric?>?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTRankedProducts(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TRankedProducts::class.java.getSimpleName()))
            }
        }
    }
}
