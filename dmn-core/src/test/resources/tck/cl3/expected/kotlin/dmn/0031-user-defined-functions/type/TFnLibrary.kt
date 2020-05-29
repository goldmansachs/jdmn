package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tFnLibrary"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TFnLibraryImpl::class)
interface TFnLibrary : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("sumFn")
    val sumFn: kotlin.Any?

    @get:com.fasterxml.jackson.annotation.JsonGetter("subFn")
    val subFn: kotlin.Any?

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplyFn")
    val multiplyFn: kotlin.Any?

    @get:com.fasterxml.jackson.annotation.JsonGetter("divideFn")
    val divideFn: kotlin.Any?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("sumFn", this.sumFn)
        context.put("subFn", this.subFn)
        context.put("multiplyFn", this.multiplyFn)
        context.put("divideFn", this.divideFn)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TFnLibrary
        if (if (this.divideFn != null) this.divideFn != other.divideFn else other.divideFn != null) return false
        if (if (this.multiplyFn != null) this.multiplyFn != other.multiplyFn else other.multiplyFn != null) return false
        if (if (this.subFn != null) this.subFn != other.subFn else other.subFn != null) return false
        if (if (this.sumFn != null) this.sumFn != other.sumFn else other.sumFn != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.divideFn != null) this.divideFn.hashCode() else 0)
        result = 31 * result + (if (this.multiplyFn != null) this.multiplyFn.hashCode() else 0)
        result = 31 * result + (if (this.subFn != null) this.subFn.hashCode() else 0)
        result = 31 * result + (if (this.sumFn != null) this.sumFn.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("divideFn=" + divideFn)
        result_.append(", multiplyFn=" + multiplyFn)
        result_.append(", subFn=" + subFn)
        result_.append(", sumFn=" + sumFn)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTFnLibrary(other: Any?): TFnLibrary? {
            if (other == null) {
                return null
            } else if (other is TFnLibrary?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TFnLibraryImpl()
                result_.sumFn = other.get("sumFn") as kotlin.Any?
                result_.subFn = other.get("subFn") as kotlin.Any?
                result_.multiplyFn = other.get("multiplyFn") as kotlin.Any?
                result_.divideFn = other.get("divideFn") as kotlin.Any?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTFnLibrary(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TFnLibrary::class.java.getSimpleName()))
            }
        }
    }
}
