package type

import java.util.*

@javax.annotation.Generated(value = ["itemDefinitionInterface.ftl", "CompositeDateTime"])
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(`as` = type.CompositeDateTimeImpl::class)
interface CompositeDateTime : com.gs.dmn.runtime.DMNType {
    @get:com.fasterxml.jackson.annotation.JsonGetter("Date")
    val date: javax.xml.datatype.XMLGregorianCalendar?

    @get:com.fasterxml.jackson.annotation.JsonGetter("Time")
    val time: javax.xml.datatype.XMLGregorianCalendar?

    @get:com.fasterxml.jackson.annotation.JsonGetter("DateTime")
    val dateTime: javax.xml.datatype.XMLGregorianCalendar?

    override fun toContext(): com.gs.dmn.runtime.Context {
        val context = com.gs.dmn.runtime.Context()
        context.put("date", this.date)
        context.put("time", this.time)
        context.put("dateTime", this.dateTime)
        return context
    }

    fun equalTo(o: Any?): Boolean {
        if (this === o) return true
        if (javaClass != o?.javaClass) return false

        val other = o as CompositeDateTime
        if (if (this.date != null) this.date != other.date else other.date != null) return false
        if (if (this.dateTime != null) this.dateTime != other.dateTime else other.dateTime != null) return false
        if (if (this.time != null) this.time != other.time else other.time != null) return false

        return true
    }

    fun hash(): Int {
        var result = 0
        result = 31 * result + (if (this.date != null) this.date.hashCode() else 0)
        result = 31 * result + (if (this.dateTime != null) this.dateTime.hashCode() else 0)
        result = 31 * result + (if (this.time != null) this.time.hashCode() else 0)
        return result
    }

    fun asString(): String {
        val result_ = StringBuilder("{")
        result_.append("Date=" + date)
        result_.append(", DateTime=" + dateTime)
        result_.append(", Time=" + time)
        result_.append("}")
        return result_.toString()
    }

    companion object {
        @JvmStatic
        fun toCompositeDateTime(other: Any?): CompositeDateTime? {
            if (other == null) {
                return null
            } else if (other is CompositeDateTime?) {
                return other
            } else if (other is com.gs.dmn.runtime.Context) {
                var result_ = CompositeDateTimeImpl()
                result_.date = other.get("Date") as javax.xml.datatype.XMLGregorianCalendar?
                result_.time = other.get("Time") as javax.xml.datatype.XMLGregorianCalendar?
                result_.dateTime = other.get("DateTime") as javax.xml.datatype.XMLGregorianCalendar?
                return result_
            } else if (other is com.gs.dmn.runtime.DMNType) {
                return toCompositeDateTime(other.toContext())
            } else if (other is proto.CompositeDateTime) {
                var result_: CompositeDateTimeImpl = CompositeDateTimeImpl()
                result_.date = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.date((other as proto.CompositeDateTime).getDate()))
                result_.time = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.time((other as proto.CompositeDateTime).getTime()))
                result_.dateTime = com.gs.dmn.feel.lib.DefaultFEELLib.INSTANCE.dateAndTime((other as proto.CompositeDateTime).getDateTime()))
                return result_
            } else {
                throw com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.javaClass.getSimpleName(), CompositeDateTime::class.java.getSimpleName()))
            }
        }

        @JvmStatic
        fun toProto(other: CompositeDateTime?): proto.CompositeDateTime {
            var result_: proto.CompositeDateTime.Builder = proto.CompositeDateTime.newBuilder()
            if (other != null) {
                var dateProto_: String = string((other as CompositeDateTime).date)
                result_.setDate(dateProto_)
                var timeProto_: String = string((other as CompositeDateTime).time)
                result_.setTime(timeProto_)
                var dateTimeProto_: String = string((other as CompositeDateTime).dateTime)
                result_.setDateTime(dateTimeProto_)
            }
            return result_.build()
        }

        @JvmStatic
        fun toProto(other: List<CompositeDateTime?>?): List<proto.CompositeDateTime>? {
            if (other == null) {
                return null
            } else {
                return other.stream().map({o -> toProto(o)}).collect(java.util.stream.Collectors.toList())
            }
        }
    }
}
