
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "0007-date-time.dmn"})
public class Test0007DateTime extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    @org.junit.Test
    public void testCase001() {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = new com.gs.dmn.runtime.annotation.AnnotationSet();
        com.gs.dmn.runtime.listener.EventListener eventListener_ = new com.gs.dmn.runtime.listener.NopEventListener();
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor();
        // Initialize input data
        String dateString = "2015-12-24";
        String timeString = "00:00:01-01:00";
        String dateTimeString = "2016-12-24T23:59:00-08:00";
        java.math.BigDecimal hours = number("12");
        java.math.BigDecimal minutes = number("59");
        java.math.BigDecimal seconds = number("01.3");
        javax.xml.datatype.Duration timezone = duration("-PT1H");
        java.math.BigDecimal year = number("1999");
        java.math.BigDecimal month = number("11");
        java.math.BigDecimal day = number("22");
        javax.xml.datatype.Duration oneHour = duration("PT1H");
        String durationString = "P13DT2H14S";

        // Check DateTime
        javax.xml.datatype.XMLGregorianCalendar dateTimeOutput = new DateTime().apply(dateTimeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(dateAndTime("2016-12-24T23:59:00-08:00"), dateTimeOutput);
        // Check Date
        type.TDateVariants dateOutput = new Date().apply(day, month, year, dateString, dateTimeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(new type.TDateVariantsImpl(date("2016-12-24"), date("2015-12-24"), date("1999-11-22")), dateOutput);
        // Check Time
        javax.xml.datatype.XMLGregorianCalendar timeOutput = new Time().apply(timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(time("00:00:01-01:00"), timeOutput);
        // Check DateTime2
        javax.xml.datatype.XMLGregorianCalendar dateTime2Output = new DateTime2().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(dateAndTime("2015-12-24T00:00:01-01:00"), dateTime2Output);
        // Check Time2
        javax.xml.datatype.XMLGregorianCalendar time2Output = new Time2().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(time("00:00:01-01:00"), time2Output);
        // Check Time3
        javax.xml.datatype.XMLGregorianCalendar time3Output = new Time3().apply(hours, minutes, seconds, timezone, annotationSet_, eventListener_, externalExecutor_);
        checkValues(time("12:59:01.3-01:00"), time3Output);
        // Check dtDuration1
        javax.xml.datatype.Duration dtDuration1Output = new DtDuration1().apply(durationString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(duration("P13DT2H14S"), dtDuration1Output);
        // Check dtDuration2
        javax.xml.datatype.Duration dtDuration2Output = new DtDuration2().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(duration("P367DT6H58M59S"), dtDuration2Output);
        // Check sumDurations
        javax.xml.datatype.Duration sumDurationsOutput = new SumDurations().apply(day, month, year, dateString, dateTimeString, durationString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(duration("P380DT8H59M13S"), sumDurationsOutput);
        // Check ymDuration2
        javax.xml.datatype.Duration ymDuration2Output = new YmDuration2().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(duration("P1Y0M"), ymDuration2Output);
        // Check cDay
        java.math.BigDecimal cDayOutput = new CDay().apply(day, month, year, dateString, dateTimeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("24"), cDayOutput);
        // Check cYear
        java.math.BigDecimal cYearOutput = new CYear().apply(day, month, year, dateString, dateTimeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("2015"), cYearOutput);
        // Check cMonth
        java.math.BigDecimal cMonthOutput = new CMonth().apply(day, month, year, dateString, dateTimeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("12"), cMonthOutput);
        // Check cHour
        java.math.BigDecimal cHourOutput = new CHour().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("0"), cHourOutput);
        // Check cMinute
        java.math.BigDecimal cMinuteOutput = new CMinute().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("0"), cMinuteOutput);
        // Check cSecond
        java.math.BigDecimal cSecondOutput = new CSecond().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("1"), cSecondOutput);
        // Check cOffset
        javax.xml.datatype.Duration cOffsetOutput = new COffset().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(duration("-PT1H"), cOffsetOutput);
        // Check years
        java.math.BigDecimal yearsOutput = new Years().apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("1"), yearsOutput);
        // Check seconds
        java.math.BigDecimal secondsOutput = new Seconds().apply(durationString, annotationSet_, eventListener_, externalExecutor_);
        checkValues(number("14"), secondsOutput);
    }

    private void checkValues(Object expected, Object actual) {
        com.gs.dmn.runtime.Assert.assertEquals(expected, actual);
    }
}
