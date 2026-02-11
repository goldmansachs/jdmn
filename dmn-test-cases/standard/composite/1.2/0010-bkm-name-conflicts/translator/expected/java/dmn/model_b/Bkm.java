package model_b;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "bkm"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/definitions/model-b",
    name = "bkm",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Bkm extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_b",
        "bkm",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class BkmLazyHolder {
        static final Bkm INSTANCE = new Bkm();
    }
    public static Bkm instance() {
        return BkmLazyHolder.INSTANCE;
    }

    private Bkm() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("x"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Bkm'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((BkmInput_)input_).getX(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Bkm'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new BkmInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Bkm'", e);
            return null;
        }
    }

    public String apply(String x, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'bkm'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long bkmStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments bkmArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            bkmArguments_.put("x", x);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bkmArguments_);

            // Evaluate BKM 'bkm'
            String output_ = lambda.apply(x, context_);

            // End BKM 'bkm'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bkmArguments_, output_, (System.currentTimeMillis() - bkmStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'bkm' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String x = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return x;
            }
        };
}
