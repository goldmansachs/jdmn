package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tItemPrice"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TItemPriceImpl : TItemPrice {
    @get:com.fasterxml.jackson.annotation.JsonGetter("itemName")
    @set:com.fasterxml.jackson.annotation.JsonGetter("itemName")
    override var itemName: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("price")
    @set:com.fasterxml.jackson.annotation.JsonGetter("price")
    override var price: kotlin.Number? = null

    constructor() {
    }

    constructor (itemName: String?, price: kotlin.Number?) {
        this.itemName = itemName
        this.price = price
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
