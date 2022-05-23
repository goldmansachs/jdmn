
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "equity36Mo"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "equity36Mo",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Equity36Mo extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "equity36Mo",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class Equity36MoLazyHolder {
        static final Equity36Mo INSTANCE = new Equity36Mo();
    }
    public static Equity36Mo instance() {
        return Equity36MoLazyHolder.INSTANCE;
    }

    private Equity36Mo() {
    }

    @java.lang.Override()
    public java.math.BigDecimal apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("p") != null ? number(input_.get("p")) : null), (input_.get("r") != null ? number(input_.get("r")) : null), (input_.get("n") != null ? number(input_.get("n")) : null), (input_.get("pmt") != null ? number(input_.get("pmt")) : null), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Equity36Mo'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal p, java.math.BigDecimal r, java.math.BigDecimal n, java.math.BigDecimal pmt, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'equity36Mo'
            long equity36MoStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments equity36MoArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            equity36MoArguments_.put("p", p);
            equity36MoArguments_.put("r", r);
            equity36MoArguments_.put("n", n);
            equity36MoArguments_.put("pmt", pmt);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_);

            // Evaluate BKM 'equity36Mo'
            java.math.BigDecimal output_ = lambda.apply(p, r, n, pmt, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'equity36Mo'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, equity36MoArguments_, output_, (System.currentTimeMillis() - equity36MoStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'equity36Mo' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                java.math.BigDecimal p = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                java.math.BigDecimal r = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                java.math.BigDecimal n = 2 < args_.length ? (java.math.BigDecimal) args_[2] : null;
                java.math.BigDecimal pmt = 3 < args_.length ? (java.math.BigDecimal) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 4 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[4] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 5 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[5] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 6 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[6] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 7 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[7] : null;

                return numericSubtract(numericMultiply(p, numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n)), numericDivide(numericMultiply(pmt, numericAdd(numericUnaryMinus(number("1")), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), n))), r));
            }
        };
}
