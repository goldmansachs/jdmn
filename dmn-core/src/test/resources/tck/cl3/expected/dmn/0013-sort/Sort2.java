
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "sort2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Sort2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sort2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Sort2() {
    }

    public List<type.TRow> apply(String tableB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((tableB != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(tableB, type.TRowImpl[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort2'", e);
            return null;
        }
    }

    public List<type.TRow> apply(String tableB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((tableB != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(tableB, type.TRowImpl[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort2'", e);
            return null;
        }
    }

    public List<type.TRow> apply(List<type.TRow> tableB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(tableB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<type.TRow> apply(List<type.TRow> tableB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'sort2'
            long sort2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sort2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sort2Arguments_.put("tableB", tableB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_);

            // Evaluate decision 'sort2'
            List<type.TRow> output_ = evaluate(tableB, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'sort2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_, output_, (System.currentTimeMillis() - sort2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sort2' evaluation", e);
            return null;
        }
    }

    private List<type.TRow> evaluate(List<type.TRow> tableB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return sort(tableB, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args) {type.TRow x = (type.TRow)args[0]; type.TRow y = (type.TRow)args[1];return numericLessThan(((java.math.BigDecimal)(x != null ? x.getCol2() : null)), ((java.math.BigDecimal)(y != null ? y.getCol2() : null)));}}).stream().map(x -> type.TRow.toTRow(x)).collect(Collectors.toList());
    }
}
