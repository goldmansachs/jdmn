
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision5"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision5",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision5 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision5",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision5() {
    }

    public String apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((employees != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, String[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision5'", e);
            return null;
        }
    }

    public String apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((employees != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, String[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision5'", e);
            return null;
        }
    }

    public String apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(employees, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'decision5'
            long decision5StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision5Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision5Arguments_.put("employees", employees);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision5Arguments_);

            // Evaluate decision 'decision5'
            String output_ = evaluate(employees, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'decision5'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision5Arguments_, output_, (System.currentTimeMillis() - decision5StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision5' evaluation", e);
            return null;
        }
    }

    private String evaluate(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return upperCase(this.<String>asElement(employees.stream().filter(item -> stringEqual(item, "Bob")).collect(Collectors.toList())));
    }
}
