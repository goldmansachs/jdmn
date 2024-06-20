package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tRow"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TRowImpl : TRow {
    @get:com.fasterxml.jackson.annotation.JsonGetter("col1")
    @set:com.fasterxml.jackson.annotation.JsonGetter("col1")
    override var col1: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("col2")
    @set:com.fasterxml.jackson.annotation.JsonGetter("col2")
    override var col2: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("col3")
    @set:com.fasterxml.jackson.annotation.JsonGetter("col3")
    override var col3: java.lang.Number? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("col4")
    @set:com.fasterxml.jackson.annotation.JsonGetter("col4")
    override var col4: java.lang.Number? = null

    constructor() {
    }

    constructor (col1: java.lang.Number?, col2: java.lang.Number?, col3: java.lang.Number?, col4: java.lang.Number?) {
        this.col1 = col1
        this.col2 = col2
        this.col3 = col3
        this.col4 = col4
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
