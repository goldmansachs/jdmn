
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-bkm.ftl", "logic"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "logic",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Logic extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "logic",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class LogicLazyHolder {
        static final Logic INSTANCE = new Logic();
    }
    public static Logic instance() {
        return LogicLazyHolder.INSTANCE;
    }

    private Logic() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("dateInput") != null ? date(input_.get("dateInput")) : null), (input_.get("timeInput") != null ? time(input_.get("timeInput")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Logic'", e);
            return null;
        }
    }

    public String apply(javax.xml.datatype.XMLGregorianCalendar dateInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'logic'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long logicStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments logicArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            logicArguments_.put("date input", dateInput);
            logicArguments_.put("time input", timeInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, logicArguments_);

            // Evaluate BKM 'logic'
            String output_ = evaluate(dateInput, timeInput, context_);

            // End BKM 'logic'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, logicArguments_, output_, (System.currentTimeMillis() - logicStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'logic' evaluation", e);
            return null;
        }
    }

    protected String evaluate(javax.xml.datatype.XMLGregorianCalendar dateInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return new FinalDecision().apply(dateInput, timeInput, context_);
    }
}
