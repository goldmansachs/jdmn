
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
public class AddExtraValues extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
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

    @java.lang.Override()
    public List<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ListOfNumbers") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ListOfNumbers"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AddExtraValues'", e);
            return null;
        }
    }

    public List<java.lang.Number> apply(List<java.lang.Number> listOfNumbers, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'addExtraValues'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long addExtraValuesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments addExtraValuesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            addExtraValuesArguments_.put("ListOfNumbers", listOfNumbers);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, addExtraValuesArguments_);

            // Evaluate decision 'addExtraValues'
            List<java.lang.Number> output_ = evaluate(listOfNumbers, context_);

            // End decision 'addExtraValues'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, addExtraValuesArguments_, output_, (System.currentTimeMillis() - addExtraValuesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'addExtraValues' evaluation", e);
            return null;
        }
    }

    protected List<java.lang.Number> evaluate(List<java.lang.Number> listOfNumbers, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return appendAll(listOfNumbers, asList(number("1"), number("2"), number("3"), number("4"), number("5"), number("6"), number("7"), number("8"), number("9")));
    }
}
