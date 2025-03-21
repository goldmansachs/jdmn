
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "9acf44f2b05343d79fc35140c493c1e0/sid-8DBE416B-B1CA-43EC-BFE6-7D5DFA296EB6-d"])
class DateTest : com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision() {
    private val date = Date()

    @org.junit.jupiter.api.Test
    fun testCase1() {
        val context_ = com.gs.dmn.runtime.ExecutionContext()
        val inputDate: java.time.LocalDate? = date("2020-09-21")
        val inputTime: java.time.temporal.TemporalAccessor? = time("13:00:00+00:00")
        val inputDateTime: java.time.temporal.TemporalAccessor? = dateAndTime("2015-01-01T12:00:00+00:00")
        val compositeInputDateTime: type.TCompositeDateTime? = type.TCompositeDateTimeImpl(date("2020-09-21"), dateAndTime("2015-01-01T12:00:00+00:00"), time("13:00:00+00:00"))
        val date: java.time.LocalDate? = this.date.apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_)

        checkValues(date("2020-09-21"), date)

        // Make proto request
        var builder_: proto.DateRequest.Builder = proto.DateRequest.newBuilder()
        val inputDateProto: String = string(inputDate);
        builder_.setInputDate(inputDateProto);
        val inputTimeProto: String = string(inputTime);
        builder_.setInputTime(inputTimeProto);
        val inputDateTimeProto: String = string(inputDateTime);
        builder_.setInputDateTime(inputDateTimeProto);
        val compositeInputDateTimeProto: proto.TCompositeDateTime = type.TCompositeDateTime.toProto(compositeInputDateTime);
        if (compositeInputDateTimeProto != null) {
            builder_.setCompositeInputDateTime(compositeInputDateTimeProto);
        }
        val dateRequest_: proto.DateRequest = builder_.build()

        // Invoke apply method
        val dateResponse_: proto.DateResponse = this.date.applyProto(dateRequest_, context_)
        val dateProto_: String = dateResponse_.getDate()

        // Check results
        checkValues(string(date("2020-09-21")), dateProto_)
    }

    private fun checkValues(expected: Any?, actual: Any?) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual)
    }
}
