
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_003"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.montera.com.au/spec/DMN/0085-decision-services",
    name = "decision_003",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_003 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_003",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Decision_003_input_1 decision_003_input_1;
    private final Decision_003_input_2 decision_003_input_2;

    public Decision_003() {
        this(new Decision_003_input_1(), new Decision_003_input_2());
    }

    public Decision_003(Decision_003_input_1 decision_003_input_1, Decision_003_input_2 decision_003_input_2) {
        this.decision_003_input_1 = decision_003_input_1;
        this.decision_003_input_2 = decision_003_input_2;
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("inputData_003"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision_003'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((Decision_003Input_)input_).getInputData_003(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision_003'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new Decision_003Input_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision_003'", e);
            return null;
        }
    }

    public String apply(String inputData_003, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision_003'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision_003StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_003Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision_003Arguments_.put("inputData_003", inputData_003);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_003Arguments_);

            // Evaluate decision 'decision_003'
            String output_ = lambda.apply(inputData_003, context_);

            // End decision 'decision_003'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_003Arguments_, output_, (System.currentTimeMillis() - decision_003StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision_003' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                String inputData_003 = 0 < args_.length ? (String) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                // Apply child decisions
                String decision_003_input_1 = Decision_003.this.decision_003_input_1.apply(context_);
                String decision_003_input_2 = Decision_003.this.decision_003_input_2.apply(context_);

                return stringAdd(stringAdd(stringAdd(stringAdd(stringAdd("A ", decision_003_input_1), " "), decision_003_input_2), " "), inputData_003);
            }
        };
}
