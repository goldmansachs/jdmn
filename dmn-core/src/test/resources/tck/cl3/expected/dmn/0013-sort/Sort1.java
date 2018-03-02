
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "sort1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Sort1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sort1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Sort1() {
    }

    public List<java.math.BigDecimal> apply(String listA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((listA != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listA, java.math.BigDecimal[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort1'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String listA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((listA != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listA, java.math.BigDecimal[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort1'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> listA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(listA, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> listA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'sort1'
            long sort1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sort1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sort1Arguments_.put("listA", listA);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_);

            // Evaluate decision 'sort1'
            List<java.math.BigDecimal> output_ = evaluate(listA, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'sort1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort1Arguments_, output_, (System.currentTimeMillis() - sort1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sort1' evaluation", e);
            return null;
        }
    }

    private List<java.math.BigDecimal> evaluate(List<java.math.BigDecimal> listA, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return sort(listA, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {java.math.BigDecimal x = (java.math.BigDecimal)args[0]; java.math.BigDecimal y = (java.math.BigDecimal)args[1];return numericGreaterThan(x, y);}});
    }
}
