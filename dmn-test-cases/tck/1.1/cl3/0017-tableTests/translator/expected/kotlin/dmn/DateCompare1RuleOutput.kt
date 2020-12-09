
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "dateCompare1"])
class DateCompare1RuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("dateCompare1")
    var dateCompare1: Boolean? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as DateCompare1RuleOutput
        if (if (this.dateCompare1 != null) this.dateCompare1 != other.dateCompare1 else other.dateCompare1 != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.dateCompare1 != null) this.dateCompare1.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", dateCompare1='%s'", dateCompare1))
        result_.append(")")
        return result_.toString()
    }
}
