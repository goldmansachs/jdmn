
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "ApprovalStatus"])
class ApprovalStatusRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("ApprovalStatus")
    var approvalStatus: String? = null
    var approvalStatusPriority: Int? = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ApprovalStatusRuleOutput
        if (if (this.approvalStatus != null) this.approvalStatus != other.approvalStatus else other.approvalStatus != null) return false
        if (if (this.approvalStatusPriority != null) this.approvalStatusPriority != other.approvalStatusPriority else other.approvalStatusPriority != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.approvalStatus != null) this.approvalStatus.hashCode() else 0)
        result = 31 * result + (if (this.approvalStatusPriority != null) this.approvalStatusPriority.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", approvalStatus='%s'", approvalStatus))
        result_.append(")")
        return result_.toString()
    }

    override fun sort(matchedResults_: MutableList<com.gs.dmn.runtime.RuleOutput>): MutableList<com.gs.dmn.runtime.RuleOutput> {
        val approvalStatusPairs: MutableList<com.gs.dmn.runtime.Pair<String?, Int?>> = ArrayList()
        matchedResults_.forEach({ (it as ApprovalStatusRuleOutput)
            approvalStatusPairs.add(com.gs.dmn.runtime.Pair(it.approvalStatus, it.approvalStatusPriority))
        })
        approvalStatusPairs.sortWith(com.gs.dmn.runtime.PairComparator())

        val result_: MutableList<com.gs.dmn.runtime.RuleOutput> = ArrayList<com.gs.dmn.runtime.RuleOutput>()
        for(i in 0 until matchedResults_.size) {
            var output_ = ApprovalStatusRuleOutput(true)
            output_.approvalStatus = approvalStatusPairs.get(i).getLeft()
            output_.approvalStatusPriority = approvalStatusPairs.get(i).getRight()
            result_.add(output_)
        }
        return result_
    }
}
