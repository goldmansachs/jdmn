
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
public class Sort2 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
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

    @java.lang.Override()
    public List<type.TRow> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("tableB") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("tableB"), new com.fasterxml.jackson.core.type.TypeReference<List<type.TRow>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort2'", e);
            return null;
        }
    }

    public List<type.TRow> apply(List<type.TRow> tableB, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'sort2'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long sort2StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sort2Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sort2Arguments_.put("tableB", tableB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_);

            // Evaluate decision 'sort2'
            List<type.TRow> output_ = lambda.apply(tableB, context_);

            // End decision 'sort2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort2Arguments_, output_, (System.currentTimeMillis() - sort2StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sort2' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<type.TRow>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<type.TRow>>() {
            public List<type.TRow> apply(Object... args_) {
                List<type.TRow> tableB = 0 < args_.length ? (List<type.TRow>) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return sort(tableB, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {type.TRow x = (type.TRow)args_[0]; type.TRow y = (type.TRow)args_[1];return numericLessThan(((java.lang.Number)(x != null ? x.getCol2() : null)), ((java.lang.Number)(y != null ? y.getCol2() : null)));}});
            }
        };
}
