
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Time3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Time3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Time3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Time3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Time3() {
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String hours, String minutes, String seconds, String timezone, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((hours != null ? number(hours) : null), (minutes != null ? number(minutes) : null), (seconds != null ? number(seconds) : null), (timezone != null ? duration(timezone) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Time3'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(String hours, String minutes, String seconds, String timezone, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((hours != null ? number(hours) : null), (minutes != null ? number(minutes) : null), (seconds != null ? number(seconds) : null), (timezone != null ? duration(timezone) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Time3'", e);
            return null;
        }
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(java.math.BigDecimal hours, java.math.BigDecimal minutes, java.math.BigDecimal seconds, javax.xml.datatype.Duration timezone, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(hours, minutes, seconds, timezone, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public javax.xml.datatype.XMLGregorianCalendar apply(java.math.BigDecimal hours, java.math.BigDecimal minutes, java.math.BigDecimal seconds, javax.xml.datatype.Duration timezone, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("hours", hours);
            arguments.put("minutes", minutes);
            arguments.put("seconds", seconds);
            arguments.put("timezone", timezone);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            javax.xml.datatype.XMLGregorianCalendar output_ = evaluate(hours, minutes, seconds, timezone, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Time3' evaluation", e);
            return null;
        }
    }

    private javax.xml.datatype.XMLGregorianCalendar evaluate(java.math.BigDecimal hours, java.math.BigDecimal minutes, java.math.BigDecimal seconds, javax.xml.datatype.Duration timezone, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return time(hours, minutes, seconds, timezone);
    }
}
