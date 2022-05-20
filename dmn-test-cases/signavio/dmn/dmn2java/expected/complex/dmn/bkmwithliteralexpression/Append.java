
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "append"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "append",
    label = "append",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Append extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "append",
        "append",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Append() {
    }

    public List<String> apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("rgb1"), input_.get("rgb2"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Append'", e);
            return null;
        }
    }

    public List<String> apply(String rgb1, String rgb2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'append'
            long appendStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments appendArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            appendArguments_.put("rgb1", rgb1);
            appendArguments_.put("rgb2", rgb2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, appendArguments_);

            // Evaluate decision 'append'
            List<String> output_ = evaluate(rgb1, rgb2, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'append'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, appendArguments_, output_, (System.currentTimeMillis() - appendStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'append' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(String rgb1, String rgb2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return append(asList(rgb1), rgb2);
    }
}
