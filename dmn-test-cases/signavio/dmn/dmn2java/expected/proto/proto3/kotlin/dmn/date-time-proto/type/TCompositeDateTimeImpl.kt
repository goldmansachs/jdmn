package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "tCompositeDateTime"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class TCompositeDateTimeImpl : TCompositeDateTime {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Date")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Date")
    override var date: java.time.LocalDate? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Time")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Time")
    override var time: java.time.temporal.TemporalAccessor? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    @set:com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    override var dateTime: java.time.temporal.TemporalAccessor? = null

    constructor() {
    }

    constructor (date: java.time.LocalDate?, dateTime: java.time.temporal.TemporalAccessor?, time: java.time.temporal.TemporalAccessor?) {
        this.date = date
        this.dateTime = dateTime
        this.time = time
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
