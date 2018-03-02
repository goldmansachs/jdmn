
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "sort3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Sort3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sort3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Sort3() {
    }

    public List<String> apply(String stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((stringList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(stringList, String[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort3'", e);
            return null;
        }
    }

    public List<String> apply(String stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((stringList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(stringList, String[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort3'", e);
            return null;
        }
    }

    public List<String> apply(List<String> stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(stringList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<String> apply(List<String> stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'sort3'
            long sort3StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sort3Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sort3Arguments_.put("stringList", stringList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort3Arguments_);

            // Evaluate decision 'sort3'
            List<String> output_ = evaluate(stringList, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'sort3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort3Arguments_, output_, (System.currentTimeMillis() - sort3StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sort3' evaluation", e);
            return null;
        }
    }

    private List<String> evaluate(List<String> stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return sort(stringList, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {String x = (String)args[0]; String y = (String)args[1];return stringLessThan(x, y);}});
    }
}
