
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "PostBureauRiskCategoryTable"])
class PostBureauRiskCategoryTableRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("PostBureauRiskCategoryTable")
    var postBureauRiskCategoryTable: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as PostBureauRiskCategoryTableRuleOutput
        if (if (this.postBureauRiskCategoryTable != null) this.postBureauRiskCategoryTable != other.postBureauRiskCategoryTable else other.postBureauRiskCategoryTable != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.postBureauRiskCategoryTable != null) this.postBureauRiskCategoryTable.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", postBureauRiskCategoryTable='%s'", postBureauRiskCategoryTable))
        result_.append(")")
        return result_.toString()
    }
}
