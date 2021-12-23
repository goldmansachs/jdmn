
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "decisionService_013_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionService_013_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionService_013_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionService_013_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION_SERVICE,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class DecisionService_013_1LazyHolder {
        static final DecisionService_013_1 INSTANCE = new DecisionService_013_1();
    }
    public static DecisionService_013_1 instance() {
        return DecisionService_013_1LazyHolder.INSTANCE;
    }

    private final Decision_013_2 decision_013_2;

    private DecisionService_013_1() {
        this(new Decision_013_2());
    }

    private DecisionService_013_1(Decision_013_2 decision_013_2) {
        this.decision_013_2 = decision_013_2;
    }

    public java.math.BigDecimal apply(java.math.BigDecimal input_013_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start DS 'decisionService_013_1'
            long decisionService_013_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionService_013_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionService_013_1Arguments_.put("input_013_1", input_013_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionService_013_1Arguments_);

            // Evaluate DS 'decisionService_013_1'
            java.math.BigDecimal output_ = lambda.apply(input_013_1, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End DS 'decisionService_013_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionService_013_1Arguments_, output_, (System.currentTimeMillis() - decisionService_013_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionService_013_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args) {
                java.math.BigDecimal input_013_1 = 0 < args.length ? (java.math.BigDecimal) args[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[4] : null;

                // Apply child decisions
                java.math.BigDecimal decision_013_2 = DecisionService_013_1.this.decision_013_2.apply(input_013_1, annotationSet_, eventListener_, externalExecutor_, cache_);

                return decision_013_2;
            }
    };
}