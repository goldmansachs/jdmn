
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "arithmetic"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "arithmetic",
    label = "arithmetic",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Arithmetic extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "arithmetic",
        "arithmetic",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Arithmetic() {
    }

    public java.math.BigDecimal apply(String numberA, String numberB, String numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((numberA != null ? number(numberA) : null), (numberB != null ? number(numberB) : null), (numberList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberList, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Arithmetic'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String numberA, String numberB, String numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((numberA != null ? number(numberA) : null), (numberB != null ? number(numberB) : null), (numberList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numberList, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Arithmetic'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(numberA, numberB, numberList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'arithmetic'
            long arithmeticStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arithmeticArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arithmeticArguments_.put("numberA", numberA);
            arithmeticArguments_.put("numberB", numberB);
            arithmeticArguments_.put("numberList", numberList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arithmeticArguments_);

            // Evaluate decision 'arithmetic'
            java.math.BigDecimal output_ = evaluate(numberA, numberB, numberList, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'arithmetic'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arithmeticArguments_, output_, (System.currentTimeMillis() - arithmeticStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'arithmetic' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal numberA, java.math.BigDecimal numberB, List<java.math.BigDecimal> numberList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericAdd(numericDivide(numericMultiply(numericSubtract(numericAdd(numberA, number("2")), number("2")), number("2")), numericAdd(numberA, number("1"))), abs(numberB)), count(numberList)), round(numberB, number("2"))), roundDown(numberB, number("2"))), roundUp(numberB, number("2"))), ceiling(numberB)), floor(numberB)), integer(numberB)), modulo(numberB, numberA)), modulo(numberB, number("2"))), power(numberB, number("2"))), percent(numberB)), product(numberList)), sum(numberList));
    }
}
