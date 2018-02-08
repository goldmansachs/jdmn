
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "conditionWithFunctions"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "conditionWithFunctions",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ConditionWithFunctions extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "conditionWithFunctions",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ConditionWithFunctions() {
    }

    public String apply(String aDate, String aString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((aDate != null ? date(aDate) : null), aString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'ConditionWithFunctions'", e);
            return null;
        }
    }

    public String apply(String aDate, String aString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((aDate != null ? date(aDate) : null), aString, annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ConditionWithFunctions'", e);
            return null;
        }
    }

    public String apply(javax.xml.datatype.XMLGregorianCalendar aDate, String aString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(aDate, aString, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(javax.xml.datatype.XMLGregorianCalendar aDate, String aString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("aDate", aDate);
            arguments.put("aString", aString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            String output_ = evaluate(aDate, aString, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'conditionWithFunctions' evaluation", e);
            return null;
        }
    }

    private String evaluate(javax.xml.datatype.XMLGregorianCalendar aDate, String aString, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return (booleanEqual(dateGreaterThan(aDate, date("2017-01-01")), Boolean.TRUE)) ? substringBefore(aString, " ") : substringAfter(aString, " ");
    }
}
