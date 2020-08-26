
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "logical"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "logical",
    label = "logical ",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Logical extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "logical",
        "logical ",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Logical() {
    }

    public Boolean apply(String booleanA, String booleanB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanA != null ? Boolean.valueOf(booleanA) : null), (booleanB != null ? Boolean.valueOf(booleanB) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Logical'", e);
            return null;
        }
    }

    public Boolean apply(String booleanA, String booleanB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanA != null ? Boolean.valueOf(booleanA) : null), (booleanB != null ? Boolean.valueOf(booleanB) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Logical'", e);
            return null;
        }
    }

    public Boolean apply(Boolean booleanA, Boolean booleanB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanA, booleanB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public Boolean apply(Boolean booleanA, Boolean booleanB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'logical'
            long logicalStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments logicalArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            logicalArguments_.put("booleanA", booleanA);
            logicalArguments_.put("booleanB", booleanB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, logicalArguments_);

            // Evaluate decision 'logical'
            Boolean output_ = evaluate(booleanA, booleanB, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'logical'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, logicalArguments_, output_, (System.currentTimeMillis() - logicalStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'logical' evaluation", e);
            return null;
        }
    }

    protected Boolean evaluate(Boolean booleanA, Boolean booleanB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return booleanNot(booleanOr(booleanAnd(booleanA, booleanB), booleanA));
    }
}
