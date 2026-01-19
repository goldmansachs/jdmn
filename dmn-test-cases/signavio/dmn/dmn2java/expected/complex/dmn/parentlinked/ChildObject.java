
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "childObject"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "childObject",
    label = "ChildObject",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ChildObject extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<java.lang.Number> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "childObject",
        "ChildObject",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Abc abc;

    public ChildObject() {
        this(new Abc());
    }

    public ChildObject(Abc abc) {
        this.abc = abc;
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("num") != null ? number(input_.get("num")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ChildObject'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number num, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'childObject'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long childObjectStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments childObjectArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            childObjectArguments_.put("num", num);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, childObjectArguments_);

            // Evaluate decision 'childObject'
            java.lang.Number output_ = evaluate(num, context_);

            // End decision 'childObject'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, childObjectArguments_, output_, (System.currentTimeMillis() - childObjectStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'childObject' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(java.lang.Number num, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        java.lang.Number abc = this.abc.apply(num, context_);

        return abc;
    }
}
