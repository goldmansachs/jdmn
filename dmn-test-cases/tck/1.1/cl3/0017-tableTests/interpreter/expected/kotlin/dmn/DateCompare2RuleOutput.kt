
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "dateCompare2"])
class DateCompare2RuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("dateCompare2")
    var dateCompare2: Boolean? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as DateCompare2RuleOutput
        if (if (this.dateCompare2 != null) this.dateCompare2 != other.dateCompare2 else other.dateCompare2 != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.dateCompare2 != null) this.dateCompare2.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", dateCompare2='%s'", dateCompare2))
        result_.append(")")
        return result_.toString()
    }
}
