
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "formattingAndCoercing"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "formattingAndCoercing",
    label = "formattingAndCoercing",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FormattingAndCoercing extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "formattingAndCoercing",
        "formattingAndCoercing",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public FormattingAndCoercing() {
    }

    @java.lang.Override()
    public java.lang.Number applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("numberB") != null ? number(input_.get("numberB")) : null), input_.get("string"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FormattingAndCoercing'", e);
            return null;
        }
    }

    public java.lang.Number apply(java.lang.Number numberB, String string, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'formattingAndCoercing'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long formattingAndCoercingStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments formattingAndCoercingArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            formattingAndCoercingArguments_.put("numberB", numberB);
            formattingAndCoercingArguments_.put("string", string);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, formattingAndCoercingArguments_);

            // Evaluate decision 'formattingAndCoercing'
            java.lang.Number output_ = evaluate(numberB, string, context_);

            // End decision 'formattingAndCoercing'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, formattingAndCoercingArguments_, output_, (System.currentTimeMillis() - formattingAndCoercingStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'formattingAndCoercing' evaluation", e);
            return null;
        }
    }

    protected java.lang.Number evaluate(java.lang.Number numberB, String string, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return number(text(numberB, string));
    }
}
