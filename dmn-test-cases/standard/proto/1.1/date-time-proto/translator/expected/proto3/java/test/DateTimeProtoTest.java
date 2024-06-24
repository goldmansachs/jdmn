
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "date-time-proto.dmn"})
public class DateTimeProtoTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.jupiter.api.Test
    public void testCase1() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar inputDate = date("2020-09-10");

        // Check 'Date'
        checkValues(date("2020-09-10"), new Date().apply(inputDate, context_));

        // Check 'Date' with proto request
        proto.DateRequest.Builder dateBuilder_ = proto.DateRequest.newBuilder();
        String inputDateProto0 = string(inputDate);
        dateBuilder_.setInputDate(inputDateProto0);
        proto.DateRequest dateRequest_ = dateBuilder_.build();
        checkValues(string(date("2020-09-10")), new Date().applyProto(dateRequest_, context_).getDate());
    }

    @org.junit.jupiter.api.Test
    public void testCase2() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar inputTime = time("12:10:10");

        // Check 'Time'
        checkValues(time("12:10:10"), new Time().apply(inputTime, context_));

        // Check 'Time' with proto request
        proto.TimeRequest.Builder timeBuilder_ = proto.TimeRequest.newBuilder();
        String inputTimeProto0 = string(inputTime);
        timeBuilder_.setInputTime(inputTimeProto0);
        proto.TimeRequest timeRequest_ = timeBuilder_.build();
        checkValues(string(time("12:10:10")), new Time().applyProto(timeRequest_, context_).getTime());
    }

    @org.junit.jupiter.api.Test
    public void testCase3() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = dateAndTime("2020-09-19T12:10:10");

        // Check 'DateTime'
        checkValues(dateAndTime("2020-09-19T12:10:10"), new DateTime().apply(inputDateTime, context_));

        // Check 'DateTime' with proto request
        proto.DateTimeRequest.Builder dateTimeBuilder_ = proto.DateTimeRequest.newBuilder();
        String inputDateTimeProto0 = string(inputDateTime);
        dateTimeBuilder_.setInputDateTime(inputDateTimeProto0);
        proto.DateTimeRequest dateTimeRequest_ = dateTimeBuilder_.build();
        checkValues(string(dateAndTime("2020-09-19T12:10:10")), new DateTime().applyProto(dateTimeRequest_, context_).getDateTime());
    }

    @org.junit.jupiter.api.Test
    public void testCase4() {
        com.gs.dmn.runtime.ExecutionContext context_ = new com.gs.dmn.runtime.ExecutionContext();
        com.gs.dmn.runtime.cache.Cache cache_ = context_.getCache();
        // Initialize input data
        type.CompositeDateTime compositeInputDateTime = new type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"));

        // Check 'CompositeDateTime'
        checkValues(type.CompositeDateTime.toCompositeDateTime(new com.gs.dmn.runtime.Context().add("Date", date("2020-09-19")).add("DateTime", dateAndTime("2020-09-19T12:10:10")).add("DayTimeDuration", duration("P1D")).add("Time", time("12:10:10")).add("YearMonthDuration", duration("P1Y"))), new CompositeDateTime().apply(compositeInputDateTime, context_));

        // Check 'CompositeDateTime' with proto request
        proto.CompositeDateTimeRequest.Builder compositeDateTimeBuilder_ = proto.CompositeDateTimeRequest.newBuilder();
        proto.CompositeDateTime compositeInputDateTimeProto0 = type.CompositeDateTime.toProto(compositeInputDateTime);
        if (compositeInputDateTimeProto0 != null) {
            compositeDateTimeBuilder_.setCompositeInputDateTime(compositeInputDateTimeProto0);
        }
        proto.CompositeDateTimeRequest compositeDateTimeRequest_ = compositeDateTimeBuilder_.build();
        checkValues(type.CompositeDateTime.toProto(new type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"))), new CompositeDateTime().applyProto(compositeDateTimeRequest_, context_).getCompositeDateTime());
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
