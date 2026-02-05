
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "fn library"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fn library",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnLibrary extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision<type.TFnLibrary> {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fn library",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public FnLibrary() {
    }

    @java.lang.Override()
    public type.TFnLibrary applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnLibrary'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TFnLibrary applyPojo(com.gs.dmn.runtime.ExecutableDRGElementInput input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FnLibrary'", e);
            return null;
        }
    }

    @java.lang.Override()
    public type.TFnLibrary applyContext(com.gs.dmn.runtime.Context input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return applyPojo(new FnLibraryInput_(input_), context_);
        } catch (Exception e) {
            logError("Cannot apply element 'FnLibrary'", e);
            return null;
        }
    }

    public type.TFnLibrary apply(com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'fn library'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long fnLibraryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnLibraryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_);

            // Evaluate decision 'fn library'
            type.TFnLibrary output_ = lambda.apply(context_);

            // End decision 'fn library'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_, output_, (System.currentTimeMillis() - fnLibraryStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fn library' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnLibrary> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnLibrary>() {
            public type.TFnLibrary apply(Object... args_) {
                com.gs.dmn.runtime.ExecutionContext context_ = 0 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[0] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                com.gs.dmn.runtime.LambdaExpression<java.lang.Number> sumFn = new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {public java.lang.Number apply(Object... args_) {java.lang.Number a = (java.lang.Number)args_[0]; java.lang.Number b = (java.lang.Number)args_[1];return numericAdd(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<java.lang.Number> subFn = new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {public java.lang.Number apply(Object... args_) {java.lang.Number a = (java.lang.Number)args_[0]; java.lang.Number b = (java.lang.Number)args_[1];return numericSubtract(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<java.lang.Number> multiplyFn = new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {public java.lang.Number apply(Object... args_) {java.lang.Number a = (java.lang.Number)args_[0]; java.lang.Number b = (java.lang.Number)args_[1];return numericMultiply(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<java.lang.Number> divideFn = new com.gs.dmn.runtime.LambdaExpression<java.lang.Number>() {public java.lang.Number apply(Object... args_) {java.lang.Number a = (java.lang.Number)args_[0]; java.lang.Number b = (java.lang.Number)args_[1];return (booleanEqual(numericEqual(b, number("0")), Boolean.TRUE)) ? null : numericDivide(a, b);}};
                type.TFnLibraryImpl fnLibrary = new type.TFnLibraryImpl();
                fnLibrary.setSumFn(sumFn);
                fnLibrary.setSubFn(subFn);
                fnLibrary.setMultiplyFn(multiplyFn);
                fnLibrary.setDivideFn(divideFn);
                return fnLibrary;
            }
        };
}
