
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "ApplicationRiskScoreModel"])
class ApplicationRiskScoreModelRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("ApplicationRiskScoreModel")
    var applicationRiskScoreModel: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ApplicationRiskScoreModelRuleOutput
        if (if (this.applicationRiskScoreModel != null) this.applicationRiskScoreModel != other.applicationRiskScoreModel else other.applicationRiskScoreModel != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.applicationRiskScoreModel != null) this.applicationRiskScoreModel.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", applicationRiskScoreModel='%s'", applicationRiskScoreModel))
        result_.append(")")
        return result_.toString()
    }
}
