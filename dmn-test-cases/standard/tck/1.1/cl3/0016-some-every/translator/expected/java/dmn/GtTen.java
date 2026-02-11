
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "gtTen"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_d7643a02-a8fc-4a6f-a8a9-5c2881afea70",
    name = "gtTen",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class GtTen extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Boolean> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "gtTen",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class GtTenLazyHolder {
        static final GtTen INSTANCE = new GtTen();
    }
    public static GtTen instance() {
        return GtTenLazyHolder.INSTANCE;
    }

    private GtTen() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("theNumber") != null ? number(input_.get("theNumber")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'GtTen'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((GtTenInput_)input_).getTheNumber(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'GtTen'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new GtTenInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'GtTen'", e);
            return null;
        }
    }

    public Boolean apply(java.lang.Number theNumber, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start BKM 'gtTen'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long gtTenStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments gtTenArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            gtTenArguments_.put("theNumber", theNumber);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, gtTenArguments_);

            // Evaluate BKM 'gtTen'
            Boolean output_ = lambda.apply(theNumber, context_);

            // End BKM 'gtTen'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, gtTenArguments_, output_, (System.currentTimeMillis() - gtTenStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'gtTen' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                java.lang.Number theNumber = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return numericGreaterThan(theNumber, number("10"));
            }
        };
}
