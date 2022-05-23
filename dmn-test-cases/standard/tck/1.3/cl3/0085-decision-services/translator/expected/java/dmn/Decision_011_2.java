
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
public class Decision_011_2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_011_1"), input_.get("inputData_011_2"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_011_2'", e);
            return null;
        }
    }

    public String apply(String inputData_011_1, String inputData_011_2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_011_2'
            long decision_011_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_011_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_011_2Arguments_.put("inputData_011_1", inputData_011_1);
            decision_011_2Arguments_.put("inputData_011_2", inputData_011_2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_011_2Arguments_);

            // Evaluate decision 'decision_011_2'
            String output_ = lambda.apply(inputData_011_1, inputData_011_2, annotationSet_, eventListener_, externalExecutor_, cache_);

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
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                // Apply child decisions
                String decision_011_3 = Decision_011_2.this.decision_011_3.apply(annotationSet_, eventListener_, externalExecutor_, cache_);
                String decision_011_4 = Decision_011_2.this.decision_011_4.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(inputData_011_1, " "), inputData_011_2), " "), decision_011_3), " "), decision_011_4);
            }
        };
}
