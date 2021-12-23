
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_011"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_011",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_011 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_011",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_011LazyHolder {
        static final DecisionService_011 INSTANCE = new DecisionService_011();
    }
    public static DecisionService_011 instance() {
        return DecisionService_011LazyHolder.INSTANCE;
    }

    private final Decision_011_2 decision_011_2;

    private DecisionService_011() {
        this(new Decision_011_2());
    }

    private DecisionService_011(Decision_011_2 decision_011_2) {
        this.decision_011_2 = decision_011_2;
    }

    public String apply(String inputData_011_1, String inputData_011_2, String decision_011_3, String decision_011_4, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_011'
            long decisionService_011StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_011Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_011Arguments_.put("inputData_011_1", inputData_011_1);
            decisionService_011Arguments_.put("inputData_011_2", inputData_011_2);
            decisionService_011Arguments_.put("decision_011_3", decision_011_3);
            decisionService_011Arguments_.put("decision_011_4", decision_011_4);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_011Arguments_);

            // Bind input decisions
            cache_.bind("decision_011_3", decision_011_3);
            cache_.bind("decision_011_4", decision_011_4);

            // Evaluate DS 'decisionService_011'
            String output_ = lambda.apply(inputData_011_1, inputData_011_2, decision_011_3, decision_011_4, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_011'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_011Arguments_, output_, (System.currentTimeMillis() - decisionService_011StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_011' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args) {
                String inputData_011_1 = 0 < args.length ? (String) args[0] : null;
                String inputData_011_2 = 1 < args.length ? (String) args[1] : null;
                String decision_011_3 = 2 < args.length ? (String) args[2] : null;
                String decision_011_4 = 3 < args.length ? (String) args[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 4 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[4] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 5 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[5] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 6 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[6] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 7 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[7] : null;

                // Apply child decisions
                String decision_011_2 = DecisionService_011.this.decision_011_2.apply(inputData_011_1, inputData_011_2, annotationSet_, eventListener_, externalExecutor_, cache_);

                return decision_011_2;
            }
    };
}