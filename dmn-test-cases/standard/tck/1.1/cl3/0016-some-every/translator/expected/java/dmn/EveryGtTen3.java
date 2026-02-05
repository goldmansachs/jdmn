
import java.util.*;
import java.util.stream.Collectors;

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
public class EveryGtTen3 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Boolean> {
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

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'EveryGtTen3'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EveryGtTen3'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new EveryGtTen3Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EveryGtTen3'", e);
            return null;
        }
    }

    public Boolean apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'everyGtTen3'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long everyGtTen3StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments everyGtTen3Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, everyGtTen3Arguments_);

            // Evaluate decision 'everyGtTen3'
            Boolean output_ = lambda.apply(context_);

            // End decision 'everyGtTen3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, everyGtTen3Arguments_, output_, (System.currentTimeMillis() - everyGtTen3StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'everyGtTen3' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                List<type.TItemPrice> priceTable1 = EveryGtTen3.this.priceTable1.apply(context_);

                return booleanAnd((List)priceTable1.stream().map(i -> booleanEqual(GtTen.instance().apply(((java.lang.Number)(i != null ? i.getPrice() : null)), context_), Boolean.TRUE)).collect(Collectors.toList()));
            }
        };
}
