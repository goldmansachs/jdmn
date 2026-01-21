
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "AppendAllTest"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "AppendAllTest",
    label = "AppendAllTest",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class AppendAllTest extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<List<String>> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "AppendAllTest",
        "AppendAllTest",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public AppendAllTest() {
    }

    @java.lang.Override()
    public List<String> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("InputDate") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("InputDate"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("InputDate") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("InputDate"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'AppendAllTest'", e);
            return null;
        }
    }

    @java.lang.Override()
    public List<String> applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((AppendAllTestInput_)input_).getList1(), ((AppendAllTestInput_)input_).getList2(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'AppendAllTest'", e);
            return null;
        }
    }

    public List<String> apply(List<String> list1, List<String> list2, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'AppendAllTest'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long appendAllTestStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments appendAllTestArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            appendAllTestArguments_.put("InputDate", list1);
            appendAllTestArguments_.put("InputDate", list2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, appendAllTestArguments_);

            // Evaluate decision 'AppendAllTest'
            List<String> output_ = lambda.apply(list1, list2, context_);

            // End decision 'AppendAllTest'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, appendAllTestArguments_, output_, (System.currentTimeMillis() - appendAllTestStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'AppendAllTest' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<List<String>> lambda =
        new com.gs.dmn.runtime.LambdaExpression<List<String>>() {
            public List<String> apply(Object... args_) {
                List<String> list1 = 0 < args_.length ? (List<String>) args_[0] : null;
                List<String> list2 = 1 < args_.length ? (List<String>) args_[1] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 2 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return com.gs.dmn.signavio.feel.lib.JavaTimeSignavioLib.INSTANCE.appendAll(list1, list2);
            }
        };
}
