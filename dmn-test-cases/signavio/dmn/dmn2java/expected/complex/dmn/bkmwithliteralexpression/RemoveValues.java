
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "removeValues"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml",
    name = "removeValues",
    label = "RemoveValues",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class RemoveValues extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<java.lang.Number>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/ec84b81482a64a2fbfcec8b1c831507a.xml",
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

    @java.lang.Override()
    public List<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ListOfNumbers") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ListOfNumbers"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'RemoveValues'", e);
            return null;
        }
    }

    public List<java.lang.Number> apply(List<java.lang.Number> listOfNumbers, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'removeValues'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long removeValuesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments removeValuesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            removeValuesArguments_.put("ListOfNumbers", listOfNumbers);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, removeValuesArguments_);

            // Evaluate decision 'removeValues'
            List<java.lang.Number> output_ = evaluate(listOfNumbers, context_);

            // End decision 'removeValues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, removeValuesArguments_, output_, (System.currentTimeMillis() - removeValuesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'removeValues' evaluation", e);
            return null;
        }
    }

    protected List<java.lang.Number> evaluate(List<java.lang.Number> listOfNumbers, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<java.lang.Number> addExtraValues = this.addExtraValues.apply(listOfNumbers, context_);

        return removeAll(addExtraValues, asList(number("2"), number("4"), number("6"), number("8")));
    }
}
