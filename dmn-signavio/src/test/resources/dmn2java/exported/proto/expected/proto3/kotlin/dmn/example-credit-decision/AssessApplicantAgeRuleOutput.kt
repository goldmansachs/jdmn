
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "assessApplicantAge"])
class AssessApplicantAgeRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("assessApplicantAge")
    var assessApplicantAge: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as AssessApplicantAgeRuleOutput
        if (if (this.assessApplicantAge != null) this.assessApplicantAge != other.assessApplicantAge else other.assessApplicantAge != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.assessApplicantAge != null) this.assessApplicantAge.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", assessApplicantAge='%s'", assessApplicantAge))
        result_.append(")")
        return result_.toString()
    }
}
