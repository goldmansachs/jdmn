
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "keepInputallFalse"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml",
    name = "keepInputallFalse",
    label = "keepInput_allFalse",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class KeepInputallFalse extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<Boolean> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml",
        "keepInputallFalse",
        "keepInput_allFalse",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public KeepInputallFalse() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("booleanAllFalse") != null ? Boolean.valueOf(input_.get("booleanAllFalse")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'KeepInputallFalse'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanAllFalse_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'keepInputallFalse'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long keepInputallFalseStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments keepInputallFalseArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            keepInputallFalseArguments_.put("booleanAllFalse", booleanAllFalse_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, keepInputallFalseArguments_);

            // Evaluate decision 'keepInputallFalse'
            Boolean output_ = evaluate(booleanAllFalse_iterator, context_);

            // End decision 'keepInputallFalse'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, keepInputallFalseArguments_, output_, (System.currentTimeMillis() - keepInputallFalseStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'keepInputallFalse' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanAllFalse_iterator, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return booleanAllFalse_iterator;
    }
}
