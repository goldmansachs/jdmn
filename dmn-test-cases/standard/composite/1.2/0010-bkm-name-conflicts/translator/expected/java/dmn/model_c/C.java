package model_c;

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "c"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_c",
    name = "c",
    label = "C",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class C extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "model_c",
        "c",
        "C",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public C() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("AA") != null ? number(input_.get("AA")) : null), input_.get("BA"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'C'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((CInput_)input_).getAa(), ((CInput_)input_).getBa(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'C'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new CInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'C'", e);
            return null;
        }
    }

    public String apply(java.lang.Number aa, String ba, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'c'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long cStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments cArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            cArguments_.put("AA", aa);
            cArguments_.put("BA", ba);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, cArguments_);

            // Evaluate decision 'c'
            String output_ = lambda.apply(aa, ba, context_);

            // End decision 'c'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, cArguments_, output_, (System.currentTimeMillis() - cStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'c' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                java.lang.Number aa = 0 < args_.length ? (java.lang.Number) args_[0] : null;
                String ba = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return stringAdd(stringAdd(stringAdd("AA: ", model_a.Bkm.instance().apply(aa, context_)), "; BA: "), model_b.Bkm.instance().apply(ba, context_));
            }
        };
}
