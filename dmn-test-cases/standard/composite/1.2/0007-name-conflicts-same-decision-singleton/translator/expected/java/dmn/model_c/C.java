package model_c;

import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "c"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "model_c",
    name = "c",
    label = "C",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class C extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
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
            return apply(input_.get("Model A.A"), input_.get("Model B.A"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'C'", e);
            return null;
        }
    }

    public String apply(String model_a_a, String model_b_a, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'c'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long cStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments cArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            cArguments_.put("Model A.A", model_a_a);
            cArguments_.put("Model B.A", model_b_a);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, cArguments_);

            // Evaluate decision 'c'
            String output_ = lambda.apply(model_a_a, model_b_a, context_);

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
                String model_a_a = 0 < args_.length ? (String) args_[0] : null;
                String model_b_a = 1 < args_.length ? (String) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return stringAdd(stringAdd(stringAdd("A: ", model_a_a), "; B: "), model_b_a);
            }
        };
}
