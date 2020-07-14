
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "priceInRange"])
class PriceInRangeRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("priceInRange")
    var priceInRange: String? = null
    var priceInRangePriority: Int? = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as PriceInRangeRuleOutput
        if (if (this.priceInRange != null) this.priceInRange != other.priceInRange else other.priceInRange != null) return false
        if (if (this.priceInRangePriority != null) this.priceInRangePriority != other.priceInRangePriority else other.priceInRangePriority != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.priceInRange != null) this.priceInRange.hashCode() else 0)
        result = 31 * result + (if (this.priceInRangePriority != null) this.priceInRangePriority.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", priceInRange='%s'", priceInRange))
        result_.append(")")
        return result_.toString()
    }

    override fun sort(matchedResults_: MutableList<com.gs.dmn.runtime.RuleOutput>): MutableList<com.gs.dmn.runtime.RuleOutput> {
        val priceInRangePairs: MutableList<com.gs.dmn.runtime.Pair<String?, Int?>> = ArrayList()
        matchedResults_.forEach({ (it as PriceInRangeRuleOutput)
            priceInRangePairs.add(com.gs.dmn.runtime.Pair(it.priceInRange, it.priceInRangePriority))
        })
        priceInRangePairs.sortWith(com.gs.dmn.runtime.PairComparator())

        val result_: MutableList<com.gs.dmn.runtime.RuleOutput> = ArrayList<com.gs.dmn.runtime.RuleOutput>()
        for(i in 0 until matchedResults_.size) {
            var output_ = PriceInRangeRuleOutput(true)
            output_.priceInRange = priceInRangePairs.get(i).getLeft()
            output_.priceInRangePriority = priceInRangePairs.get(i).getRight()
            result_.add(output_)
        }
        return result_
    }
}
