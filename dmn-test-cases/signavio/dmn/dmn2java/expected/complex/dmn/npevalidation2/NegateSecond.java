
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "negateSecond"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/5417bfd1893048bc9ca18c51aa11b7f0.xml",
    name = "negateSecond",
    label = "negateSecond",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class NegateSecond extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "negateSecond",
        "negateSecond",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public NegateSecond() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("second") != null ? number(input_.get("second")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'NegateSecond'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number second, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'negateSecond'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long negateSecondStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments negateSecondArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            negateSecondArguments_.put("second", second);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, negateSecondArguments_);

            // Evaluate decision 'negateSecond'
            java.lang.Number output_ = evaluate(second, context_);

            // End decision 'negateSecond'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, negateSecondArguments_, output_, (System.currentTimeMillis() - negateSecondStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'negateSecond' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(java.lang.Number second, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return numericUnaryMinus(second);
    }
}
