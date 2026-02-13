
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "arithmetic"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml",
    name = "arithmetic",
    label = "arithmetic",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Arithmetic extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml",
        "arithmetic",
        "arithmetic",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Arithmetic() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("numberA") != null ? number(input_.get("numberA")) : null), (input_.get("numberB") != null ? number(input_.get("numberB")) : null), (input_.get("numberList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("numberList"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Arithmetic'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number numberA, java.lang.Number numberB, List<java.lang.Number> numberList, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'arithmetic'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long arithmeticStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arithmeticArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arithmeticArguments_.put("numberA", numberA);
            arithmeticArguments_.put("numberB", numberB);
            arithmeticArguments_.put("numberList", numberList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arithmeticArguments_);

            // Evaluate decision 'arithmetic'
            java.lang.Number output_ = evaluate(numberA, numberB, numberList, context_);

            // End decision 'arithmetic'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arithmeticArguments_, output_, (System.currentTimeMillis() - arithmeticStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'arithmetic' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(java.lang.Number numberA, java.lang.Number numberB, List<java.lang.Number> numberList, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericDivide(numericMultiply(numericSubtract(numericAdd(numberA, number("2")), number("2")), number("2")), numericAdd(numberA, number("1"))), abs(numberB)), count(numberList)), round(numberB, number("2"))), roundDown(numberB, number("2"))), roundUp(numberB, number("2"))), ceiling(numberB)), floor(numberB)), integer(numberB)), modulo(numberB, numberA)), modulo(numberB, number("2"))), power(numberB, number("2"))), percent(numberB)), product(numberList)), sum(numberList));
    }
}
