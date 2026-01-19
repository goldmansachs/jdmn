
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "mid"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "mid",
    label = "mid",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Mid extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "mid",
        "mid",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Zip zip;

    public Mid() {
        this(new Zip());
    }

    public Mid(Zip zip) {
        this.zip = zip;
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("inputA") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("inputA"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("inputB") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("inputB"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Mid'", e);
            return null;
        }
    }

    public List<String> apply(List<String> inputA, List<java.lang.Number> inputB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'mid'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long midStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments midArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            midArguments_.put("inputA", inputA);
            midArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, midArguments_);

            // Iterate and aggregate
            List<String> output_ = evaluate(inputA, inputB, context_);

            // End decision 'mid'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, midArguments_, output_, (System.currentTimeMillis() - midStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'mid' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(List<String> inputA, List<java.lang.Number> inputB, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<type.Zip> zip = this.zip.apply(inputA, inputB, context_);

        DoSomething doSomething = new DoSomething();
        return zip.stream().map(zip4_iterator -> doSomething.apply(type.Zip3.toZip3(zip4_iterator), context_)).collect(Collectors.toList());
    }
}
