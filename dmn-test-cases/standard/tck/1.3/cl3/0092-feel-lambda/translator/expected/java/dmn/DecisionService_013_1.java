
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"ds.ftl", "decisionService_013_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_013_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_013_1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_013_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_013_1LazyHolder {
        static final DecisionService_013_1 INSTANCE = new DecisionService_013_1();
    }
    public static DecisionService_013_1 instance() {
        return DecisionService_013_1LazyHolder.INSTANCE;
    }

    private final Decision_013_2 decision_013_2;

    private DecisionService_013_1() {
        this(new Decision_013_2());
    }

    private DecisionService_013_1(Decision_013_2 decision_013_2) {
        this.decision_013_2 = decision_013_2;
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("input_013_1") != null ? number(input_.get("input_013_1")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionService_013_1'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((DecisionService_013_1Input_)input_).getInput_013_1(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'DecisionService_013_1'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number input_013_1, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start DS 'decisionService_013_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionService_013_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_013_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_013_1Arguments_.put("input_013_1", input_013_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_013_1Arguments_);

            // Evaluate DS 'decisionService_013_1'
            java.lang.Number output_ = lambda.apply(input_013_1, context_);

            // End DS 'decisionService_013_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_013_1Arguments_, output_, (System.currentTimeMillis() - decisionService_013_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_013_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.lang.Number input_013_1 = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                java.lang.Number decision_013_2 = DecisionService_013_1.this.decision_013_2.apply(input_013_1, context_);

                return decision_013_2;
            }
    };
}