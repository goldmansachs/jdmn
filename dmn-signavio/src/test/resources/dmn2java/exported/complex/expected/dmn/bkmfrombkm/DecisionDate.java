
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decisionDate"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionDate",
    label = "decision date",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionDate extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionDate",
        "decision date",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public DecisionDate() {
    }

    public List<String> apply(String dateInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((dateInput != null ? date(dateInput) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionDate'", e);
            return null;
        }
    }

    public List<String> apply(String dateInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((dateInput != null ? date(dateInput) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionDate'", e);
            return null;
        }
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar dateInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(dateInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar dateInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decisionDate'
            long decisionDateStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionDateArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionDateArguments_.put("date input", dateInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionDateArguments_);

            // Evaluate decision 'decisionDate'
            List<String> output_ = evaluate(dateInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decisionDate'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionDateArguments_, output_, (System.currentTimeMillis() - decisionDateStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionDate' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar dateInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return ImportedLogicDates.importedLogicDates(dateInput, annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
