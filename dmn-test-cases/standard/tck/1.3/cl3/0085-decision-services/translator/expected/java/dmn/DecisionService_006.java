
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "decisionService_006"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.montera.com.au/spec/DMN/0085-decision-services",
    name = "decisionService_006",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_006 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.montera.com.au/spec/DMN/0085-decision-services",
        "decisionService_006",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_006LazyHolder {
        static final DecisionService_006 INSTANCE = new DecisionService_006();
    }
    public static DecisionService_006 instance() {
        return DecisionService_006LazyHolder.INSTANCE;
    }

    private final Decision_006_2 decision_006_2;

    private DecisionService_006() {
        this(new Decision_006_2());
    }

    private DecisionService_006(Decision_006_2 decision_006_2) {
        this.decision_006_2 = decision_006_2;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("decision_006_3"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_006'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((DecisionService_006Input_)input_).getDecision_006_3(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'DecisionService_006'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new DecisionService_006Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'DecisionService_006'", e);
            return null;
        }
    }

    public String apply(String decision_006_3, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'decisionService_006'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionService_006StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_006Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_006Arguments_.put("decision_006_3", decision_006_3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_006Arguments_);

            // Bind input decisions
            cache_.bind("decision_006_3", decision_006_3);

            // Evaluate DS 'decisionService_006'
            String output_ = lambda.apply(decision_006_3, context_);

            // End DS 'decisionService_006'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_006Arguments_, output_, (System.currentTimeMillis() - decisionService_006StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_006' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String decision_006_3 = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_006_2 = DecisionService_006.this.decision_006_2.apply(context_);

                return decision_006_2;
            }
    };
}