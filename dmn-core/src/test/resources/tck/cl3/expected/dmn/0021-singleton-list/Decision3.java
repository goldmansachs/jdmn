
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision3() {
    }

    public List<String> apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((employees != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, String[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision3'", e);
            return null;
        }
    }

    public List<String> apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((employees != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, String[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision3'", e);
            return null;
        }
    }

    public List<String> apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(employees, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<String> apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'decision3'
            long decision3StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision3Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision3Arguments_.put("employees", employees);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision3Arguments_);

            // Evaluate decision 'decision3'
            List<String> output_ = evaluate(employees, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'decision3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision3Arguments_, output_, (System.currentTimeMillis() - decision3StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision3' evaluation", e);
            return null;
        }
    }

    private List<String> evaluate(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return employees.stream().filter(item -> stringEqual(item, "Bob")).collect(Collectors.toList());
    }
}
