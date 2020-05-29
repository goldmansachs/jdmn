package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tA"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TAImpl::class)
interface TA : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("name")
    val name: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("price")
    val price: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("name", this.name)
        context.put("price", this.price)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TA
        if (if (this.name != null) this.name != other.name else other.name != null) return false
        if (if (this.price != null) this.price != other.price else other.price != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.name != null) this.name.hashCode() else 0)
        result = 31 * result + (if (this.price != null) this.price.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("name=" + name)
        result_.append(", price=" + price)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTA(other: Any?): TA? {
            if (other == null) {
                return null
            } else if (other is TA?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TAImpl()
                result_.name = other.get("name") as String?
                result_.price = other.get("price") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTA(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TA::class.java.getSimpleName()))
            }
        }
    }
}
