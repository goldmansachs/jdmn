
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"bkm.ftl", "PMT"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "PMT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PMT extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("p") != null ? number(input_.get("p")) : null), (input_.get("r") != null ? number(input_.get("r")) : null), (input_.get("n") != null ? number(input_.get("n")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PMT'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal p, java.math.BigDecimal r, java.math.BigDecimal n, com.gs.dmn.runtime.ExecutionContext context_) {
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
            java.math.BigDecimal output_ = lambda.apply(p, r, n, context_);

            // End BKM 'PMT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, pMTArguments_, output_, (System.currentTimeMillis() - pMTStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'PMT' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                java.math.BigDecimal p = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                java.math.BigDecimal r = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                java.math.BigDecimal n = 2 < args_.length ? (java.math.BigDecimal) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericDivide(numericDivide(numericMultiply(p, r), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), numericUnaryMinus(n))));
            }
        };
}
