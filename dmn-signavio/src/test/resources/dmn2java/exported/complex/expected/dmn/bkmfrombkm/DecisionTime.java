
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decisionTime"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionTime",
    label = "decision time",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionTime extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionTime",
        "decision time",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public DecisionTime() {
    }

    public List<String> apply(String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((timeInput != null ? time(timeInput) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionTime'", e);
            return null;
        }
    }

    public List<String> apply(String timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((timeInput != null ? time(timeInput) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionTime'", e);
            return null;
        }
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(timeInput, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decisionTime'
            long decisionTimeStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionTimeArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionTimeArguments_.put("time input", timeInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionTimeArguments_);

            // Evaluate decision 'decisionTime'
            List<String> output_ = evaluate(timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decisionTime'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionTimeArguments_, output_, (System.currentTimeMillis() - decisionTimeStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionTime' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return ImportedLogicTime.importedLogicTime(timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
