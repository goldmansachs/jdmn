package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tDeptTable"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TDeptTableImpl : TDeptTable {
    @get:com.fasterxml.jackson.annotation.JsonGetter("number")
    @set:com.fasterxml.jackson.annotation.JsonGetter("number")
    override var number: java.math.BigDecimal? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("name")
    @set:com.fasterxml.jackson.annotation.JsonGetter("name")
    override var name: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("manager")
    @set:com.fasterxml.jackson.annotation.JsonGetter("manager")
    override var manager: String? = null

    constructor() {
    }

    constructor (manager: String?, name: String?, number: java.math.BigDecimal?) {
        this.manager = manager
        this.name = name
        this.number = number
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
