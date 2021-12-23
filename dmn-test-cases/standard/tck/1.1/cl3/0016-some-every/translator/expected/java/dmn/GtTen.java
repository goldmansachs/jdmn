
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"bkm.ftl", "gtTen"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "gtTen",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class GtTen extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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

    public Boolean apply(java.math.BigDecimal theNumber, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'gtTen'
            long gtTenStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments gtTenArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            gtTenArguments_.put("theNumber", theNumber);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, gtTenArguments_);

            // Evaluate BKM 'gtTen'
            Boolean output_ = lambda.apply(theNumber, annotationSet_, eventListener_, externalExecutor_, cache_);

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
            public Boolean apply(Object... args) {
                java.math.BigDecimal theNumber = 0 < args.length ? (java.math.BigDecimal) args[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 1 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[1] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 2 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[2] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 3 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[3] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 4 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[4] : null;

                return numericGreaterThan(theNumber, number("10"));
            }
        };
}
