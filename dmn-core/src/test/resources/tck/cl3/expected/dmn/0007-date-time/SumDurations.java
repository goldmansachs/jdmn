
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "sumDurations"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sumDurations",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SumDurations extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sumDurations",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final DtDuration1 dtDuration1;
    private final DtDuration2 dtDuration2;

    public SumDurations() {
        this(new DtDuration1(), new DtDuration2());
    }

    public SumDurations(DtDuration1 dtDuration1, DtDuration2 dtDuration2) {
        this.dtDuration1 = dtDuration1;
        this.dtDuration2 = dtDuration2;
    }

    public javax.xml.datatype.Duration apply(String day, String month, String year, String dateString, String dateTimeString, String durationString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, durationString, timeString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'SumDurations'", e);
            return null;
        }
    }

    public javax.xml.datatype.Duration apply(String day, String month, String year, String dateString, String dateTimeString, String durationString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((day != null ? number(day) : null), (month != null ? number(month) : null), (year != null ? number(year) : null), dateString, dateTimeString, durationString, timeString, annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'SumDurations'", e);
            return null;
        }
    }

    public javax.xml.datatype.Duration apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, String dateString, String dateTimeString, String durationString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(day, month, year, dateString, dateTimeString, durationString, timeString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public javax.xml.datatype.Duration apply(java.math.BigDecimal day, java.math.BigDecimal month, java.math.BigDecimal year, String dateString, String dateTimeString, String durationString, String timeString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("day", day);
            arguments.put("month", month);
            arguments.put("year", year);
            arguments.put("dateString", dateString);
            arguments.put("dateTimeString", dateTimeString);
            arguments.put("durationString", durationString);
            arguments.put("timeString", timeString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            javax.xml.datatype.Duration dtDuration1Output = dtDuration1.apply(durationString, annotationSet_, eventListener_, externalExecutor_);
            javax.xml.datatype.Duration dtDuration2Output = dtDuration2.apply(day, month, year, dateString, dateTimeString, timeString, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            javax.xml.datatype.Duration output_ = evaluate(dtDuration1Output, dtDuration2Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sumDurations' evaluation", e);
            return null;
        }
    }

    private javax.xml.datatype.Duration evaluate(javax.xml.datatype.Duration dtDuration1, javax.xml.datatype.Duration dtDuration2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return durationAdd(dtDuration1, dtDuration2);
    }
}
