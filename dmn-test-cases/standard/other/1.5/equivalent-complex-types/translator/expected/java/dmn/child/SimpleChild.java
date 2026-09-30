package child;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "SimpleChild"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://kie.org/dmn/_E41422F9-9D1F-4BAD-B81A-0A28390DF4F7",
    name = "SimpleChild",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class SimpleChild extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<child.type.ChildPerson> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "https://kie.org/dmn/_E41422F9-9D1F-4BAD-B81A-0A28390DF4F7",
        "SimpleChild",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public SimpleChild() {
    }

    @java.lang.Override()
    public child.type.ChildPerson applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'SimpleChild'", e);
            return null;
        }
    }

    @java.lang.Override()
    public child.type.ChildPerson applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'SimpleChild'", e);
            return null;
        }
    }

    @java.lang.Override()
    public child.type.ChildPerson applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new SimpleChildInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'SimpleChild'", e);
            return null;
        }
    }

    public child.type.ChildPerson apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'SimpleChild'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long simpleChildStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments simpleChildArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, simpleChildArguments_);

            // Evaluate decision 'SimpleChild'
            child.type.ChildPerson output_ = lambda.apply(context_);

            // End decision 'SimpleChild'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, simpleChildArguments_, output_, (System.currentTimeMillis() - simpleChildStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'SimpleChild' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<child.type.ChildPerson> lambda =
        new com.gs.dmn.runtime.LambdaExpression<child.type.ChildPerson>() {
            public child.type.ChildPerson apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return child.type.ChildPerson.toChildPerson(new com.gs.dmn.runtime.Context().add("age", number("4")).add("name", "John").add("addresses", asList()));
            }
        };
}
