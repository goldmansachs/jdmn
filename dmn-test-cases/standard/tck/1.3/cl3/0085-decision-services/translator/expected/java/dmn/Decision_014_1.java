
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
public class Decision_014_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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

    public com.gs.dmn.runtime.Context apply(String inputData_014_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputData_014_1, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public com.gs.dmn.runtime.Context apply(String inputData_014_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_014_1'
            long decision_014_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_014_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_014_1Arguments_.put("inputData_014_1", inputData_014_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_014_1Arguments_);

            // Evaluate decision 'decision_014_1'
            com.gs.dmn.runtime.Context output_ = evaluate(inputData_014_1, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_014_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_014_1Arguments_, output_, (System.currentTimeMillis() - decision_014_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_014_1' evaluation", e);
            return null;
        }
    }

    protected com.gs.dmn.runtime.Context evaluate(String inputData_014_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply child decisions
        String decision_014_3 = Decision_014_1.this.decision_014_3.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

        String decisionService_014 = DecisionService_014.instance().apply("A", "B", annotationSet_, eventListener_, externalExecutor_, cache_);
        com.gs.dmn.runtime.Context decision_014_1 = new com.gs.dmn.runtime.Context();
        decision_014_1.put("inputData_014_1",  inputData_014_1);
        decision_014_1.put("decision_014_3",  decision_014_3);
        decision_014_1.put("decisionService_014",  decisionService_014);
        return decision_014_1;
    }
}
