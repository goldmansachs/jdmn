
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "TotalVacationDays"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "TotalVacationDays",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class TotalVacationDays extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "TotalVacationDays",
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
            return apply((age != null ? number(age) : null), (yearsOfService != null ? number(yearsOfService) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'TotalVacationDays'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(String age, String yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((age != null ? number(age) : null), (yearsOfService != null ? number(yearsOfService) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'TotalVacationDays'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(age, yearsOfService, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(java.math.BigDecimal age, java.math.BigDecimal yearsOfService, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("age", age);
            arguments.put("yearsOfService", yearsOfService);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            java.math.BigDecimal baseVacationDaysOutput = baseVacationDays.apply(annotationSet_, eventListener_, externalExecutor_);
            java.math.BigDecimal extraDaysCase1Output = extraDaysCase1.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_);
            java.math.BigDecimal extraDaysCase2Output = extraDaysCase2.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_);
            java.math.BigDecimal extraDaysCase3Output = extraDaysCase3.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            java.math.BigDecimal output_ = evaluate(baseVacationDaysOutput, extraDaysCase1Output, extraDaysCase2Output, extraDaysCase3Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'TotalVacationDays' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(java.math.BigDecimal baseVacationDays, java.math.BigDecimal extraDaysCase1, java.math.BigDecimal extraDaysCase2, java.math.BigDecimal extraDaysCase3, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return numericAdd(numericAdd(baseVacationDays, max(extraDaysCase1, extraDaysCase3)), extraDaysCase2);
    }
}
