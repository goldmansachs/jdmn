
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "decision_012_2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_012_2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_012_2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_012_2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Decision_012_3 decision_012_3;
    private final Decision_012_4 decision_012_4;

    public Decision_012_2() {
        this(new Decision_012_3(), new Decision_012_4());
    }

    public Decision_012_2(Decision_012_3 decision_012_3, Decision_012_4 decision_012_4) {
        this.decision_012_3 = decision_012_3;
        this.decision_012_4 = decision_012_4;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_012_1"), input_.get("inputData_012_2"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_012_2'", e);
            return null;
        }
    }

    public String apply(String inputData_012_1, String inputData_012_2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_012_2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_012_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_012_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_012_2Arguments_.put("inputData_012_1", inputData_012_1);
            decision_012_2Arguments_.put("inputData_012_2", inputData_012_2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_012_2Arguments_);

            // Evaluate decision 'decision_012_2'
            String output_ = lambda.apply(inputData_012_1, inputData_012_2, context_);

            // End decision 'decision_012_2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_012_2Arguments_, output_, (System.currentTimeMillis() - decision_012_2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_012_2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_012_1 = 0 < args_.length ? (String) args_[0] : null;
                String inputData_012_2 = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_012_3 = Decision_012_2.this.decision_012_3.apply(context_);
                String decision_012_4 = Decision_012_2.this.decision_012_4.apply(context_);

                return stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(inputData_012_1, " "), inputData_012_2), " "), decision_012_3), " "), decision_012_4);
            }
        };
}
