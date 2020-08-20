
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "compareAgainstLendingThreshold"])
class CompareAgainstLendingThresholdRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("compareAgainstLendingThreshold")
    var compareAgainstLendingThreshold: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as CompareAgainstLendingThresholdRuleOutput
        if (if (this.compareAgainstLendingThreshold != null) this.compareAgainstLendingThreshold != other.compareAgainstLendingThreshold else other.compareAgainstLendingThreshold != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.compareAgainstLendingThreshold != null) this.compareAgainstLendingThreshold.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", compareAgainstLendingThreshold='%s'", compareAgainstLendingThreshold))
        result_.append(")")
        return result_.toString()
    }
}
