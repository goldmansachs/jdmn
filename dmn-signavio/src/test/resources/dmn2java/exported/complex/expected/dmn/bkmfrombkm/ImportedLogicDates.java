
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

    public static final ImportedLogicDates INSTANCE = new ImportedLogicDates();

    private ImportedLogicDates() {
    }

    public static List<String> importedLogicDates(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return INSTANCE.apply(date, annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    private List<String> apply(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'importedLogicDates'
            long importedLogicDatesStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments importedLogicDatesArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            importedLogicDatesArguments_.put("date", date);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, importedLogicDatesArguments_);

            // Evaluate BKM 'importedLogicDates'
            List<String> output_ = evaluate(date, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'importedLogicDates'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, importedLogicDatesArguments_, output_, (System.currentTimeMillis() - importedLogicDatesStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'importedLogicDates' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(javax.xml.datatype.XMLGregorianCalendar date, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return new DateOperators().apply(date, annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
