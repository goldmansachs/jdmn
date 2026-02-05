
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "FACT"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "FACT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FACT extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "FACT",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class FACTLazyHolder {
        static final FACT INSTANCE = new FACT();
    }
    public static FACT instance() {
        return FACTLazyHolder.INSTANCE;
    }

    private FACT() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("n") != null ? number(input_.get("n")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FACT'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((FACTInput_)input_).getN(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FACT'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FACTInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FACT'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number n, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'FACT'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fACTStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fACTArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fACTArguments_.put("n", n);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fACTArguments_);

            // Evaluate BKM 'FACT'
            java.lang.Number output_ = lambda.apply(n, context_);

            // End BKM 'FACT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fACTArguments_, output_, (System.currentTimeMillis() - fACTStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'FACT' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.lang.Number n = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return (booleanEqual(numericLessThan(n, number("0")), Boolean.TRUE)) ? null : (booleanEqual(numericEqual(n, number("0")), Boolean.TRUE)) ? number("1") : numericMultiply(n, FACT.instance().apply(numericSubtract(n, number("1")), context_));
            }
        };
}
