
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decisionDate"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml",
    name = "decisionDate",
    label = "decision date",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionDate extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "http://www.provider.com/dmn/1.1/diagram/af75837563be485d941eba0f9bf7a5f4.xml",
        "decisionDate",
        "decision date",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public DecisionDate() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("date input") != null ? date(input_.get("date input")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionDate'", e);
            return null;
        }
    }

    public List<String> apply(java.time.LocalDate dateInput, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decisionDate'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionDateStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionDateArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionDateArguments_.put("date input", dateInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionDateArguments_);

            // Evaluate decision 'decisionDate'
            List<String> output_ = evaluate(dateInput, context_);

            // End decision 'decisionDate'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionDateArguments_, output_, (System.currentTimeMillis() - decisionDateStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionDate' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(java.time.LocalDate dateInput, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return ImportedLogicDates.instance().apply(dateInput, context_);
    }
}
