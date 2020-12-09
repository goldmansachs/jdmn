
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "CreditContingencyFactorTable"])
class CreditContingencyFactorTableRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("CreditContingencyFactorTable")
    var creditContingencyFactorTable: java.math.BigDecimal? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as CreditContingencyFactorTableRuleOutput
        if (if (this.creditContingencyFactorTable != null) this.creditContingencyFactorTable != other.creditContingencyFactorTable else other.creditContingencyFactorTable != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.creditContingencyFactorTable != null) this.creditContingencyFactorTable.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", creditContingencyFactorTable='%s'", creditContingencyFactorTable))
        result_.append(")")
        return result_.toString()
    }
}
