
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "monthlyPayment"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "monthlyPayment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class MonthlyPayment extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "monthlyPayment",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class MonthlyPaymentLazyHolder {
        static final MonthlyPayment INSTANCE = new MonthlyPayment();
    }
    public static MonthlyPayment instance() {
        return MonthlyPaymentLazyHolder.INSTANCE;
    }

    private MonthlyPayment() {
    }


    public java.math.BigDecimal apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("p") != null ? number(input_.get("p")) : null), (input_.get("r") != null ? number(input_.get("r")) : null), (input_.get("n") != null ? number(input_.get("n")) : null), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'MonthlyPayment'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal p, java.math.BigDecimal r, java.math.BigDecimal n, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'monthlyPayment'
            long monthlyPaymentStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments monthlyPaymentArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            monthlyPaymentArguments_.put("p", p);
            monthlyPaymentArguments_.put("r", r);
            monthlyPaymentArguments_.put("n", n);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_);

            // Evaluate BKM 'monthlyPayment'
            java.math.BigDecimal output_ = lambda.apply(p, r, n, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'monthlyPayment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_, output_, (System.currentTimeMillis() - monthlyPaymentStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'monthlyPayment' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                java.math.BigDecimal p = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                java.math.BigDecimal r = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                java.math.BigDecimal n = 2 < args_.length ? (java.math.BigDecimal) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 3 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[3] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 4 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[4] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 5 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[5] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 6 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[6] : null;

                return numericDivide(numericDivide(numericMultiply(p, r), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(r, number("12"))), numericUnaryMinus(n))));
            }
        };
}
