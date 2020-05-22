package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tFnInvocationNamedResult"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TFnInvocationNamedResultImpl::class)
interface TFnInvocationNamedResult : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("subResult")
    val subResult: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("subResultMixed")
    val subResultMixed: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("divisionResultNamed")
    val divisionResultNamed: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplicationResultNamed")
    val multiplicationResultNamed: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("subResult", this.subResult)
        context.put("subResultMixed", this.subResultMixed)
        context.put("divisionResultNamed", this.divisionResultNamed)
        context.put("multiplicationResultNamed", this.multiplicationResultNamed)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TFnInvocationNamedResult
        if (if (this.divisionResultNamed != null) this.divisionResultNamed != other.divisionResultNamed else other.divisionResultNamed != null) return false
        if (if (this.multiplicationResultNamed != null) this.multiplicationResultNamed != other.multiplicationResultNamed else other.multiplicationResultNamed != null) return false
        if (if (this.subResult != null) this.subResult != other.subResult else other.subResult != null) return false
        if (if (this.subResultMixed != null) this.subResultMixed != other.subResultMixed else other.subResultMixed != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.divisionResultNamed != null) this.divisionResultNamed.hashCode() else 0)
        result = 31 * result + (if (this.multiplicationResultNamed != null) this.multiplicationResultNamed.hashCode() else 0)
        result = 31 * result + (if (this.subResult != null) this.subResult.hashCode() else 0)
        result = 31 * result + (if (this.subResultMixed != null) this.subResultMixed.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("divisionResultNamed=" + divisionResultNamed)
        result_.append(", multiplicationResultNamed=" + multiplicationResultNamed)
        result_.append(", subResult=" + subResult)
        result_.append(", subResultMixed=" + subResultMixed)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTFnInvocationNamedResult(other: Any?): TFnInvocationNamedResult? {
            if (other == null) {
                return null
            } else if (other is TFnInvocationNamedResult?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TFnInvocationNamedResultImpl()
                result_.subResult = other.get("subResult") as java.math.BigDecimal?
                result_.subResultMixed = other.get("subResultMixed") as java.math.BigDecimal?
                result_.divisionResultNamed = other.get("divisionResultNamed") as java.math.BigDecimal?
                result_.multiplicationResultNamed = other.get("multiplicationResultNamed") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTFnInvocationNamedResult(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TFnInvocationNamedResult::class.java.getSimpleName()))
            }
        }
    }
}
