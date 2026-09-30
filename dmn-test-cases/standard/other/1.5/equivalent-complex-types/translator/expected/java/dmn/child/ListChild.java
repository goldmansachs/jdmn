package child;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "ListChild"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "https://kie.org/dmn/_E41422F9-9D1F-4BAD-B81A-0A28390DF4F7",
    name = "ListChild",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class ListChild extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<child.type.ChildPerson>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "https://kie.org/dmn/_E41422F9-9D1F-4BAD-B81A-0A28390DF4F7",
        "ListChild",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public ListChild() {
    }

    @java.lang.Override()
    public List<child.type.ChildPerson> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'ListChild'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<child.type.ChildPerson> applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ListChild'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<child.type.ChildPerson> applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new ListChildInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'ListChild'", e);
            return null;
        }
    }

    public List<child.type.ChildPerson> apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'ListChild'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long listChildStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments listChildArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, listChildArguments_);

            // Evaluate decision 'ListChild'
            List<child.type.ChildPerson> output_ = lambda.apply(context_);

            // End decision 'ListChild'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, listChildArguments_, output_, (System.currentTimeMillis() - listChildStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'ListChild' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<child.type.ChildPerson>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<child.type.ChildPerson>>() {
            public List<child.type.ChildPerson> apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return asList(new com.gs.dmn.runtime.Context().add("age", number("4")).add("name", "John").add("addresses", asList())).stream().map(x_ -> child.type.ChildPerson.toChildPerson(x_)).collect(java.util.stream.Collectors.toList());
            }
        };
}
