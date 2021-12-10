
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_013_2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_013_2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_013_2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_013_2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Decision_013_3 decision_013_3;

    public Decision_013_2() {
        this(new Decision_013_3());
    }

    public Decision_013_2(Decision_013_3 decision_013_3) {
        this.decision_013_3 = decision_013_3;
    }

    public String apply(String inputData_013_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputData_013_1, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(String inputData_013_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_013_2'
            long decision_013_2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_013_2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_013_2Arguments_.put("inputData_013_1", inputData_013_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_013_2Arguments_);

            // Apply child decisions
            String decision_013_3 = this.decision_013_3.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'decision_013_2'
            String output_ = evaluate(decision_013_3, inputData_013_1, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_013_2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_013_2Arguments_, output_, (System.currentTimeMillis() - decision_013_2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_013_2' evaluation", e);
            return null;
        }
    }

    protected String evaluate(String decision_013_3, String inputData_013_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return stringAdd(stringAdd(inputData_013_1, " "), decision_013_3);
    }
}
