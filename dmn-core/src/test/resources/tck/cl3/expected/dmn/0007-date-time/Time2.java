
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Time2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Time2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Time2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Time2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final DateTime2 dateTime2;

    public Time2() {
        this(new DateTime2());
    }

    public Time2(DateTime2 dateTime2) {
        this.dateTime2 = dateTime2;
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String day, String month, String year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, timeString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Time2'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String day, String month, String year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Time2'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arguments_.put("day", day);
            arguments_.put("month", month);
            arguments_.put("year", year);
            arguments_.put("dateString", dateString);
            arguments_.put("dateTimeString", dateTimeString);
            arguments_.put("timeString", timeString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments_);

            // Apply child decisions
            javax.xml.datatype.XMLGregorianCalendar dateTime2Output = dateTime2.apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            javax.xml.datatype.XMLGregorianCalendar output_ = evaluate(dateTime2Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Time2' evaluation", e);
            return null;
        }
    }

    private javax.xml.datatype.XMLGregorianCalendar evaluate(javax.xml.datatype.XMLGregorianCalendar dateTime2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return time(dateTime2);
    }
}
