
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
public class ListHandling extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Boolean> {
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

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("numberB") != null ? number(input_.get("numberB")) : null), (input_.get("numberList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("numberList"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ListHandling'", e);
            return null;
        }
    }

    public Boolean apply(java.lang.Number numberB, List<java.lang.Number> numberList, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'listHandling'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long listHandlingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments listHandlingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            listHandlingArguments_.put("numberB", numberB);
            listHandlingArguments_.put("numberList", numberList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, listHandlingArguments_);

            // Evaluate decision 'listHandling'
            Boolean output_ = evaluate(numberB, numberList, context_);

            // End decision 'listHandling'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, listHandlingArguments_, output_, (System.currentTimeMillis() - listHandlingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'listHandling' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(java.lang.Number numberB, List<java.lang.Number> numberList, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return booleanOr(booleanOr(booleanOr(numericEqual(count(appendAll(numberList, append(numberList, numberB))), number("5")), containsOnly(numberList, numberList)), notContainsAny(numberList, numberList)), areElementsOf(numberList, numberList));
    }
}
