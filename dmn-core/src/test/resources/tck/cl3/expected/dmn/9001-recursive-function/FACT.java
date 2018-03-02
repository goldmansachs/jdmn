
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "FACT"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "FACT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FACT extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "FACT",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static final FACT INSTANCE = new FACT();

    private FACT() {
    }

    public static java.math.BigDecimal FACT(java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(n, annotationSet_, eventListener_, externalExecutor_);
    }

    private java.math.BigDecimal apply(java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start BKM 'FACT'
            long fACTStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fACTArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fACTArguments_.put("n", n);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fACTArguments_);

            // Evaluate BKM 'FACT'
            java.math.BigDecimal output_ = evaluate(n, annotationSet_, eventListener_, externalExecutor_);

            // End BKM 'FACT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fACTArguments_, output_, (System.currentTimeMillis() - fACTStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'FACT' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return (booleanEqual(numericLessThan(n, number("0")), Boolean.TRUE)) ? null : (booleanEqual(numericEqual(n, number("0")), Boolean.TRUE)) ? number("1") : numericMultiply(n, FACT(numericSubtract(n, number("1")), annotationSet_, eventListener_, externalExecutor_));
    }
}
