
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-bkm.ftl", "logic"})
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

    public static final Logic INSTANCE = new Logic();

    private Logic() {
    }

    public static String logic(javax.xml.datatype.XMLGregorianCalendar dateInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return INSTANCE.apply(dateInput, timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    private String apply(javax.xml.datatype.XMLGregorianCalendar dateInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'logic'
            long logicStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments logicArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            logicArguments_.put("date input", dateInput);
            logicArguments_.put("time input", timeInput);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, logicArguments_);

            // Evaluate BKM 'logic'
            String output_ = evaluate(dateInput, timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'logic'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, logicArguments_, output_, (System.currentTimeMillis() - logicStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'logic' evaluation", e);
            return null;
        }
    }

    protected String evaluate(javax.xml.datatype.XMLGregorianCalendar dateInput, javax.xml.datatype.XMLGregorianCalendar timeInput, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return new FinalDecision().apply(dateInput, timeInput, annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
