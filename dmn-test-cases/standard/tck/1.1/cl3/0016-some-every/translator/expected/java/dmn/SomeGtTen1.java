
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "someGtTen1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "someGtTen1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SomeGtTen1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "someGtTen1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final PriceTable1 priceTable1;

    public SomeGtTen1() {
        this(new PriceTable1());
    }

    public SomeGtTen1(PriceTable1 priceTable1) {
        this.priceTable1 = priceTable1;
    }

    public Boolean apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'someGtTen1'
            long someGtTen1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments someGtTen1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, someGtTen1Arguments_);

            // Apply child decisions
            List<type.TItemPrice> priceTable1 = this.priceTable1.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'someGtTen1'
            Boolean output_ = evaluate(priceTable1, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'someGtTen1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, someGtTen1Arguments_, output_, (System.currentTimeMillis() - someGtTen1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'someGtTen1' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(List<type.TItemPrice> priceTable1, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return booleanOr((List)priceTable1.stream().map(i -> numericGreaterThan(((java.math.BigDecimal)(i != null ? i.getPrice() : null)), number("10"))).collect(Collectors.toList()));
    }
}
