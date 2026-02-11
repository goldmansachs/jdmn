
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Total Vacation Days"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://www.drools.org/kie-dmn",
    name = "Total Vacation Days",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class TotalVacationDays extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Total Vacation Days",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final BaseVacationDays baseVacationDays;
    private final ExtraDaysCase1 extraDaysCase1;
    private final ExtraDaysCase2 extraDaysCase2;
    private final ExtraDaysCase3 extraDaysCase3;

    public TotalVacationDays() {
        this(new BaseVacationDays(), new ExtraDaysCase1(), new ExtraDaysCase2(), new ExtraDaysCase3());
    }

    public TotalVacationDays(BaseVacationDays baseVacationDays, ExtraDaysCase1 extraDaysCase1, ExtraDaysCase2 extraDaysCase2, ExtraDaysCase3 extraDaysCase3) {
        this.baseVacationDays = baseVacationDays;
        this.extraDaysCase1 = extraDaysCase1;
        this.extraDaysCase2 = extraDaysCase2;
        this.extraDaysCase3 = extraDaysCase3;
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Age") != null ? number(input_.get("Age")) : null), (input_.get("Years of Service") != null ? number(input_.get("Years of Service")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TotalVacationDays'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((TotalVacationDaysInput_)input_).getAge(), ((TotalVacationDaysInput_)input_).getYearsOfService(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'TotalVacationDays'", e);
            return null;
        }
    }

    @java.lang.Override()
    public java.lang.Number applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new TotalVacationDaysInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'TotalVacationDays'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number age, java.lang.Number yearsOfService, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Total Vacation Days'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long totalVacationDaysStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments totalVacationDaysArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            totalVacationDaysArguments_.put("Age", age);
            totalVacationDaysArguments_.put("Years of Service", yearsOfService);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_);

            // Evaluate decision 'Total Vacation Days'
            java.lang.Number output_ = lambda.apply(age, yearsOfService, context_);

            // End decision 'Total Vacation Days'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_, output_, (System.currentTimeMillis() - totalVacationDaysStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Total Vacation Days' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.lang.Number> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {
            public java.lang.Number apply(Object... args_) {
                java.lang.Number age = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                java.lang.Number yearsOfService = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                java.lang.Number baseVacationDays = TotalVacationDays.this.baseVacationDays.apply(context_);
                java.lang.Number extraDaysCase1 = TotalVacationDays.this.extraDaysCase1.apply(age, yearsOfService, context_);
                java.lang.Number extraDaysCase2 = TotalVacationDays.this.extraDaysCase2.apply(age, yearsOfService, context_);
                java.lang.Number extraDaysCase3 = TotalVacationDays.this.extraDaysCase3.apply(age, yearsOfService, context_);

                return numericAdd(numericAdd(baseVacationDays, max(extraDaysCase1, extraDaysCase3)), extraDaysCase2);
            }
        };
}
