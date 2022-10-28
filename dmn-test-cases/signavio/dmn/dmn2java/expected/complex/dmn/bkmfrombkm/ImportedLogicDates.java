
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-bkm.ftl", "importedLogicDates"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "importedLogicDates",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ImportedLogicDates extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "importedLogicDates",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class ImportedLogicDatesLazyHolder {
        static final ImportedLogicDates INSTANCE = new ImportedLogicDates();
    }
    public static ImportedLogicDates instance() {
        return ImportedLogicDatesLazyHolder.INSTANCE;
    }

    private ImportedLogicDates() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("date") != null ? date(input_.get("date")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ImportedLogicDates'", e);
            return null;
        }
    }

    public List<String> apply(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'importedLogicDates'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long importedLogicDatesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments importedLogicDatesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            importedLogicDatesArguments_.put("date", date);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, importedLogicDatesArguments_);

            // Evaluate BKM 'importedLogicDates'
            List<String> output_ = evaluate(date, context_);

            // End BKM 'importedLogicDates'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, importedLogicDatesArguments_, output_, (System.currentTimeMillis() - importedLogicDatesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'importedLogicDates' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return new DateOperators().apply(date, context_);
    }
}
