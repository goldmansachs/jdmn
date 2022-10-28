
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "AffordabilityCalculation"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "AffordabilityCalculation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AffordabilityCalculation extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "AffordabilityCalculation",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class AffordabilityCalculationLazyHolder {
        static final AffordabilityCalculation INSTANCE = new AffordabilityCalculation();
    }
    public static AffordabilityCalculation instance() {
        return AffordabilityCalculationLazyHolder.INSTANCE;
    }

    private AffordabilityCalculation() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("MonthlyIncome") != null ? number(input_.get("MonthlyIncome")) : null), (input_.get("MonthlyRepayments") != null ? number(input_.get("MonthlyRepayments")) : null), (input_.get("MonthlyExpenses") != null ? number(input_.get("MonthlyExpenses")) : null), input_.get("RiskCategory"), (input_.get("RequiredMonthlyInstallment") != null ? number(input_.get("RequiredMonthlyInstallment")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AffordabilityCalculation'", e);
            return null;
        }
    }

    public Boolean apply(java.math.BigDecimal monthlyIncome, java.math.BigDecimal monthlyRepayments, java.math.BigDecimal monthlyExpenses, String riskCategory, java.math.BigDecimal requiredMonthlyInstallment, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'AffordabilityCalculation'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long affordabilityCalculationStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments affordabilityCalculationArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            affordabilityCalculationArguments_.put("MonthlyIncome", monthlyIncome);
            affordabilityCalculationArguments_.put("MonthlyRepayments", monthlyRepayments);
            affordabilityCalculationArguments_.put("MonthlyExpenses", monthlyExpenses);
            affordabilityCalculationArguments_.put("RiskCategory", riskCategory);
            affordabilityCalculationArguments_.put("RequiredMonthlyInstallment", requiredMonthlyInstallment);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_);

            // Evaluate BKM 'AffordabilityCalculation'
            Boolean output_ = lambda.apply(monthlyIncome, monthlyRepayments, monthlyExpenses, riskCategory, requiredMonthlyInstallment, context_);

            // End BKM 'AffordabilityCalculation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, affordabilityCalculationArguments_, output_, (System.currentTimeMillis() - affordabilityCalculationStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'AffordabilityCalculation' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                java.math.BigDecimal monthlyIncome = 0 < args_.length ? (java.math.BigDecimal) args_[0] : null;
                java.math.BigDecimal monthlyRepayments = 1 < args_.length ? (java.math.BigDecimal) args_[1] : null;
                java.math.BigDecimal monthlyExpenses = 2 < args_.length ? (java.math.BigDecimal) args_[2] : null;
                String riskCategory = 3 < args_.length ? (String) args_[3] : null;
                java.math.BigDecimal requiredMonthlyInstallment = 4 < args_.length ? (java.math.BigDecimal) args_[4] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 5 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[5] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                java.math.BigDecimal disposableIncome = numericSubtract(monthlyIncome, numericAdd(monthlyExpenses, monthlyRepayments));
                java.math.BigDecimal creditContingencyFactor = CreditContingencyFactorTable.instance().apply(riskCategory, context_);
                Boolean affordability = (booleanEqual(numericGreaterThan(numericMultiply(disposableIncome, creditContingencyFactor), requiredMonthlyInstallment), Boolean.TRUE)) ? Boolean.TRUE : Boolean.FALSE;
                return affordability;
            }
        };
}
