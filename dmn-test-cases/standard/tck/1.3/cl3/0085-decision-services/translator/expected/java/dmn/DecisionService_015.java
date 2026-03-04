
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "decisionService_015"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.montera.com.au/spec/DMN/0085-decision-services",
    name = "decisionService_015",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_015 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<com.gs.dmn.runtime.Context> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.montera.com.au/spec/DMN/0085-decision-services",
        "decisionService_015",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_015LazyHolder {
        static final DecisionService_015 INSTANCE = new DecisionService_015();
    }
    public static DecisionService_015 instance() {
        return DecisionService_015LazyHolder.INSTANCE;
    }

    private final Decision_015_1 decision_015_1;
    private final Decision_015_2 decision_015_2;

    private DecisionService_015() {
        this(new Decision_015_1(), new Decision_015_2());
    }

    private DecisionService_015(Decision_015_1 decision_015_1, Decision_015_2 decision_015_2) {
        this.decision_015_1 = decision_015_1;
        this.decision_015_2 = decision_015_2;
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.Context applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_015'", e);
            return null;
        }
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.Context applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'DecisionService_015'", e);
            return null;
        }
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.Context applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new DecisionService_015Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'DecisionService_015'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.Context apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'decisionService_015'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionService_015StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_015Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_015Arguments_);

            // Evaluate DS 'decisionService_015'
            com.gs.dmn.runtime.Context output_ = lambda.apply(context_);

            // End DS 'decisionService_015'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_015Arguments_, output_, (System.currentTimeMillis() - decisionService_015StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_015' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context>() {
            public com.gs.dmn.runtime.Context apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_015_1 = DecisionService_015.this.decision_015_1.apply(context_);
                String decision_015_2 = DecisionService_015.this.decision_015_2.apply(context_);

                com.gs.dmn.runtime.Context output_ = new com.gs.dmn.runtime.Context();
                output_.add("decision_015_1",  decision_015_1);
                output_.add("decision_015_2",  decision_015_2);
                return output_;
            }
    };
}