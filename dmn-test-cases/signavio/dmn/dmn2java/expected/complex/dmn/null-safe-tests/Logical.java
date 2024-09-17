
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "logical"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "logical",
    label = "logical ",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Logical extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "logical",
        "logical ",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Logical() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("booleanA") != null ? Boolean.valueOf(input_.get("booleanA")) : null), (input_.get("booleanB") != null ? Boolean.valueOf(input_.get("booleanB")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Logical'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanA, Boolean booleanB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'logical'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long logicalStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments logicalArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            logicalArguments_.put("booleanA", booleanA);
            logicalArguments_.put("booleanB", booleanB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, logicalArguments_);

            // Evaluate decision 'logical'
            Boolean output_ = evaluate(booleanA, booleanB, context_);

            // End decision 'logical'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, logicalArguments_, output_, (System.currentTimeMillis() - logicalStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'logical' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanA, Boolean booleanB, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return booleanNot(booleanOr(booleanAnd(booleanA, booleanB), booleanA));
    }
}
