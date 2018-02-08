
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "gtTen"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "gtTen",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class GtTen extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "gtTen",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public static GtTen INSTANCE = new GtTen();

    private GtTen() {
    }

    public static Boolean gtTen(java.math.BigDecimal theNumber, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return INSTANCE.apply(theNumber, annotationSet_, eventListener_, externalExecutor_);
    }

    private Boolean apply(java.math.BigDecimal theNumber, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // BKM start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("theNumber", theNumber);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            Boolean output_ = evaluate(theNumber, annotationSet_, eventListener_, externalExecutor_);

            // BKM end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'gtTen' evaluation", e);
            return null;
        }
    }

    private Boolean evaluate(java.math.BigDecimal theNumber, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return numericGreaterThan(theNumber, number("10"));
    }
}
