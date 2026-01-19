
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "loop"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "loop",
    label = "Loop",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Loop extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<java.lang.Number>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "loop",
        "Loop",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Zip1 zip1;

    public Loop() {
        this(new Zip1());
    }

    public Loop(Zip1 zip1) {
        this.zip1 = zip1;
    }

    @java.lang.Override()
    public List<java.lang.Number> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("A"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), (input_.get("B") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("B"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Loop'", e);
            return null;
        }
    }

    public List<java.lang.Number> apply(List<java.lang.Number> a4, List<java.lang.Number> b, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'loop'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long loopStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments loopArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            loopArguments_.put("A", a4);
            loopArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, loopArguments_);

            // Iterate and aggregate
            List<java.lang.Number> output_ = evaluate(a4, b, context_);

            // End decision 'loop'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, loopArguments_, output_, (System.currentTimeMillis() - loopStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'loop' evaluation", e);
            return null;
        }
    }

    protected List<java.lang.Number> evaluate(List<java.lang.Number> a4, List<java.lang.Number> b, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<type.Zip1> zip1 = this.zip1.apply(a4, b, context_);

        Body body = new Body();
        return zip1.stream().map(it_iterator -> body.apply(type.It.toIt(it_iterator), context_)).collect(Collectors.toList());
    }
}
