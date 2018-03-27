
import java.util.*;
import java.util.stream.Collectors;

import static GtTen.gtTen;

@javax.annotation.Generated(value = {"decision.ftl", "everyGtTen3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "everyGtTen3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class EveryGtTen3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "everyGtTen3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final PriceTable1 priceTable1;

    public EveryGtTen3() {
        this(new PriceTable1());
    }

    public EveryGtTen3(PriceTable1 priceTable1) {
        this.priceTable1 = priceTable1;
    }

    public Boolean apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public Boolean apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'everyGtTen3'
            long everyGtTen3StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments everyGtTen3Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, everyGtTen3Arguments_);

            // Apply child decisions
            List<type.TItemPrice> priceTable1 = this.priceTable1.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'everyGtTen3'
            Boolean output_ = evaluate(priceTable1, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'everyGtTen3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, everyGtTen3Arguments_, output_, (System.currentTimeMillis() - everyGtTen3StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'everyGtTen3' evaluation", e);
            return null;
        }
    }

    private Boolean evaluate(List<type.TItemPrice> priceTable1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return booleanAnd((List)priceTable1.stream().map(i -> booleanEqual(gtTen(((java.math.BigDecimal)(i != null ? i.getPrice() : null)), annotationSet_, eventListener_, externalExecutor_), Boolean.TRUE)).collect(Collectors.toList()));
    }
}
