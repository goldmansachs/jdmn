package parent;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "SimpleParent"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://kie.org/dmn/_41DA1B21-4EF2-42AC-8B8C-0EB93859BC91",
    name = "SimpleParent",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SimpleParent extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<parent.type.ParentPerson> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "https://kie.org/dmn/_41DA1B21-4EF2-42AC-8B8C-0EB93859BC91",
        "SimpleParent",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final child.SimpleChild simpleChild;

    public SimpleParent() {
        this(new child.SimpleChild());
    }

    public SimpleParent(child.SimpleChild simpleChild) {
        this.simpleChild = simpleChild;
    }

    @java.lang.Override()
    public parent.type.ParentPerson applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'SimpleParent'", e);
            return null;
        }
    }

    @java.lang.Override()
    public parent.type.ParentPerson applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'SimpleParent'", e);
            return null;
        }
    }

    @java.lang.Override()
    public parent.type.ParentPerson applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new SimpleParentInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'SimpleParent'", e);
            return null;
        }
    }

    public parent.type.ParentPerson apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'SimpleParent'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long simpleParentStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments simpleParentArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, simpleParentArguments_);

            // Evaluate decision 'SimpleParent'
            parent.type.ParentPerson output_ = lambda.apply(context_);

            // End decision 'SimpleParent'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, simpleParentArguments_, output_, (System.currentTimeMillis() - simpleParentStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'SimpleParent' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<parent.type.ParentPerson> lambda =
        new com.gs.dmn.runtime.LambdaExpression<parent.type.ParentPerson>() {
            public parent.type.ParentPerson apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                child.type.ChildPerson simpleChild = SimpleParent.this.simpleChild.apply(context_);

                return parent.type.ParentPerson.toParentPerson(simpleChild);
            }
        };
}
