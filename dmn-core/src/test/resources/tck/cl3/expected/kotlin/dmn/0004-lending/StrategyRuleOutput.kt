
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "Strategy"])
class StrategyRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("Strategy")
    var strategy: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as StrategyRuleOutput
        if (if (this.strategy != null) this.strategy != other.strategy else other.strategy != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.strategy != null) this.strategy.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", strategy='%s'", strategy))
        result_.append(")")
        return result_.toString()
    }
}
