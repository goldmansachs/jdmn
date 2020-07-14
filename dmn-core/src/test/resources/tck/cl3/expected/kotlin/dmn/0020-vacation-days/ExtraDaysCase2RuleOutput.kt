
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "ExtraDaysCase2"])
class ExtraDaysCase2RuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("ExtraDaysCase2")
    var extraDaysCase2: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as ExtraDaysCase2RuleOutput
        if (if (this.extraDaysCase2 != null) this.extraDaysCase2 != other.extraDaysCase2 else other.extraDaysCase2 != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.extraDaysCase2 != null) this.extraDaysCase2.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", extraDaysCase2='%s'", extraDaysCase2))
        result_.append(")")
        return result_.toString()
    }
}
