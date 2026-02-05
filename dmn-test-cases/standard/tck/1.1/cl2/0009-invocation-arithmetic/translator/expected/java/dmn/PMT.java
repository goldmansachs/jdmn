
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "PMT"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PMT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PMT extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "PMT",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class PMTLazyHolder {
        static final PMT INSTANCE = new PMT();
    }
    public static PMT instance() {
        return PMTLazyHolder.INSTANCE;
    }

    private PMT() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("p") != null ? number(input_.get("p")) : null), (input_.get("r") != null ? number(input_.get("r")) : null), (input_.get("n") != null ? number(input_.get("n")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PMT'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((PMTInput_)input_).getP(), ((PMTInput_)input_).getR(), ((PMTInput_)input_).getN(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'PMT'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new PMTInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'PMT'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number p, java.lang.Number r, java.lang.Number n, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'PMT'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long pMTStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments pMTArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            pMTArguments_.put("p", p);
            pMTArguments_.put("r", r);
            pMTArguments_.put("n", n);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, pMTArguments_);

            // Evaluate BKM 'PMT'
            java.lang.Number output_ = lambda.apply(p, r, n, context_);

            // End BKM 'PMT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, pMTArguments_, output_, (System.currentTimeMillis() - pMTStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'PMT' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.lang.Number p = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                java.lang.Number r = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                java.lang.Number n = 2 < args_.length ? (java.lang.Number) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericDivide(numericDivide(numericMultiply(p, r), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), numericUnaryMinus(n))));
            }
        };
}
