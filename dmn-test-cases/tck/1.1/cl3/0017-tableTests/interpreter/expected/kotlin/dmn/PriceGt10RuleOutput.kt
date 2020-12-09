
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "priceGt10"])
class PriceGt10RuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("priceGt10")
    var priceGt10: Boolean? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as PriceGt10RuleOutput
        if (if (this.priceGt10 != null) this.priceGt10 != other.priceGt10 else other.priceGt10 != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.priceGt10 != null) this.priceGt10.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", priceGt10='%s'", priceGt10))
        result_.append(")")
        return result_.toString()
    }
}
