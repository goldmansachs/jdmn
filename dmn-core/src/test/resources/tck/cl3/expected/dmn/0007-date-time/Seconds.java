
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "seconds"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "seconds",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Seconds extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "seconds",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final DtDuration1 dtDuration1;

    public Seconds() {
        this(new DtDuration1());
    }

    public Seconds(DtDuration1 dtDuration1) {
        this.dtDuration1 = dtDuration1;
    }

    public java.math.BigDecimal apply(String durationString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(durationString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(String durationString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arguments_.put("durationString", durationString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments_);

            // Apply child decisions
            javax.xml.datatype.Duration dtDuration1Output = dtDuration1.apply(durationString, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            java.math.BigDecimal output_ = evaluate(dtDuration1Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'seconds' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(javax.xml.datatype.Duration dtDuration1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return seconds(dtDuration1);
    }
}
