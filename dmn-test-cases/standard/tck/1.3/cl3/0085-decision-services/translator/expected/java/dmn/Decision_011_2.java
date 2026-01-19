
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_011_2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_011_2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_011_2 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_011_2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Decision_011_3 decision_011_3;
    private final Decision_011_4 decision_011_4;

    public Decision_011_2() {
        this(new Decision_011_3(), new Decision_011_4());
    }

    public Decision_011_2(Decision_011_3 decision_011_3, Decision_011_4 decision_011_4) {
        this.decision_011_3 = decision_011_3;
        this.decision_011_4 = decision_011_4;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_011_1"), input_.get("inputData_011_2"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_011_2'", e);
            return null;
        }
    }

    public String apply(String inputData_011_1, String inputData_011_2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_011_2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_011_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_011_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_011_2Arguments_.put("inputData_011_1", inputData_011_1);
            decision_011_2Arguments_.put("inputData_011_2", inputData_011_2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_011_2Arguments_);

            // Evaluate decision 'decision_011_2'
            String output_ = lambda.apply(inputData_011_1, inputData_011_2, context_);

            // End decision 'decision_011_2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_011_2Arguments_, output_, (System.currentTimeMillis() - decision_011_2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_011_2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_011_1 = 0 < args_.length ? (String) args_[0] : null;
                String inputData_011_2 = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_011_3 = Decision_011_2.this.decision_011_3.apply(context_);
                String decision_011_4 = Decision_011_2.this.decision_011_4.apply(context_);

                return stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(inputData_011_1, " "), inputData_011_2), " "), decision_011_3), " "), decision_011_4);
            }
        };
}
