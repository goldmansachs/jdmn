
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "listHandling"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "listHandling",
    label = "listHandling",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ListHandling extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "listHandling",
        "listHandling",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ListHandling() {
    }

    public Boolean apply(String numberB, String numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((numberB != null ? number(numberB) : null), (numberList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberList, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'ListHandling'", e);
            return null;
        }
    }

    public Boolean apply(String numberB, String numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((numberB != null ? number(numberB) : null), (numberList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberList, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ListHandling'", e);
            return null;
        }
    }

    public Boolean apply(java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(numberB, numberList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'listHandling'
            long listHandlingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments listHandlingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            listHandlingArguments_.put("numberB", numberB);
            listHandlingArguments_.put("numberList", numberList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, listHandlingArguments_);

            // Evaluate decision 'listHandling'
            Boolean output_ = evaluate(numberB, numberList, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'listHandling'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, listHandlingArguments_, output_, (System.currentTimeMillis() - listHandlingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'listHandling' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return booleanOr(booleanOr(booleanOr(numericEqual(count(appendAll(numberList, append(numberList, numberB))), number("5")), containsOnly(numberList, numberList)), notContainsAny(numberList, numberList)), areElementsOf(numberList, numberList));
    }
}
