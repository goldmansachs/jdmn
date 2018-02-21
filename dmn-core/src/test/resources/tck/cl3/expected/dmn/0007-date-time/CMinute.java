
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "cMinute"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "cMinute",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class CMinute extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "cMinute",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final DateTime2 dateTime2;

    public CMinute() {
        this(new DateTime2());
    }

    public CMinute(DateTime2 dateTime2) {
        this.dateTime2 = dateTime2;
    }

    public java.math.BigDecimal apply(String day, String month, String year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, timeString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'CMinute'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String day, String month, String year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CMinute'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
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
            java.math.BigDecimal output_ = evaluate(dateTime2Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'cMinute' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(javax.xml.datatype.XMLGregorianCalendar dateTime2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return minute(dateTime2);
    }
}
