package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tItemPrice"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TItemPriceImpl::class)
interface TItemPrice : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("itemName")
    val itemName: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("price")
    val price: java.math.BigDecimal?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("itemName", this.itemName)
        context.put("price", this.price)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TItemPrice
        if (if (this.itemName != null) this.itemName != other.itemName else other.itemName != null) return false
        if (if (this.price != null) this.price != other.price else other.price != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.itemName != null) this.itemName.hashCode() else 0)
        result = 31 * result + (if (this.price != null) this.price.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("itemName=" + itemName)
        result_.append(", price=" + price)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTItemPrice(other: Any?): TItemPrice? {
            if (other == null) {
                return null
            } else if (other is TItemPrice?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TItemPriceImpl()
                result_.itemName = other.get("itemName") as String?
                result_.price = other.get("price") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTItemPrice(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TItemPrice::class.java.getSimpleName()))
            }
        }
    }
}
