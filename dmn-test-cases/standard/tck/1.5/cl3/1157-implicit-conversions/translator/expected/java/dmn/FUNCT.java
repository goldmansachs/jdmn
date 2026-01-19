
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "FUNCT"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "FUNCT",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FUNCT extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "FUNCT",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class FUNCTLazyHolder {
        static final FUNCT INSTANCE = new FUNCT();
    }
    public static FUNCT instance() {
        return FUNCTLazyHolder.INSTANCE;
    }

    private FUNCT() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("numberList") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("numberList"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), (input_.get("number") != null ? number(input_.get("number")) : null), (input_.get("dateTime") != null ? dateAndTime(input_.get("dateTime")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FUNCT'", e);
            return null;
        }
    }

    public String apply(List<java.lang.Number> numberList, java.lang.Number number, java.time.temporal.TemporalAccessor dateTime, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'FUNCT'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fUNCTStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fUNCTArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fUNCTArguments_.put("numberList", numberList);
            fUNCTArguments_.put("number", number);
            fUNCTArguments_.put("dateTime", dateTime);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fUNCTArguments_);

            // Evaluate BKM 'FUNCT'
            String output_ = lambda.apply(numberList, number, dateTime, context_);

            // End BKM 'FUNCT'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fUNCTArguments_, output_, (System.currentTimeMillis() - fUNCTStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'FUNCT' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                List<java.lang.Number> numberList = 0 < args_.length ? (List<java.lang.Number>) args_[0] : null;
                java.lang.Number number = 1 < args_.length ? (java.lang.Number) args_[1] : null;
                java.time.temporal.TemporalAccessor dateTime = 2 < args_.length ? (java.time.temporal.TemporalAccessor) args_[2] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return stringJoin(asList(string(numberList), string(number), string(dateTime)), "-");
            }
        };
}
