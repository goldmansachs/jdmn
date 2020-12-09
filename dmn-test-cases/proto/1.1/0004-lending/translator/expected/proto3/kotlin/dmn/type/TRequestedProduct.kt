package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "tRequestedProduct"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.TRequestedProductImpl::class)
interface TRequestedProduct : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("ProductType")
    val productType: String?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Amount")
    val amount: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    val rate: java.math.BigDecimal?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Term")
    val term: java.math.BigDecimal?

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
                result_.amount = other.get("Amount") as java.math.BigDecimal?
                result_.rate = other.get("Rate") as java.math.BigDecimal?
                result_.term = other.get("Term") as java.math.BigDecimal?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toTRequestedProduct(other.toContext())
            } else if (other is proto.TRequestedProduct) {
                var result_: TRequestedProductImpl = TRequestedProductImpl()
                result_.productType = (other as proto.TRequestedProduct).getProductType()
                result_.amount = java.math.BigDecimal.valueOf((other as proto.TRequestedProduct).getAmount())
                result_.rate = java.math.BigDecimal.valueOf((other as proto.TRequestedProduct).getRate())
                result_.term = java.math.BigDecimal.valueOf((other as proto.TRequestedProduct).getTerm())
                return result_
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), TRequestedProduct::class.java.getSimpleName()))
            }
        }

        @JvmStatic
        fun toProto(other: TRequestedProduct?): proto.TRequestedProduct {
            var result_: proto.TRequestedProduct.Builder = proto.TRequestedProduct.newBuilder()
            if (other != null) {
                var productTypeProto_: String = (if ((other as TRequestedProduct).productType == null) "" else (other as TRequestedProduct).productType!!)
                result_.setProductType(productTypeProto_)
                var amountProto_: Double = (if ((other as TRequestedProduct).amount == null) 0.0 else (other as TRequestedProduct).amount!!.toDouble())
                result_.setAmount(amountProto_)
                var rateProto_: Double = (if ((other as TRequestedProduct).rate == null) 0.0 else (other as TRequestedProduct).rate!!.toDouble())
                result_.setRate(rateProto_)
                var termProto_: Double = (if ((other as TRequestedProduct).term == null) 0.0 else (other as TRequestedProduct).term!!.toDouble())
                result_.setTerm(termProto_)
            }
            return result_.build()
        }

        @JvmStatic
        fun toProto(other: List<TRequestedProduct?>?): List<proto.TRequestedProduct>? {
            if (other == null) {
                return null
            } else {
                return other.stream().map({o -> toProto(o)}).collect(java.util.stream.Collectors.toList())
            }
        }
    }
}
