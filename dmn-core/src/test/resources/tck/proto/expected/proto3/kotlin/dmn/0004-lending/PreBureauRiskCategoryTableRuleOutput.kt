
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "PreBureauRiskCategoryTable"])
class PreBureauRiskCategoryTableRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("PreBureauRiskCategoryTable")
    var preBureauRiskCategoryTable: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as PreBureauRiskCategoryTableRuleOutput
        if (if (this.preBureauRiskCategoryTable != null) this.preBureauRiskCategoryTable != other.preBureauRiskCategoryTable else other.preBureauRiskCategoryTable != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.preBureauRiskCategoryTable != null) this.preBureauRiskCategoryTable.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", preBureauRiskCategoryTable='%s'", preBureauRiskCategoryTable))
        result_.append(")")
        return result_.toString()
    }
}
