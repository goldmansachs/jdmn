
@javax.annotation.Generated(value = ["decisionTableRuleOutput.ftl", "BureauCallTypeTable"])
class BureauCallTypeTableRuleOutput(matched: Boolean) : com.gs.dmn.runtime.RuleOutput(matched) {
    @com.fasterxml.jackson.annotation.JsonProperty("BureauCallTypeTable")
    var bureauCallTypeTable: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as BureauCallTypeTableRuleOutput
        if (if (this.bureauCallTypeTable != null) this.bureauCallTypeTable != other.bureauCallTypeTable else other.bureauCallTypeTable != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = 0
        result = 31 * result + (if (this.bureauCallTypeTable != null) this.bureauCallTypeTable.hashCode() else 0)

        return result
    }

    override fun toString(): String {
        val result_ = StringBuilder("(matched=" + isMatched())
        result_.append(String.format(", bureauCallTypeTable='%s'", bureauCallTypeTable))
        result_.append(")")
        return result_.toString()
    }
}
