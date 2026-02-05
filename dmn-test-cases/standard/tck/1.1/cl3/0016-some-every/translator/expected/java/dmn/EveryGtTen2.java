
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
public class EveryGtTen2 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Boolean> {
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

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("priceTable2") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("priceTable2"), new com.fasterxml.jackson.core.type.TypeReference<List<type.TItemPrice>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'EveryGtTen2'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((EveryGtTen2Input_)input_).getPriceTable2(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EveryGtTen2'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new EveryGtTen2Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'EveryGtTen2'", e);
            return null;
        }
    }

    public Boolean apply(List<type.TItemPrice> priceTable2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'everyGtTen2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long everyGtTen2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments everyGtTen2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            everyGtTen2Arguments_.put("priceTable2", priceTable2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, everyGtTen2Arguments_);

            // Evaluate decision 'everyGtTen2'
            Boolean output_ = lambda.apply(priceTable2, context_);

            // End decision 'everyGtTen2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, everyGtTen2Arguments_, output_, (System.currentTimeMillis() - everyGtTen2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'everyGtTen2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                List<type.TItemPrice> priceTable2 = 0 < args_.length ? (List<type.TItemPrice>) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return booleanAnd((List)priceTable2.stream().map(i -> numericGreaterThan(((java.lang.Number)(i != null ? i.getPrice() : null)), number("10"))).collect(Collectors.toList()));
            }
        };
}
