
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "priceTable1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "priceTable1",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class PriceTable1 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "priceTable1",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public PriceTable1() {
    }

    public List<type.TItemPrice> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<type.TItemPrice> apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'priceTable1'
            long priceTable1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments priceTable1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, priceTable1Arguments_);

            // Evaluate decision 'priceTable1'
            List<type.TItemPrice> output_ = evaluate(annotationSet_, eventListener_, externalExecutor_);

            // End decision 'priceTable1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, priceTable1Arguments_, output_, (System.currentTimeMillis() - priceTable1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'priceTable1' evaluation", e);
            return null;
        }
    }

    private List<type.TItemPrice> evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return asList(new type.TItemPriceImpl("widget", number("25")),
				new type.TItemPriceImpl("sprocket", number("15")),
				new type.TItemPriceImpl("trinket", number("1.5")));
    }
}
