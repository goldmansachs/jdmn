
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "concatenate3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "concatenate3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Concatenate3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "concatenate3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final LiteralSimpleList literalSimpleList;

    public Concatenate3() {
        this(new LiteralSimpleList());
    }

    public Concatenate3(LiteralSimpleList literalSimpleList) {
        this.literalSimpleList = literalSimpleList;
    }

    public List<String> apply(String nestedList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((nestedList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(nestedList, java.util.List[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Concatenate3'", e);
            return null;
        }
    }

    public List<String> apply(String nestedList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((nestedList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(nestedList, java.util.List[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Concatenate3'", e);
            return null;
        }
    }

    public List<String> apply(List<List<String>> nestedList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(nestedList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<String> apply(List<List<String>> nestedList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("nestedList", nestedList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            List<String> literalSimpleListOutput = literalSimpleList.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            List<String> output_ = evaluate(literalSimpleListOutput, nestedList, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'concatenate3' evaluation", e);
            return null;
        }
    }

    private List<String> evaluate(List<String> literalSimpleList, List<List<String>> nestedList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return concatenate(literalSimpleList, flatten(nestedList));
    }
}
