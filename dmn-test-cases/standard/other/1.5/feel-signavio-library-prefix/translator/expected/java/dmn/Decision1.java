
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Decision1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Decision1",
    label = "Decision1",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision1 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<Boolean> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Decision1",
        "Decision1",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision1() {
    }

    @java.lang.Override()
    public Boolean applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("InputString"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision1'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((Decision1Input_)input_).getInputString(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision1'", e);
            return null;
        }
    }

    @java.lang.Override()
    public Boolean applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new Decision1Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision1'", e);
            return null;
        }
    }

    public Boolean apply(String inputString, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Decision1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision1Arguments_.put("InputString", inputString);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision1Arguments_);

            // Evaluate decision 'Decision1'
            Boolean output_ = lambda.apply(inputString, context_);

            // End decision 'Decision1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision1Arguments_, output_, (System.currentTimeMillis() - decision1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Decision1' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Boolean> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Boolean>() {
            public Boolean apply(Object... args_) {
                String inputString = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.isAlpha(inputString);
            }
        };
}
