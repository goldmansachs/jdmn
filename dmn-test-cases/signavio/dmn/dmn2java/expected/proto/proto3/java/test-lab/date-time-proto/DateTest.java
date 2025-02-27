
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "9acf44f2b05343d79fc35140c493c1e0/sid-8DBE416B-B1CA-43EC-BFE6-7D5DFA296EB6-d"})
public class DateTest extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    private final Date date = new Date();

    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        java.time.LocalDate inputDate = date("2020-09-21");
        java.time.temporal.TemporalAccessor inputTime = time("13:00:00+00:00");
        java.time.temporal.TemporalAccessor inputDateTime = dateAndTime("2015-01-01T12:00:00+00:00");
        type.TCompositeDateTime compositeInputDateTime = new type.TCompositeDateTimeImpl(date("2020-09-21"), dateAndTime("2015-01-01T12:00:00+00:00"), time("13:00:00+00:00"));
        java.time.LocalDate date = this.date.apply(compositeInputDateTime, inputDate, inputDateTime, inputTime, context_);

        checkValues(date("2020-09-21"), date);

        // Make proto request
        proto.DateRequest.Builder builder_ = proto.DateRequest.newBuilder();
        String inputDateProto = string(inputDate);
        builder_.setInputDate(inputDateProto);
        String inputTimeProto = string(inputTime);
        builder_.setInputTime(inputTimeProto);
        String inputDateTimeProto = string(inputDateTime);
        builder_.setInputDateTime(inputDateTimeProto);
        proto.TCompositeDateTime compositeInputDateTimeProto = type.TCompositeDateTime.toProto(compositeInputDateTime);
        if (compositeInputDateTimeProto != null) {
            builder_.setCompositeInputDateTime(compositeInputDateTimeProto);
        }
        proto.DateRequest dateRequest_ = builder_.build();

        // Invoke apply method
        proto.DateResponse dateResponse_ = this.date.applyProto(dateRequest_, context_);
        String dateProto_ = dateResponse_.getDate();

        // Check results
        checkValues(string(date("2020-09-21")), dateProto_);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
