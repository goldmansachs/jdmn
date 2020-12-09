
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "ExtraDaysCase3"])
class ExtraDaysCase3RuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("ExtraDaysCase3")
    var extraDaysCase3: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ExtraDaysCase3RuleOutput
        if (if (this.extraDaysCase3 != null) this.extraDaysCase3 != other.extraDaysCase3 else other.extraDaysCase3 != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.extraDaysCase3 != null) this.extraDaysCase3.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", extraDaysCase3='%s'", extraDaysCase3))
        result_.append(")")
        return result_.toString()
    }
}
