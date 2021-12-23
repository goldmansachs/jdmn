
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'Base Vacation Days'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'Base Vacation Days'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class BaseVacationDays extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'Base Vacation Days'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public BaseVacationDays() {
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public java.math.BigDecimal apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''Base Vacation Days''
            long baseVacationDaysStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments baseVacationDaysArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, baseVacationDaysArguments_);

            // Evaluate decision ''Base Vacation Days''
            java.math.BigDecimal output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''Base Vacation Days''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, baseVacationDaysArguments_, output_, (System.currentTimeMillis() - baseVacationDaysStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''Base Vacation Days'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> lambda =
        new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {
            public java.math.BigDecimal apply(Object... args) {
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 0 < args.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args[0] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 1 < args.length ? (com.gs.dmn.runtime.listener.EventListener) args[1] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 2 < args.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args[2] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 3 < args.length ? (com.gs.dmn.runtime.cache.Cache) args[3] : null;

                return number("22");
            }
        };
}
