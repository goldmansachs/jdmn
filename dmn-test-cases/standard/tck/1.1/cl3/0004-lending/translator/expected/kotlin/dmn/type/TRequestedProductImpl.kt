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
    override var amount: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Rate")
    override var rate: kotlin.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Term")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Term")
    override var term: kotlin.Number? = null

    constructor() {
    }

    constructor (amount: kotlin.Number?, productType: String?, rate: kotlin.Number?, term: kotlin.Number?) {
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
