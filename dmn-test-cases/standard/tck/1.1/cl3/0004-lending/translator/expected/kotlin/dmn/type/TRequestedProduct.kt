package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tRequestedProduct"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TRequestedProductImpl::class)
interface TRequestedProduct : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("ProductType")
    val productType: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Amount")
    val amount: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    val rate: kotlin.Number?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Term")
    val term: kotlin.Number?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("productType", this.productType)
        context.put("amount", this.amount)
        context.put("rate", this.rate)
        context.put("term", this.term)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as TRequestedProduct
        if (if (this.amount != null) this.amount != other.amount else other.amount != null) return false
        if (if (this.productType != null) this.productType != other.productType else other.productType != null) return false
        if (if (this.rate != null) this.rate != other.rate else other.rate != null) return false
        if (if (this.term != null) this.term != other.term else other.term != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.amount != null) this.amount.hashCode() else 0)
        result = 31 * result + (if (this.productType != null) this.productType.hashCode() else 0)
        result = 31 * result + (if (this.rate != null) this.rate.hashCode() else 0)
        result = 31 * result + (if (this.term != null) this.term.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Amount=" + amount)
        result_.append(", ProductType=" + productType)
        result_.append(", Rate=" + rate)
        result_.append(", Term=" + term)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toTRequestedProduct(other: Any?): TRequestedProduct? {
            if (other == null) {
                return null
            } else if (other is TRequestedProduct?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = TRequestedProductImpl()
                result_.productType = other.get("ProductType") as String?
                result_.amount = other.get("Amount") as kotlin.Number?
                result_.rate = other.get("Rate") as kotlin.Number?
                result_.term = other.get("Term") as kotlin.Number?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTRequestedProduct(other.toContext())
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TRequestedProduct::class.java.getSimpleName()))
            }
        }
    }
}
