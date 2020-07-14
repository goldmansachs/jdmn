
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "RoutingRules"])
class RoutingRulesRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("RoutingRules")
    var routingRules: String? = null
    var routingRulesPriority: Int? = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as RoutingRulesRuleOutput
        if (if (this.routingRules != null) this.routingRules != other.routingRules else other.routingRules != null) return false
        if (if (this.routingRulesPriority != null) this.routingRulesPriority != other.routingRulesPriority else other.routingRulesPriority != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.routingRules != null) this.routingRules.hashCode() else 0)
        result = 31 * result + (if (this.routingRulesPriority != null) this.routingRulesPriority.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", routingRules='%s'", routingRules))
        result_.append(")")
        return result_.toString()
    }

    override fun sort(matchedResults_: MutableList<com.gs.dmn.runtime.RuleOutput>): MutableList<com.gs.dmn.runtime.RuleOutput> {
        val routingRulesPairs: MutableList<com.gs.dmn.runtime.Pair<String?, Int?>> = ArrayList()
        matchedResults_.forEach({ (it as RoutingRulesRuleOutput)
            routingRulesPairs.add(com.gs.dmn.runtime.Pair(it.routingRules, it.routingRulesPriority))
        })
        routingRulesPairs.sortWith(com.gs.dmn.runtime.PairComparator())

        val result_: MutableList<com.gs.dmn.runtime.RuleOutput> = ArrayList<com.gs.dmn.runtime.RuleOutput>()
        for(i in 0 until matchedResults_.size) {
            var output_ = RoutingRulesRuleOutput(true)
            output_.routingRules = routingRulesPairs.get(i).getLeft()
            output_.routingRulesPriority = routingRulesPairs.get(i).getRight()
            result_.add(output_)
        }
        return result_
    }
}
