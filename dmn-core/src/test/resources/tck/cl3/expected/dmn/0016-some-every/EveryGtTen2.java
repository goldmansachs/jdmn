
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "everyGtTen2"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "everyGtTen2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EveryGtTen2 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "everyGtTen2",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public EveryGtTen2() {
    }

    public Boolean apply(String priceTable2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((priceTable2 != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(priceTable2, type.TItemPriceImpl[].class)) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'EveryGtTen2'", e);
            return null;
        }
    }

    public Boolean apply(String priceTable2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((priceTable2 != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(priceTable2, type.TItemPriceImpl[].class)) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'EveryGtTen2'", e);
            return null;
        }
    }

    public Boolean apply(List<type.TItemPrice> priceTable2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(priceTable2, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(List<type.TItemPrice> priceTable2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'everyGtTen2'
            long everyGtTen2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments everyGtTen2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            everyGtTen2Arguments_.put("priceTable2", priceTable2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, everyGtTen2Arguments_);

            // Evaluate decision 'everyGtTen2'
            Boolean output_ = evaluate(priceTable2, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'everyGtTen2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, everyGtTen2Arguments_, output_, (System.currentTimeMillis() - everyGtTen2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'everyGtTen2' evaluation", e);
            return null;
        }
    }

    private Boolean evaluate(List<type.TItemPrice> priceTable2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return booleanAnd((List)priceTable2.stream().map(i -> numericGreaterThan(((java.math.BigDecimal)(i != null ? i.getPrice() : null)), number("10"))).collect(Collectors.toList()));
    }
}
