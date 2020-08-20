
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "ExtraDaysCase1"])
class ExtraDaysCase1RuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("ExtraDaysCase1")
    var extraDaysCase1: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ExtraDaysCase1RuleOutput
        if (if (this.extraDaysCase1 != null) this.extraDaysCase1 != other.extraDaysCase1 else other.extraDaysCase1 != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.extraDaysCase1 != null) this.extraDaysCase1.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", extraDaysCase1='%s'", extraDaysCase1))
        result_.append(")")
        return result_.toString()
    }
}
