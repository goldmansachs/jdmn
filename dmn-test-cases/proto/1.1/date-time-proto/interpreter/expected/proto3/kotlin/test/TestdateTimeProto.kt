
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "date-time-proto.dmn"])
class TestdateTimeProto : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    @org.junit.Test
    fun testCase1() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val inputDate: javax.xml.datatype.XMLGregorianCalendar? = date("2020-09-10")

        // Check Date
        checkValues(date("2020-09-10"), Date().apply(inputDate, annotationSet_, eventListener_, externalExecutor_, cache_))

        // Check Date with proto request
        val dateBuilder_: proto.DateRequest.Builder = proto.DateRequest.newBuilder()
        val inputDateProto0: String = string(inputDate)
        dateBuilder_.setInputDate(inputDateProto0)
        val dateRequest_: proto.DateRequest = dateBuilder_.build()
        checkValues(string(date("2020-09-10")), Date().apply(dateRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getDate())
    }

    @org.junit.Test
    fun testCase2() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val inputTime: javax.xml.datatype.XMLGregorianCalendar? = time("12:10:10")

        // Check Time
        checkValues(time("12:10:10"), Time().apply(inputTime, annotationSet_, eventListener_, externalExecutor_, cache_))

        // Check Time with proto request
        val timeBuilder_: proto.TimeRequest.Builder = proto.TimeRequest.newBuilder()
        val inputTimeProto0: String = string(inputTime)
        timeBuilder_.setInputTime(inputTimeProto0)
        val timeRequest_: proto.TimeRequest = timeBuilder_.build()
        checkValues(string(time("12:10:10")), Time().apply(timeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getTime())
    }

    @org.junit.Test
    fun testCase3() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val inputDateTime: javax.xml.datatype.XMLGregorianCalendar? = dateAndTime("2020-09-19T12:10:10")

        // Check DateTime
        checkValues(dateAndTime("2020-09-19T12:10:10"), DateTime().apply(inputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_))

        // Check DateTime with proto request
        val dateTimeBuilder_: proto.DateTimeRequest.Builder = proto.DateTimeRequest.newBuilder()
        val inputDateTimeProto0: String = string(inputDateTime)
        dateTimeBuilder_.setInputDateTime(inputDateTimeProto0)
        val dateTimeRequest_: proto.DateTimeRequest = dateTimeBuilder_.build()
        checkValues(string(dateAndTime("2020-09-19T12:10:10")), DateTime().apply(dateTimeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getDateTime())
    }

    @org.junit.Test
    fun testCase4() {
        val annotationSet_ = com.gs.dmn.runtime.annotation.AnnotationSet()
        val eventListener_ = com.gs.dmn.runtime.listener.NopEventListener()
        val externalExecutor_ = com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor()
        val cache_ = com.gs.dmn.runtime.cache.DefaultCache()
        // Initialize input data
        val compositeInputDateTime: type.CompositeDateTime? = type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"))

        // Check CompositeDateTime
        checkValues(type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y")), CompositeDateTime().apply(compositeInputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_))

        // Check CompositeDateTime with proto request
        val compositeDateTimeBuilder_: proto.CompositeDateTimeRequest.Builder = proto.CompositeDateTimeRequest.newBuilder()
        val compositeInputDateTimeProto0: proto.CompositeDateTime = type.CompositeDateTime.toProto(compositeInputDateTime)
        if (compositeInputDateTimeProto0 != null) {
            compositeDateTimeBuilder_.setCompositeInputDateTime(compositeInputDateTimeProto0)
        }
        val compositeDateTimeRequest_: proto.CompositeDateTimeRequest = compositeDateTimeBuilder_.build()
        checkValues(type.CompositeDateTime.toProto(type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"))), CompositeDateTime().apply(compositeDateTimeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getCompositeDateTime())
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
