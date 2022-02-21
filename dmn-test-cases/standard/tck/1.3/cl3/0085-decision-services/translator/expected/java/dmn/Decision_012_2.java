
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_012_2"})
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

    public String apply(String inputData_012_1, String inputData_012_2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputData_012_1, inputData_012_2, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String inputData_012_1, String inputData_012_2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_012_2'
            long decision_012_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_012_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_012_2Arguments_.put("inputData_012_1", inputData_012_1);
            decision_012_2Arguments_.put("inputData_012_2", inputData_012_2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_012_2Arguments_);

            // Evaluate decision 'decision_012_2'
            String output_ = lambda.apply(inputData_012_1, inputData_012_2, annotationSet_, eventListener_, externalExecutor_, cache_);

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
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 2 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[2] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 3 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[3] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 4 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[4] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 5 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[5] : null;

                // Apply child decisions
                String decision_012_3 = Decision_012_2.this.decision_012_3.apply(annotationSet_, eventListener_, externalExecutor_, cache_);
                String decision_012_4 = Decision_012_2.this.decision_012_4.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

                return stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(stringAdd(inputData_012_1, " "), inputData_012_2), " "), decision_012_3), " "), decision_012_4);
            }
        };
}
