
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "decision_014_2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_014_2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_014_2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_014_2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Decision_014_3 decision_014_3;

    public Decision_014_2() {
        this(new Decision_014_3());
    }

    public Decision_014_2(Decision_014_3 decision_014_3) {
        this.decision_014_3 = decision_014_3;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_014_1"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_014_2'", e);
            return null;
        }
    }

    public String apply(String inputData_014_1, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_014_2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_014_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_014_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_014_2Arguments_.put("inputData_014_1", inputData_014_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_014_2Arguments_);

            // Evaluate decision 'decision_014_2'
            String output_ = lambda.apply(inputData_014_1, context_);

            // End decision 'decision_014_2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_014_2Arguments_, output_, (System.currentTimeMillis() - decision_014_2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_014_2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_014_1 = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_014_3 = Decision_014_2.this.decision_014_3.apply(context_);

                return stringAdd(stringAdd(inputData_014_1, " "), decision_014_3);
            }
        };
}
