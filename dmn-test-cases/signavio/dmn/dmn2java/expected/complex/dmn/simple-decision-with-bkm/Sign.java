
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "sign"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sign",
    label = "Sign",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Sign extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sign",
        "Sign",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Sign() {
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? number(input_.get("A")) : null), (input_.get("B") != null ? number(input_.get("B")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sign'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal a2, java.math.BigDecimal b3, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'sign'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long signStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments signArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            signArguments_.put("A", a2);
            signArguments_.put("B", b3);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, signArguments_);

            // Evaluate decision 'sign'
            java.math.BigDecimal output_ = evaluate(a2, b3, context_);

            // End decision 'sign'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, signArguments_, output_, (System.currentTimeMillis() - signStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sign' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal a2, java.math.BigDecimal b3, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return BKM.instance().apply(b3, a2, context_);
    }
}
