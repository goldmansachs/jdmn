package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tFnLibrary"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TFnLibraryImpl : TFnLibrary {
    @get:com.fasterxml.jackson.annotation.JsonGetter("sumFn")
    @set:com.fasterxml.jackson.annotation.JsonGetter("sumFn")
    override var sumFn: kotlin.Any? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("subFn")
    @set:com.fasterxml.jackson.annotation.JsonGetter("subFn")
    override var subFn: kotlin.Any? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("multiplyFn")
    @set:com.fasterxml.jackson.annotation.JsonGetter("multiplyFn")
    override var multiplyFn: kotlin.Any? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("divideFn")
    @set:com.fasterxml.jackson.annotation.JsonGetter("divideFn")
    override var divideFn: kotlin.Any? = null

    constructor() {
    }

    constructor (divideFn: kotlin.Any?, multiplyFn: kotlin.Any?, subFn: kotlin.Any?, sumFn: kotlin.Any?) {
        this.divideFn = divideFn
        this.multiplyFn = multiplyFn
        this.subFn = subFn
        this.sumFn = sumFn
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
