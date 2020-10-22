package com.gs.dmn.generated.proto.date_time_proto;

public class DateTimeProtoHandwrittenTest extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    private Date dateDecision = new Date();
    private Time timeDecision = new Time();
    private DateTime dateTimeDecision = new DateTime();

    @org.junit.Test
    public void testCase1() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        com.gs.dmn.runtime.cache.Cache cache_ = new com.gs.dmn.runtime.cache.DefaultCache();
        // Initialize input data
        javax.xml.datatype.XMLGregorianCalendar inputDate = date("2020-09-10");

        // Check Date
        checkValues(date("2020-09-10"), dateDecision.apply(inputDate, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check Date with proto request
        com.gs.dmn.generated.proto.date_time_proto.proto.DateRequest.Builder dateBuilder_ = com.gs.dmn.generated.proto.date_time_proto.proto.DateRequest.newBuilder();
        String inputDateProto0 = string(inputDate);
        dateBuilder_.setInputDate(inputDateProto0);
        com.gs.dmn.generated.proto.date_time_proto.proto.DateRequest dateRequest_ = dateBuilder_.build();
        checkValues("2020-09-10", dateDecision.apply(dateRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getDate());
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
        checkValues(time("12:10:10"), timeDecision.apply(inputTime, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check Time with proto request
        com.gs.dmn.generated.proto.date_time_proto.proto.TimeRequest.Builder timeBuilder_ = com.gs.dmn.generated.proto.date_time_proto.proto.TimeRequest.newBuilder();
        String inputTimeProto0 = string(inputTime);
        timeBuilder_.setInputTime(inputTimeProto0);
        com.gs.dmn.generated.proto.date_time_proto.proto.TimeRequest timeRequest_ = timeBuilder_.build();
        checkValues("12:10:10", timeDecision.apply(timeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getTime());
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
        checkValues(dateAndTime("2020-09-19T12:10:10"), dateTimeDecision.apply(inputDateTime, annotationSet_, eventListener_, externalExecutor_, cache_));

        // Check DateTime with proto request
        com.gs.dmn.generated.proto.date_time_proto.proto.DateTimeRequest.Builder dateTimeBuilder_ = com.gs.dmn.generated.proto.date_time_proto.proto.DateTimeRequest.newBuilder();
        String inputDateTimeProto0 = string(inputDateTime);
        dateTimeBuilder_.setInputDateTime(inputDateTimeProto0);
        com.gs.dmn.generated.proto.date_time_proto.proto.DateTimeRequest dateTimeRequest_ = dateTimeBuilder_.build();
        checkValues("2020-09-19T12:10:10", dateTimeDecision.apply(dateTimeRequest_, annotationSet_, eventListener_, externalExecutor_, cache_).getDateTime());
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
