
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "InstallmentCalculation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "InstallmentCalculation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class InstallmentCalculation extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "InstallmentCalculation",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class InstallmentCalculationLazyHolder {
        static final InstallmentCalculation INSTANCE = new InstallmentCalculation();
    }
    public static InstallmentCalculation instance() {
        return InstallmentCalculationLazyHolder.INSTANCE;
    }

    private InstallmentCalculation() {
    }

    public java.math.BigDecimal apply(String productType, java.math.BigDecimal rate, java.math.BigDecimal term, java.math.BigDecimal amount, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'InstallmentCalculation'
            long installmentCalculationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments installmentCalculationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            installmentCalculationArguments_.put("ProductType", productType);
            installmentCalculationArguments_.put("Rate", rate);
            installmentCalculationArguments_.put("Term", term);
            installmentCalculationArguments_.put("Amount", amount);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, installmentCalculationArguments_);

            // Evaluate BKM 'InstallmentCalculation'
            java.math.BigDecimal output_ = lambda.apply(productType, rate, term, amount, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'InstallmentCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, installmentCalculationArguments_, output_, (System.currentTimeMillis() - installmentCalculationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'InstallmentCalculation' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args_) {
                String productType = 0 < args_.length ? (String) args_[0] : null;
                java.math.BigDecimal rate = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                java.math.BigDecimal term = 2 < args_.length ? (java.math.BigDecimal) args_[2] : null;
                java.math.BigDecimal amount = 3 < args_.length ? (java.math.BigDecimal) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 4 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[4] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 5 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[5] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 6 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[6] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 7 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[7] : null;

                java.math.BigDecimal monthlyFee = (booleanEqual(stringEqual(productType, "STANDARD LOAN"), Boolean.TRUE)) ? number("20.00") : (booleanEqual(stringEqual(productType, "SPECIAL LOAN"), Boolean.TRUE)) ? number("25.00") : null;
                java.math.BigDecimal monthlyRepayment = numericDivide(numericDivide(numericMultiply(amount, rate), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(rate, number("12"))), numericUnaryMinus(term))));
                return numericAdd(monthlyRepayment, monthlyFee);
            }
        };
}
