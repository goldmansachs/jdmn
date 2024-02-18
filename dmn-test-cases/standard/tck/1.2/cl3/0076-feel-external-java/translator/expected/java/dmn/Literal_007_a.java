
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"decision.ftl", "literal_007_a"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "literal_007_a",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Literal_007_a extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "literal_007_a",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Literal_007_a() {
    }

    @java.lang.Override()
    public Object applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Literal_007_a'", e);
            return null;
        }
    }

    public Object apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'literal_007_a'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long literal_007_aStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments literal_007_aArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, literal_007_aArguments_);

            // Evaluate decision 'literal_007_a'
            Object output_ = lambda.apply(context_);

            // End decision 'literal_007_a'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, literal_007_aArguments_, output_, (System.currentTimeMillis() - literal_007_aStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'literal_007_a' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<Object> lambda =
        new com.gs.dmn.runtime.LambdaExpression<Object>() {
            public Object apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                com.gs.dmn.runtime.external.JavaExternalFunction<Object> valueOf = new com.gs.dmn.runtime.external.JavaExternalFunction<>(new com.gs.dmn.runtime.external.JavaFunctionInfo("java.lang.String", "valueOf", Arrays.asList("char")), externalExecutor_, Object.class);
                return valueOf.apply("abc");
            }
        };
}
