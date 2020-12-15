
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "addExtraValues"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "addExtraValues",
    label = "AddExtraValues",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AddExtraValues extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "addExtraValues",
        "AddExtraValues",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public AddExtraValues() {
    }

    public List<java.math.BigDecimal> apply(String listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((listOfNumbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listOfNumbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'AddExtraValues'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((listOfNumbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listOfNumbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AddExtraValues'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(listOfNumbers, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'addExtraValues'
            long addExtraValuesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments addExtraValuesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            addExtraValuesArguments_.put("ListOfNumbers", listOfNumbers);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, addExtraValuesArguments_);

            // Evaluate decision 'addExtraValues'
            List<java.math.BigDecimal> output_ = evaluate(listOfNumbers, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'addExtraValues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, addExtraValuesArguments_, output_, (System.currentTimeMillis() - addExtraValuesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'addExtraValues' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(List<java.math.BigDecimal> listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return appendAll(listOfNumbers, asList(number("1"), number("2"), number("3"), number("4"), number("5"), number("6"), number("7"), number("8"), number("9")));
    }
}
