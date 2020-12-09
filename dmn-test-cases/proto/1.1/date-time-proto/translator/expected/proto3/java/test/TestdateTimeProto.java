
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "date-time-proto.dmn"})
public class TestdateTimeProto extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar inputDate = date("2020-09-10");

        // Check Date
        checkValues(date("2020-09-10"), new Date().apply(inputDate, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check Date with proto request
        proto.DateRequest.Builder dateBuilder_ = proto.DateRequest.newBuilder();
        String inputDateProto0 = string(inputDate);
        dateBuilder_.setInputDate(inputDateProto0);
        proto.DateRequest dateRequest_ = dateBuilder_.build();
        checkValues(string(date("2020-09-10")), new Date().apply(dateRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getDate());
    }

    @org.junit.Test
    public void testCase2() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar inputTime = time("12:10:10");

        // Check Time
        checkValues(time("12:10:10"), new Time().apply(inputTime, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check Time with proto request
        proto.TimeRequest.Builder timeBuilder_ = proto.TimeRequest.newBuilder();
        String inputTimeProto0 = string(inputTime);
        timeBuilder_.setInputTime(inputTimeProto0);
        proto.TimeRequest timeRequest_ = timeBuilder_.build();
        checkValues(string(time("12:10:10")), new Time().apply(timeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getTime());
    }

    @org.junit.Test
    public void testCase3() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar inputDateTime = dateAndTime("2020-09-19T12:10:10");

        // Check DateTime
        checkValues(dateAndTime("2020-09-19T12:10:10"), new DateTime().apply(inputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check DateTime with proto request
        proto.DateTimeRequest.Builder dateTimeBuilder_ = proto.DateTimeRequest.newBuilder();
        String inputDateTimeProto0 = string(inputDateTime);
        dateTimeBuilder_.setInputDateTime(inputDateTimeProto0);
        proto.DateTimeRequest dateTimeRequest_ = dateTimeBuilder_.build();
        checkValues(string(dateAndTime("2020-09-19T12:10:10")), new DateTime().apply(dateTimeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getDateTime());
    }

    @org.junit.Test
    public void testCase4() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        type.CompositeDateTime compositeInputDateTime = new type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"));

        // Check CompositeDateTime
        checkValues(new type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y")), new CompositeDateTime().apply(compositeInputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check CompositeDateTime with proto request
        proto.CompositeDateTimeRequest.Builder compositeDateTimeBuilder_ = proto.CompositeDateTimeRequest.newBuilder();
        proto.CompositeDateTime compositeInputDateTimeProto0 = type.CompositeDateTime.toProto(compositeInputDateTime);
        if (compositeInputDateTimeProto0 != null) {
            compositeDateTimeBuilder_.setCompositeInputDateTime(compositeInputDateTimeProto0);
        }
        proto.CompositeDateTimeRequest compositeDateTimeRequest_ = compositeDateTimeBuilder_.build();
        checkValues(type.CompositeDateTime.toProto(new type.CompositeDateTimeImpl(date("2020-09-19"), dateAndTime("2020-09-19T12:10:10"), duration("P1D"), time("12:10:10"), duration("P1Y"))), new CompositeDateTime().apply(compositeDateTimeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getCompositeDateTime());
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
