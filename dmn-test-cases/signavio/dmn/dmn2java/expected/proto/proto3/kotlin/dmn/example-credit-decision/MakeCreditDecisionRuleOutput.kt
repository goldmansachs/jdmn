
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "makeCreditDecision"])
class MakeCreditDecisionRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("makeCreditDecision")
    var makeCreditDecision: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as MakeCreditDecisionRuleOutput
        if (if (this.makeCreditDecision != null) this.makeCreditDecision != other.makeCreditDecision else other.makeCreditDecision != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.makeCreditDecision != null) this.makeCreditDecision.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", makeCreditDecision='%s'", makeCreditDecision))
        result_.append(")")
        return result_.toString()
    }
}
