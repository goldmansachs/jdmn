package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tFnInvocationPositionalResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TFnInvocationPositionalResultImpl::class)
interface TFnInvocationPositionalResult : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    val sumResult: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    val divisionResultPositional: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    val multiplicationResultPositional: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("sumResult", this.sumResult)
        context.put("divisionResultPositional", this.divisionResultPositional)
        context.put("multiplicationResultPositional", this.multiplicationResultPositional)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TFnInvocationPositionalResult
        if (if (this.divisionResultPositional != null) this.divisionResultPositional != other.divisionResultPositional else other.divisionResultPositional != null) return false
        if (if (this.multiplicationResultPositional != null) this.multiplicationResultPositional != other.multiplicationResultPositional else other.multiplicationResultPositional != null) return false
        if (if (this.sumResult != null) this.sumResult != other.sumResult else other.sumResult != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.divisionResultPositional != null) this.divisionResultPositional.hashCode() else 0)
        result = 31 * result + (if (this.multiplicationResultPositional != null) this.multiplicationResultPositional.hashCode() else 0)
        result = 31 * result + (if (this.sumResult != null) this.sumResult.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("divisionResultPositional=" + divisionResultPositional)
        result_.append(", multiplicationResultPositional=" + multiplicationResultPositional)
        result_.append(", sumResult=" + sumResult)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTFnInvocationPositionalResult(other: Any?): TFnInvocationPositionalResult? {
            if (other == null) {
                return null
            } else if (other is TFnInvocationPositionalResult?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TFnInvocationPositionalResultImpl()
                result_.sumResult = other.get("sumResult") as java.math.BigDecimal?
                result_.divisionResultPositional = other.get("divisionResultPositional") as java.math.BigDecimal?
                result_.multiplicationResultPositional = other.get("multiplicationResultPositional") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTFnInvocationPositionalResult(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TFnInvocationPositionalResult::class.java.getSimpleName()))
            }
        }
    }
}
