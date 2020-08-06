
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

    public static final InstallmentCalculation INSTANCE = new InstallmentCalculation();

    private InstallmentCalculation() {
    }

    public static java.math.BigDecimal InstallmentCalculation(String productType, java.math.BigDecimal rate, java.math.BigDecimal term, java.math.BigDecimal amount, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return INSTANCE.apply(productType, rate, term, amount, annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    private java.math.BigDecimal apply(String productType, java.math.BigDecimal rate, java.math.BigDecimal term, java.math.BigDecimal amount, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
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
            java.math.BigDecimal output_ = evaluate(productType, rate, term, amount, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'InstallmentCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, installmentCalculationArguments_, output_, (System.currentTimeMillis() - installmentCalculationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'InstallmentCalculation' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(String productType, java.math.BigDecimal rate, java.math.BigDecimal term, java.math.BigDecimal amount, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        java.math.BigDecimal monthlyFee = (booleanEqual(stringEqual(productType, "STANDARD LOAN"), Boolean.TRUE)) ? number("20.00") : (booleanEqual(stringEqual(productType, "SPECIAL LOAN"), Boolean.TRUE)) ? number("25.00") : null;
        java.math.BigDecimal monthlyRepayment = numericDivide(numericDivide(numericMultiply(amount, rate), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(rate, number("12"))), numericUnaryMinus(term))));
        return numericAdd(monthlyRepayment, monthlyFee);
    }
}
