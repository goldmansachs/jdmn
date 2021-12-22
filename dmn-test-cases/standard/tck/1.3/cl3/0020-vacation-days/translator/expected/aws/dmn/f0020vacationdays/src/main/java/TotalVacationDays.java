
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Total Vacation Days'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Total Vacation Days'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class TotalVacationDays extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'Total Vacation Days'",
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

    public java.math.BigDecimal apply(String age, String yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((age != null ? number(age) : null), (yearsOfService != null ? number(yearsOfService) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'TotalVacationDays'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String age, String yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((age != null ? number(age) : null), (yearsOfService != null ? number(yearsOfService) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TotalVacationDays'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(age, yearsOfService, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Total Vacation Days''
            long totalVacationDaysStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments totalVacationDaysArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            totalVacationDaysArguments_.put("Age", age);
            totalVacationDaysArguments_.put("'Years of Service'", yearsOfService);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_);

            // Evaluate decision ''Total Vacation Days''
            java.math.BigDecimal output_ = evaluate(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Total Vacation Days''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_, output_, (System.currentTimeMillis() - totalVacationDaysStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Total Vacation Days'' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply child decisions
        java.math.BigDecimal baseVacationDays = TotalVacationDays.this.baseVacationDays.apply(annotationSet_, eventListener_, externalExecutor_, cache_);
        java.math.BigDecimal extraDaysCase1 = TotalVacationDays.this.extraDaysCase1.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_);
        java.math.BigDecimal extraDaysCase2 = TotalVacationDays.this.extraDaysCase2.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_);
        java.math.BigDecimal extraDaysCase3 = TotalVacationDays.this.extraDaysCase3.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_);

        return numericAdd(numericAdd(baseVacationDays, max(extraDaysCase1, extraDaysCase3)), extraDaysCase2);
    }
}
