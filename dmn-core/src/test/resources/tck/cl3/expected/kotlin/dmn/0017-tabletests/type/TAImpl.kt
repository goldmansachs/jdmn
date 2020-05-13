package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tA"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TAImpl : TA {
    @get:com.fasterxml.jackson.annotation.JsonGetter("name")
    @set:com.fasterxml.jackson.annotation.JsonGetter("name")
    override var name: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("price")
    @set:com.fasterxml.jackson.annotation.JsonGetter("price")
    override var price: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (name: String?, price: java.math.BigDecimal?) {
        this.name = name
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
