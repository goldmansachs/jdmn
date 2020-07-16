
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "removeValues"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "removeValues",
    label = "RemoveValues",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class RemoveValues extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "removeValues",
        "RemoveValues",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final AddExtraValues addExtraValues;

    public RemoveValues() {
        this(new AddExtraValues());
    }

    public RemoveValues(AddExtraValues addExtraValues) {
        this.addExtraValues = addExtraValues;
    }

    public List<java.math.BigDecimal> apply(String listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((listOfNumbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listOfNumbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'RemoveValues'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(String listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((listOfNumbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listOfNumbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RemoveValues'", e);
            return null;
        }
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(listOfNumbers, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<java.math.BigDecimal> apply(List<java.math.BigDecimal> listOfNumbers, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'removeValues'
            long removeValuesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeValuesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeValuesArguments_.put("ListOfNumbers", listOfNumbers);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeValuesArguments_);

            // Apply child decisions
            List<java.math.BigDecimal> addExtraValues = this.addExtraValues.apply(listOfNumbers, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'removeValues'
            List<java.math.BigDecimal> output_ = evaluate(addExtraValues, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'removeValues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeValuesArguments_, output_, (System.currentTimeMillis() - removeValuesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'removeValues' evaluation", e);
            return null;
        }
    }

    protected List<java.math.BigDecimal> evaluate(List<java.math.BigDecimal> addExtraValues, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return removeAll(addExtraValues, asList(number("2"), number("4"), number("6"), number("8")));
    }
}
