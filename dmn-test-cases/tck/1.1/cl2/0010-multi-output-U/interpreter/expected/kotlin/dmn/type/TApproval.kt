package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tApproval"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TApprovalImpl::class)
interface TApproval : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Status")
    val status: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    val rate: String?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("status", this.status)
        context.put("rate", this.rate)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TApproval
        if (if (this.rate != null) this.rate != other.rate else other.rate != null) return false
        if (if (this.status != null) this.status != other.status else other.status != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.rate != null) this.rate.hashCode() else 0)
        result = 31 * result + (if (this.status != null) this.status.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Rate=" + rate)
        result_.append(", Status=" + status)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTApproval(other: Any?): TApproval? {
            if (other == null) {
                return null
            } else if (other is TApproval?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TApprovalImpl()
                result_.status = other.get("Status") as String?
                result_.rate = other.get("Rate") as String?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTApproval(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TApproval::class.java.getSimpleName()))
            }
        }
    }
}
