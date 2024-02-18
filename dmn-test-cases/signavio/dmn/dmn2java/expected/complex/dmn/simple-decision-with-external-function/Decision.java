
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "decision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision",
    label = "Decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision",
        "Decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision() {
    }

    @java.lang.Override()
    public java.math.BigDecimal applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? number(input_.get("A")) : null), (input_.get("B") != null ? number(input_.get("B")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public java.math.BigDecimal apply(java.math.BigDecimal a, java.math.BigDecimal b, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'decision'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long decisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionArguments_.put("A", a);
            decisionArguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionArguments_);

            // Evaluate decision 'decision'
            java.math.BigDecimal output_ = evaluate(a, b, context_);

            // End decision 'decision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionArguments_, output_, (System.currentTimeMillis() - decisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision' evaluation", e);
            return null;
        }
    }

    protected java.math.BigDecimal evaluate(java.math.BigDecimal a, java.math.BigDecimal b, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return ((com.gs.dmn.runtime.external.JavaExternalFunction<java.math.BigDecimal>)((com.gs.dmn.runtime.Context)new com.gs.dmn.runtime.Context().add("f", new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("com.gs.dmn.generated.sd_with_external_function.Sum", "add", Arrays.asList("java.math.BigDecimal", "java.math.BigDecimal")), externalExecutor_, java.math.BigDecimal.class))).get("f")).apply(a, b);
    }
}
