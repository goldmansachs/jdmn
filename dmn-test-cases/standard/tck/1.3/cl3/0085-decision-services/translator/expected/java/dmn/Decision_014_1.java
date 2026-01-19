
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_014_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_014_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_014_1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<com.gs.dmn.runtime.Context> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_014_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Decision_014_3 decision_014_3;

    public Decision_014_1() {
        this(new Decision_014_3());
    }

    public Decision_014_1(Decision_014_3 decision_014_3) {
        this.decision_014_3 = decision_014_3;
    }

    @java.lang.Override()
    public com.gs.dmn.runtime.Context applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_014_1"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_014_1'", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.Context apply(String inputData_014_1, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_014_1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_014_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_014_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_014_1Arguments_.put("inputData_014_1", inputData_014_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_014_1Arguments_);

            // Evaluate decision 'decision_014_1'
            com.gs.dmn.runtime.Context output_ = lambda.apply(inputData_014_1, context_);

            // End decision 'decision_014_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_014_1Arguments_, output_, (System.currentTimeMillis() - decision_014_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_014_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context> lambda =
        new com.gs.dmn.runtime.LambdaExpression<com.gs.dmn.runtime.Context>() {
            public com.gs.dmn.runtime.Context apply(Object... args_) {
                String inputData_014_1 = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_014_3 = Decision_014_1.this.decision_014_3.apply(context_);

                String decisionService_014 = DecisionService_014.instance().apply("A", "B", context_);
                com.gs.dmn.runtime.Context decision_014_1 = new com.gs.dmn.runtime.Context();
                decision_014_1.put("inputData_014_1",  inputData_014_1);
                decision_014_1.put("decision_014_3",  decision_014_3);
                decision_014_1.put("decisionService_014",  decisionService_014);
                return decision_014_1;
            }
        };
}
