
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-bkm.ftl", "bKM"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/2521256910f54d44b0a90fa88a1aa917.xml",
    name = "bKM",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class BKM extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "bKM",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class BKMLazyHolder {
        static final BKM INSTANCE = new BKM();
    }
    public static BKM instance() {
        return BKMLazyHolder.INSTANCE;
    }

    private BKM() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? number(input_.get("A")) : null), (input_.get("B") != null ? number(input_.get("B")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'BKM'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number a, java.lang.Number b, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bKM'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bKMStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bKMArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bKMArguments_.put("A", a);
            bKMArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bKMArguments_);

            // Evaluate BKM 'bKM'
            java.lang.Number output_ = evaluate(a, b, context_);

            // End BKM 'bKM'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bKMArguments_, output_, (System.currentTimeMillis() - bKMStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bKM' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(java.lang.Number a, java.lang.Number b, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return new SUM().apply(a, b, context_);
    }
}
