package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tRequestedProduct"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TRequestedProductImpl : TRequestedProduct {
    @get:com.fasterxml.jackson.annotation.JsonGetter("ProductType")
    @set:com.fasterxml.jackson.annotation.JsonGetter("ProductType")
    override var productType: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Amount")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Amount")
    override var amount: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    override var rate: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Term")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Term")
    override var term: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (amount: java.math.BigDecimal?, productType: String?, rate: java.math.BigDecimal?, term: java.math.BigDecimal?) {
        this.amount = amount
        this.productType = productType
        this.rate = rate
        this.term = term
    }

    override fun equals(other: Any?): Boolean {
        return equalTo(other)
    }

    override fun hashCode(): Int {
        return hash()
    }

    @Override
    override fun toString(): String {
        return asString()
    }
}
