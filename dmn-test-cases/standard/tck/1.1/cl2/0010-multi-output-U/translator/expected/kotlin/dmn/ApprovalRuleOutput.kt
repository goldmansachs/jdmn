
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "Approval"])
class ApprovalRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("Status")
    var status: String? = null
    @com.fasterxml.jackson.annotation.JsonProperty("Rate")
    var rate: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ApprovalRuleOutput
        if (if (this.status != null) this.status != other.status else other.status != null) return false
        if (if (this.rate != null) this.rate != other.rate else other.rate != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.status != null) this.status.hashCode() else 0)
        result = 31 * result + (if (this.rate != null) this.rate.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", status='%s'", status))
        result_.append(String.format(", rate='%s'", rate))
        result_.append(")")
        return result_.toString()
    }
}
