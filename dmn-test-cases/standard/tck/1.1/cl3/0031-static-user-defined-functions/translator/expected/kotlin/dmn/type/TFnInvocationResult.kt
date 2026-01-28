package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tFnInvocationResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TFnInvocationResultImpl::class)
interface TFnInvocationResult : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("sumResult")
    val sumResult: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultPositional")
    val multiplicationResultPositional: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("divisionResultPositional")
    val divisionResultPositional: kotlin.Number?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.add("sumResult", this.sumResult)
        context.add("multiplicationResultPositional", this.multiplicationResultPositional)
        context.add("divisionResultPositional", this.divisionResultPositional)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TFnInvocationResult
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
        fun toTFnInvocationResult(other: Any?): TFnInvocationResult? {
            if (other == null) {
                return null
            } else if (other is TFnInvocationResult?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TFnInvocationResultImpl()
                var sumResult = other.get("sumResult")
                result_.sumResult = sumResult as kotlin.Number?
                var multiplicationResultPositional = other.get("multiplicationResultPositional")
                result_.multiplicationResultPositional = multiplicationResultPositional as kotlin.Number?
                var divisionResultPositional = other.get("divisionResultPositional")
                result_.divisionResultPositional = divisionResultPositional as kotlin.Number?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTFnInvocationResult(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TFnInvocationResult::class.java.getSimpleName()))
            }
        }
    }
}
