
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "insert2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "insert2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Insert2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "insert2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final LiteralNestedList literalNestedList;

    public Insert2() {
        this(new LiteralNestedList());
    }

    public Insert2(LiteralNestedList literalNestedList) {
        this.literalNestedList = literalNestedList;
    }

    public List<List<String>> apply(String position, String simpleList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((position != null ? number(position) : null), (simpleList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(simpleList, String[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Insert2'", e);
            return null;
        }
    }

    public List<List<String>> apply(String position, String simpleList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((position != null ? number(position) : null), (simpleList != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(simpleList, String[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Insert2'", e);
            return null;
        }
    }

    public List<List<String>> apply(java.math.BigDecimal position, List<String> simpleList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(position, simpleList, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<List<String>> apply(java.math.BigDecimal position, List<String> simpleList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("position", position);
            arguments.put("simpleList", simpleList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Apply child decisions
            List<List<String>> literalNestedListOutput = literalNestedList.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            List<List<String>> output_ = evaluate(literalNestedListOutput, position, simpleList, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'insert2' evaluation", e);
            return null;
        }
    }

    private List<List<String>> evaluate(List<List<String>> literalNestedList, java.math.BigDecimal position, List<String> simpleList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return insertBefore(literalNestedList, position, simpleList);
    }
}
