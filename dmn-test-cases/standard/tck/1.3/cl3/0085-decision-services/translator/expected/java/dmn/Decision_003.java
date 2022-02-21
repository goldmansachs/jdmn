
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_003"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_003",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_003 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_003",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Decision_003_input_1 decision_003_input_1;
    private final Decision_003_input_2 decision_003_input_2;

    public Decision_003() {
        this(new Decision_003_input_1(), new Decision_003_input_2());
    }

    public Decision_003(Decision_003_input_1 decision_003_input_1, Decision_003_input_2 decision_003_input_2) {
        this.decision_003_input_1 = decision_003_input_1;
        this.decision_003_input_2 = decision_003_input_2;
    }

    public String apply(String inputData_003, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputData_003, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String inputData_003, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_003'
            long decision_003StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_003Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_003Arguments_.put("inputData_003", inputData_003);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_003Arguments_);

            // Evaluate decision 'decision_003'
            String output_ = lambda.apply(inputData_003, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_003'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_003Arguments_, output_, (System.currentTimeMillis() - decision_003StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_003' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_003 = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                // Apply child decisions
                String decision_003_input_1 = Decision_003.this.decision_003_input_1.apply(annotationSet_, eventListener_, externalExecutor_, cache_);
                String decision_003_input_2 = Decision_003.this.decision_003_input_2.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd(stringAdd(stringAdd(stringAdd(stringAdd("A ", decision_003_input_1), " "), decision_003_input_2), " "), inputData_003);
            }
        };
}
