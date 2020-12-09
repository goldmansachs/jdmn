package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tRankedProducts"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TRankedProductsImpl : TRankedProducts {
    @get:com.fasterxml.jackson.annotation.JsonGetter("metricsTable")
    @set:com.fasterxml.jackson.annotation.JsonGetter("metricsTable")
    override var metricsTable: List<type.TMetric?>? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByRate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rankByRate")
    override var rankByRate: List<type.TMetric?>? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByDownPmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rankByDownPmt")
    override var rankByDownPmt: List<type.TMetric?>? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByMonthlyPmt")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rankByMonthlyPmt")
    override var rankByMonthlyPmt: List<type.TMetric?>? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("rankByEquityPct")
    @set:com.fasterxml.jackson.annotation.JsonGetter("rankByEquityPct")
    override var rankByEquityPct: List<type.TMetric?>? = null

    constructor() {
    }

    constructor (metricsTable: List<type.TMetric?>?, rankByDownPmt: List<type.TMetric?>?, rankByEquityPct: List<type.TMetric?>?, rankByMonthlyPmt: List<type.TMetric?>?, rankByRate: List<type.TMetric?>?) {
        this.metricsTable = metricsTable
        this.rankByDownPmt = rankByDownPmt
        this.rankByEquityPct = rankByEquityPct
        this.rankByMonthlyPmt = rankByMonthlyPmt
        this.rankByRate = rankByRate
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
