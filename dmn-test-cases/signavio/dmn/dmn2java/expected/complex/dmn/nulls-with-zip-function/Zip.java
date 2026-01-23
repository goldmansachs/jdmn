
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "zip"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "zip",
    label = "zip",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Zip extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<type.Zip>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "zip",
        "zip",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Zip() {
    }

    @java.lang.Override()
    public List<type.Zip> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("inputA") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("inputA"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("inputB") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("inputB"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<String> inputA, List<java.lang.Number> inputB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'zip'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long zipStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments zipArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            zipArguments_.put("inputA", inputA);
            zipArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, zipArguments_);

            // Evaluate decision 'zip'
            List<type.Zip> output_ = evaluate(inputA, inputB, context_);

            // End decision 'zip'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, zipArguments_, output_, (System.currentTimeMillis() - zipStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'zip' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<String> inputA, List<java.lang.Number> inputB, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return zip(asList("inputA", "inputB"), asList(inputA, inputB)).stream().map(x_ -> type.Zip.toZip(x_)).collect(java.util.stream.Collectors.toList());
    }
}
