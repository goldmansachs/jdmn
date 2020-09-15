package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinition.ftl", "CompositeDateTime"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
class CompositeDateTimeImpl : CompositeDateTime {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Date")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Date")
    override var date: javax.xml.datatype.XMLGregorianCalendar? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("Time")
    @set:com.fasterxml.jackson.annotation.JsonGetter("Time")
    override var time: javax.xml.datatype.XMLGregorianCalendar? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    @set:com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    override var dateTime: javax.xml.datatype.XMLGregorianCalendar? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("YearMonthDuration")
    @set:com.fasterxml.jackson.annotation.JsonGetter("YearMonthDuration")
    override var yearMonthDuration: javax.xml.datatype.Duration? = null

    @get:com.fasterxml.jackson.annotation.JsonGetter("DayTimeDuration")
    @set:com.fasterxml.jackson.annotation.JsonGetter("DayTimeDuration")
    override var dayTimeDuration: javax.xml.datatype.Duration? = null

    constructor() {
    }

    constructor (date: javax.xml.datatype.XMLGregorianCalendar?, dateTime: javax.xml.datatype.XMLGregorianCalendar?, dayTimeDuration: javax.xml.datatype.Duration?, time: javax.xml.datatype.XMLGregorianCalendar?, yearMonthDuration: javax.xml.datatype.Duration?) {
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
