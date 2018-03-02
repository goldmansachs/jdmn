
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "BaseVacationDays"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "BaseVacationDays",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class BaseVacationDays extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "BaseVacationDays",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public BaseVacationDays() {
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'BaseVacationDays'
            long baseVacationDaysStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments baseVacationDaysArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, baseVacationDaysArguments_);

            // Evaluate decision 'BaseVacationDays'
            java.math.BigDecimal output_ = evaluate(annotationSet_, eventListener_, externalExecutor_);

            // End decision 'BaseVacationDays'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, baseVacationDaysArguments_, output_, (System.currentTimeMillis() - baseVacationDaysStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'BaseVacationDays' evaluation", e);
            return null;
        }
    }

    private java.math.BigDecimal evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return number("22");
    }
}
