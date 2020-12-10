
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "processPriorIssues"])
class ProcessPriorIssuesRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("processPriorIssues")
    var processPriorIssues: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ProcessPriorIssuesRuleOutput
        if (if (this.processPriorIssues != null) this.processPriorIssues != other.processPriorIssues else other.processPriorIssues != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.processPriorIssues != null) this.processPriorIssues.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", processPriorIssues='%s'", processPriorIssues))
        result_.append(")")
        return result_.toString()
    }
}
