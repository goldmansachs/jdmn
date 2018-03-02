
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "Circumference"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Circumference",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Circumference extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Circumference",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static final Circumference INSTANCE = new Circumference();

    private Circumference() {
    }

    public static java.math.BigDecimal Circumference(java.math.BigDecimal radius, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(radius, annotationSet_, eventListener_, externalExecutor_);
    }

    private java.math.BigDecimal apply(java.math.BigDecimal radius, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'Circumference'
            long circumferenceStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments circumferenceArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            circumferenceArguments_.put("radius", radius);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, circumferenceArguments_);

            // Evaluate BKM 'Circumference'
            java.math.BigDecimal output_ = evaluate(radius, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'Circumference'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, circumferenceArguments_, output_, (System.currentTimeMillis() - circumferenceStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Circumference' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(java.math.BigDecimal radius, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return numericMultiply(numericMultiply(number("2"), number("3.141592")), radius);
    }
}
