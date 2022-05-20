
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "sort3"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "sort3",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Sort3 extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "sort3",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Sort3() {
    }

    public List<String> apply(String stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((stringList != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(stringList, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Sort3'", e);
            return null;
        }
    }

    public List<String> apply(List<String> stringList, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'sort3'
            long sort3StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments sort3Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            sort3Arguments_.put("stringList", stringList);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, sort3Arguments_);

            // Evaluate decision 'sort3'
            List<String> output_ = lambda.apply(stringList, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'sort3'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, sort3Arguments_, output_, (System.currentTimeMillis() - sort3StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'sort3' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<String>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<String>>() {
            public List<String> apply(Object... args_) {
                List<String> stringList = 0 < args_.length ? (List<String>) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[4] : null;

                return sort(stringList, new com.gs.dmn.runtime.LambdaExpression<Boolean>() {public Boolean apply(Object... args_) {String x = (String)args_[0]; String y = (String)args_[1];return stringLessThan(x, y);}});
            }
        };
}
