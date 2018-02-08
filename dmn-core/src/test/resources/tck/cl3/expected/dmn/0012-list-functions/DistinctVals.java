
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "distinctVals"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "distinctVals",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DistinctVals extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "distinctVals",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final InsertBefore1 insertBefore1;

    public DistinctVals() {
        this(new InsertBefore1());
    }

    public DistinctVals(InsertBefore1 insertBefore1) {
        this.insertBefore1 = insertBefore1;
    }

    public List<String> apply(String list2, String string1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((list2 != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(list2, String[].class)) : null), string1, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'DistinctVals'", e);
            return null;
        }
    }

    public List<String> apply(String list2, String string1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((list2 != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(list2, String[].class)) : null), string1, annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DistinctVals'", e);
            return null;
        }
    }

    public List<String> apply(List<String> list2, String string1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(list2, string1, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<String> apply(List<String> list2, String string1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("list2", list2);
            arguments.put("string1", string1);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            List<String> insertBefore1Output = insertBefore1.apply(list2, string1, annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            List<String> output_ = evaluate(insertBefore1Output, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'distinctVals' evaluation", e);
            return null;
        }
    }

    private List<String> evaluate(List<String> insertBefore1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return distinctValues(insertBefore1);
    }
}
