
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "stringHandling"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "stringHandling",
    label = "stringHandling",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class StringHandling extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "stringHandling",
        "stringHandling",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public StringHandling() {
    }

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("numberA"), input_.get("numberB"), input_.get("stringList"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'StringHandling'", e);
            return null;
        }
    }

    public String apply(String numberA, String numberB, String stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((numberA != null ? number(numberA) : null), (numberB != null ? number(numberB) : null), (stringList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(stringList, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'StringHandling'", e);
            return null;
        }
    }

    public String apply(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<String> stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'stringHandling'
            long stringHandlingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments stringHandlingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            stringHandlingArguments_.put("numberA", numberA);
            stringHandlingArguments_.put("numberB", numberB);
            stringHandlingArguments_.put("stringList", stringList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, stringHandlingArguments_);

            // Evaluate decision 'stringHandling'
            String output_ = evaluate(numberA, numberB, stringList, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'stringHandling'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, stringHandlingArguments_, output_, (System.currentTimeMillis() - stringHandlingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'stringHandling' evaluation", e);
            return null;
        }
    }

    protected String evaluate(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<String> stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return mid(right(left(trim(upper(lower(concat(stringList)))), numberB), numberA), number("0"), numberB);
    }
}
