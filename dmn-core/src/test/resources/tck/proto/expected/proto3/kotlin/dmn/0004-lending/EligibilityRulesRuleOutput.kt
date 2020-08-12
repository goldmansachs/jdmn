
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "EligibilityRules"])
class EligibilityRulesRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("EligibilityRules")
    var eligibilityRules: String? = null
    var eligibilityRulesPriority: Int? = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as EligibilityRulesRuleOutput
        if (if (this.eligibilityRules != null) this.eligibilityRules != other.eligibilityRules else other.eligibilityRules != null) return false
        if (if (this.eligibilityRulesPriority != null) this.eligibilityRulesPriority != other.eligibilityRulesPriority else other.eligibilityRulesPriority != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.eligibilityRules != null) this.eligibilityRules.hashCode() else 0)
        result = 31 * result + (if (this.eligibilityRulesPriority != null) this.eligibilityRulesPriority.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", eligibilityRules='%s'", eligibilityRules))
        result_.append(")")
        return result_.toString()
    }

    override fun sort(matchedResults_: MutableList<com.gs.dmn.runtime.RuleOutput>): MutableList<com.gs.dmn.runtime.RuleOutput> {
        val eligibilityRulesPairs: MutableList<com.gs.dmn.runtime.Pair<String?, Int?>> = ArrayList()
        matchedResults_.forEach({ (it as EligibilityRulesRuleOutput)
            eligibilityRulesPairs.add(com.gs.dmn.runtime.Pair(it.eligibilityRules, it.eligibilityRulesPriority))
        })
        eligibilityRulesPairs.sortWith(com.gs.dmn.runtime.PairComparator())

        val result_: MutableList<com.gs.dmn.runtime.RuleOutput> = ArrayList<com.gs.dmn.runtime.RuleOutput>()
        for(i in 0 until matchedResults_.size) {
            var output_ = EligibilityRulesRuleOutput(true)
            output_.eligibilityRules = eligibilityRulesPairs.get(i).getLeft()
            output_.eligibilityRulesPriority = eligibilityRulesPairs.get(i).getRight()
            result_.add(output_)
        }
        return result_
    }
}
