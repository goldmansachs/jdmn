package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tEmployeeTable"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TEmployeeTableImpl : TEmployeeTable {
    @get:com.fasterxml.jackson.annotation.JsonGetter("id")
    @set:com.fasterxml.jackson.annotation.JsonGetter("id")
    override var id: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("name")
    @set:com.fasterxml.jackson.annotation.JsonGetter("name")
    override var name: String? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("deptNum")
    @set:com.fasterxml.jackson.annotation.JsonGetter("deptNum")
    override var deptNum: java.math.BigDecimal? = null

    constructor() {
    }

    constructor (deptNum: java.math.BigDecimal?, id: String?, name: String?) {
        this.deptNum = deptNum
        this.id = id
        this.name = name
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
