package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "CompositeDateTime"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class CompositeDateTimeImpl : CompositeDateTime {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Date")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Date")
    override var date: java.time.LocalDate? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Time")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Time")
    override var time: java.time.temporal.TemporalAccessor? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    @set:com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    override var dateTime: java.time.temporal.TemporalAccessor? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("YearMonthDuration")
    @set:com.fasterxml.jackson.annotation.JsonGetter("YearMonthDuration")
    override var yearMonthDuration: java.time.temporal.TemporalAmount? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("DayTimeDuration")
    @set:com.fasterxml.jackson.annotation.JsonGetter("DayTimeDuration")
    override var dayTimeDuration: java.time.temporal.TemporalAmount? = null

    constructor() {
    }

    constructor (date: java.time.LocalDate?, dateTime: java.time.temporal.TemporalAccessor?, dayTimeDuration: java.time.temporal.TemporalAmount?, time: java.time.temporal.TemporalAccessor?, yearMonthDuration: java.time.temporal.TemporalAmount?) {
        this.date = date
        this.dateTime = dateTime
        this.dayTimeDuration = dayTimeDuration
        this.time = time
        this.yearMonthDuration = yearMonthDuration
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
