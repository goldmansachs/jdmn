
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "date-time-proto.dmn"])
class DateTimeProtoTest : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    @org.junit.jupiter.api.Test
    fun testCase1() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val inputDate: java.time.LocalDate? = date("2020-09-10")

        // Check 'Date'
        checkValues(date("2020-09-10"), Date().apply(inputDate, context_))

        // Check 'Date' with proto request
        val dateBuilder_: proto.DateRequest.Builder = proto.DateRequest.newBuilder()
        val inputDateProto0: String = string(inputDate)
        dateBuilder_.setInputDate(inputDateProto0)
        val dateRequest_: proto.DateRequest = dateBuilder_.build()
        checkValues(string(date("2020-09-10")), Date().applyProto(dateRequest_, context_).getDate())
    }

    @org.junit.jupiter.api.Test
    fun testCase2() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val inputTime: java.time.temporal.TemporalAccessor? = time("12:10:10")

        // Check 'Time'
        checkValues(time("12:10:10"), Time().apply(inputTime, context_))

        // Check 'Time' with proto request
        val timeBuilder_: proto.TimeRequest.Builder = proto.TimeRequest.newBuilder()
        val inputTimeProto0: String = string(inputTime)
        timeBuilder_.setInputTime(inputTimeProto0)
        val timeRequest_: proto.TimeRequest = timeBuilder_.build()
        checkValues(string(time("12:10:10")), Time().applyProto(timeRequest_, context_).getTime())
    }

    @org.junit.jupiter.api.Test
    fun testCase3() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val inputDateTime: java.time.temporal.TemporalAccessor? = dateAndTime("2020-09-19T12:10:10")

        // Check 'DateTime'
        checkValues(dateAndTime("2020-09-19T12:10:10"), DateTime().apply(inputDateTime, context_))

        // Check 'DateTime' with proto request
        val dateTimeBuilder_: proto.DateTimeRequest.Builder = proto.DateTimeRequest.newBuilder()
        val inputDateTimeProto0: String = string(inputDateTime)
        dateTimeBuilder_.setInputDateTime(inputDateTimeProto0)
        val dateTimeRequest_: proto.DateTimeRequest = dateTimeBuilder_.build()
        checkValues(string(dateAndTime("2020-09-19T12:10:10")), DateTime().applyProto(dateTimeRequest_, context_).getDateTime())
    }

    @org.junit.jupiter.api.Test
    fun testCase4() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val cache_ = context_.getCache()
        // Initialize input data
        val compositeInputDateTime: type.CompositeDateTime? = type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"))

        // Check 'CompositeDateTime'
        checkValues(type.CompositeDateTime.toCompositeDateTime(com.gs.dmn.runtime.Context().add("Date", date("2020-09-19")).add("DateTime", dateAndTime("2020-09-19T12:10:10")).add("DayTimeDuration", duration("P1D")).add("Time", time("12:10:10")).add("YearMonthDuration", duration("P1Y"))), CompositeDateTime().apply(compositeInputDateTime, context_))

        // Check 'CompositeDateTime' with proto request
        val compositeDateTimeBuilder_: proto.CompositeDateTimeRequest.Builder = proto.CompositeDateTimeRequest.newBuilder()
        val compositeInputDateTimeProto0: proto.CompositeDateTime = type.CompositeDateTime.toProto(compositeInputDateTime)
        if (compositeInputDateTimeProto0 != null) {
            compositeDateTimeBuilder_.setCompositeInputDateTime(compositeInputDateTimeProto0)
        }
        val compositeDateTimeRequest_: proto.CompositeDateTimeRequest = compositeDateTimeBuilder_.build()
        checkValues(type.CompositeDateTime.toProto(type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"))), CompositeDateTime().applyProto(compositeDateTimeRequest_, context_).getCompositeDateTime())
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
