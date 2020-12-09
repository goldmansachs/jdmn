
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "ApprovalStatus"])
class ApprovalStatusRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("ApprovalStatus")
    var approvalStatus: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ApprovalStatusRuleOutput
        if (if (this.approvalStatus != null) this.approvalStatus != other.approvalStatus else other.approvalStatus != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.approvalStatus != null) this.approvalStatus.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", approvalStatus='%s'", approvalStatus))
        result_.append(")")
        return result_.toString()
    }
}
