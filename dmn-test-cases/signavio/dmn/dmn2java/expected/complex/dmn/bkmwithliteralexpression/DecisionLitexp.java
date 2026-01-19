
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decisionLitexp"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionLitexp",
    label = "decision litexp",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionLitexp extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<type.Zip>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionLitexp",
        "decision litexp",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public DecisionLitexp() {
    }

    @java.lang.Override()
    public List<type.Zip> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("censored") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("censored"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("labels") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("labels"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("numz") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("numz"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), input_.get("redgreenblue1"), input_.get("redgreenblue2"), (input_.get("redgreenbluelist1") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("redgreenbluelist1"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("redgreenbluelist2") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("redgreenbluelist2"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionLitexp'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<String> censored, List<String> labels, List<java.lang.Number> numz, String redgreenblue1, String redgreenblue2, List<String> redgreenbluelist1, List<String> redgreenbluelist2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decisionLitexp'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionLitexpStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionLitexpArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionLitexpArguments_.put("censored", censored);
            decisionLitexpArguments_.put("labels", labels);
            decisionLitexpArguments_.put("numz", numz);
            decisionLitexpArguments_.put("redgreenblue1", redgreenblue1);
            decisionLitexpArguments_.put("redgreenblue2", redgreenblue2);
            decisionLitexpArguments_.put("redgreenbluelist1", redgreenbluelist1);
            decisionLitexpArguments_.put("redgreenbluelist2", redgreenbluelist2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionLitexpArguments_);

            // Evaluate decision 'decisionLitexp'
            List<type.Zip> output_ = evaluate(censored, labels, numz, redgreenblue1, redgreenblue2, redgreenbluelist1, redgreenbluelist2, context_);

            // End decision 'decisionLitexp'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionLitexpArguments_, output_, (System.currentTimeMillis() - decisionLitexpStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionLitexp' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<String> censored, List<String> labels, List<java.lang.Number> numz, String redgreenblue1, String redgreenblue2, List<String> redgreenbluelist1, List<String> redgreenbluelist2, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return LitexpLogic.instance().apply(censored, numz, labels, redgreenblue1, redgreenbluelist1, redgreenblue2, redgreenbluelist2, context_);
    }
}
