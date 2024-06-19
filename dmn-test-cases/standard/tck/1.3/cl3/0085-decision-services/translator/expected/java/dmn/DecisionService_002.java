
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "decisionService_002"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_002",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_002 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_002",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_002LazyHolder {
        static final DecisionService_002 INSTANCE = new DecisionService_002();
    }
    public static DecisionService_002 instance() {
        return DecisionService_002LazyHolder.INSTANCE;
    }

    private final Decision_002 decision_002;

    private DecisionService_002() {
        this(new Decision_002());
    }

    private DecisionService_002(Decision_002 decision_002) {
        this.decision_002 = decision_002;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("decision_002_input"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_002'", e);
            return null;
        }
    }

    public String apply(String decision_002_input, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'decisionService_002'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionService_002StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_002Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_002Arguments_.put("decision_002_input", decision_002_input);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_002Arguments_);

            // Bind input decisions
            cache_.bind("decision_002_input", decision_002_input);

            // Evaluate DS 'decisionService_002'
            String output_ = lambda.apply(decision_002_input, context_);

            // End DS 'decisionService_002'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_002Arguments_, output_, (System.currentTimeMillis() - decisionService_002StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_002' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String decision_002_input = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_002 = DecisionService_002.this.decision_002.apply(context_);

                return decision_002;
            }
    };
}