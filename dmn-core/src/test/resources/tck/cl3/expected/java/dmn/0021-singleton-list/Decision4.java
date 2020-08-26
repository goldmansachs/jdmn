
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision4"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision4",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision4 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision4",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision4() {
    }

    public String apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((employees != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision4'", e);
            return null;
        }
    }

    public String apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((employees != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision4'", e);
            return null;
        }
    }

    public String apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(employees, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision4'
            long decision4StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision4Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision4Arguments_.put("Employees", employees);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision4Arguments_);

            // Evaluate decision 'decision4'
            String output_ = evaluate(employees, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision4'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision4Arguments_, output_, (System.currentTimeMillis() - decision4StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision4' evaluation", e);
            return null;
        }
    }

    protected String evaluate(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return this.<String>asElement(employees.stream().filter(item -> stringEqual(item, "Bob")).collect(Collectors.toList()));
    }
}
