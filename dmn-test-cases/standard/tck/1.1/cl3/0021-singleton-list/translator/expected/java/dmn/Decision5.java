
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision5"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision5",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision5 extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision5",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision5() {
    }

    @java.lang.Override()
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("Employees") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("Employees"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision5'", e);
            return null;
        }
    }

    @java.lang.Override()
    public String applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(((Decision5Input_)input_).getEmployees(), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'Decision5'", e);
            return null;
        }
    }

    public String apply(List<String> employees, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision5'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decision5StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision5Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decision5Arguments_.put("Employees", employees);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision5Arguments_);

            // Evaluate decision 'decision5'
            String output_ = lambda.apply(employees, context_);

            // End decision 'decision5'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision5Arguments_, output_, (System.currentTimeMillis() - decision5StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision5' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                List<String> employees = 0 < args_.length ? (List<String>) args_[0] : null;
                com.gs.dmn.runtime.ExecutionContext context_ = 1 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[1] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return upperCase(asElement(employees.stream().filter(item -> stringEqual(item, "Bob") == Boolean.TRUE).collect(Collectors.toList())));
            }
        };
}
