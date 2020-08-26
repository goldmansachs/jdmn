
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision1() {
    }

    public List<String> apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((employees != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision1'", e);
            return null;
        }
    }

    public List<String> apply(String employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((employees != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employees, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision1'", e);
            return null;
        }
    }

    public List<String> apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(employees, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision1'
            long decision1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision1Arguments_.put("Employees", employees);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision1Arguments_);

            // Evaluate decision 'decision1'
            List<String> output_ = evaluate(employees, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision1Arguments_, output_, (System.currentTimeMillis() - decision1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision1' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(List<String> employees, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return sublist(employees, number("2"), number("1"));
    }
}
