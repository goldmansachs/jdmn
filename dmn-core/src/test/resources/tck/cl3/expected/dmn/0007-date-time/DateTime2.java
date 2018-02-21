
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "DateTime2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "DateTime2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DateTime2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "DateTime2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final Date date;
    private final Time time;

    public DateTime2() {
        this(new Date(), new Time());
    }

    public DateTime2(Date date, Time time) {
        this.date = date;
        this.time = time;
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String day, String month, String year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, timeString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'DateTime2'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String day, String month, String year, String dateString, String dateTimeString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DateTime2'", e);
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
            type.TDateVariants dateOutput = date.apply(day, month, year, dateString, dateTimeString, annotationSet_, eventListener_, externalExecutor_);
            javax.xml.datatype.XMLGregorianCalendar timeOutput = time.apply(timeString, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            javax.xml.datatype.XMLGregorianCalendar output_ = evaluate(dateOutput, timeOutput, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'DateTime2' evaluation", e);
            return null;
        }
    }

    private javax.xml.datatype.XMLGregorianCalendar evaluate(type.TDateVariants date, javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return dateAndTime(((javax.xml.datatype.XMLGregorianCalendar)date.getFromString()), time);
    }
}
