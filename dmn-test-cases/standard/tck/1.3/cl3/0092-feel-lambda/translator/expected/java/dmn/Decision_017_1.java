
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_017_1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_017_1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_017_1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_017_1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_017_1() {
    }

    public List<String> apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("input_017_1"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_017_1'", e);
            return null;
        }
    }

    public List<String> apply(String input_017_1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_017_1'
            long decision_017_1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_017_1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_017_1Arguments_.put("input_017_1", input_017_1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_017_1Arguments_);

            // Evaluate decision 'decision_017_1'
            List<String> output_ = lambda.apply(input_017_1, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision_017_1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_017_1Arguments_, output_, (System.currentTimeMillis() - decision_017_1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_017_1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<String>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<String>>() {
            public List<String> apply(Object... args_) {
                String input_017_1 = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                return Bkm_017_1.instance().apply(new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {String a = (String)args_[0]; String b = (String)args_[1];return (booleanEqual(stringEqual(a, input_017_1), Boolean.TRUE)) ? Boolean.TRUE : Boolean.FALSE;}}, annotationSet_, eventListener_, externalExecutor_, cache_);
            }
        };
}
