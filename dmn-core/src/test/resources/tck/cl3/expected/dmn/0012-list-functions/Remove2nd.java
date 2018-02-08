
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "remove2nd"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "remove2nd",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Remove2nd extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "remove2nd",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Remove2nd() {
    }

    public List<String> apply(String list2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((list2 != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(list2, String[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Remove2nd'", e);
            return null;
        }
    }

    public List<String> apply(String list2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((list2 != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(list2, String[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Remove2nd'", e);
            return null;
        }
    }

    public List<String> apply(List<String> list2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(list2, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<String> apply(List<String> list2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("list2", list2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            List<String> output_ = evaluate(list2, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'remove2nd' evaluation", e);
            return null;
        }
    }

    private List<String> evaluate(List<String> list2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return remove(list2, number("2"));
    }
}
