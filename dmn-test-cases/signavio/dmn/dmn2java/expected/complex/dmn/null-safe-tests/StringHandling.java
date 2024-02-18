
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "stringHandling"})
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
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("numberA") != null ? number(input_.get("numberA")) : null), (input_.get("numberB") != null ? number(input_.get("numberB")) : null), (input_.get("stringList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("stringList"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'StringHandling'", e);
            return null;
        }
    }

    public String apply(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<String> stringList, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'stringHandling'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long stringHandlingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments stringHandlingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            stringHandlingArguments_.put("numberA", numberA);
            stringHandlingArguments_.put("numberB", numberB);
            stringHandlingArguments_.put("stringList", stringList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, stringHandlingArguments_);

            // Evaluate decision 'stringHandling'
            String output_ = evaluate(numberA, numberB, stringList, context_);

            // End decision 'stringHandling'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, stringHandlingArguments_, output_, (System.currentTimeMillis() - stringHandlingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'stringHandling' evaluation", e);
            return null;
        }
    }

    protected String evaluate(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<String> stringList, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return mid(right(left(trim(upper(lower(concat(stringList)))), numberB), numberA), number("0"), numberB);
    }
}
