
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "'fn library'"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'fn library'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnLibrary extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "'fn library'",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public FnLibrary() {
    }

    public type.TFnLibrary apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision ''fn library''
            long fnLibraryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnLibraryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_);

            // Evaluate decision ''fn library''
            type.TFnLibrary output_ = lambda.apply(annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision ''fn library''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_, output_, (System.currentTimeMillis() - fnLibraryStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in ''fn library'' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<type.TFnLibrary> lambda =
        new com.gs.dmn.runtime.LambdaExpression<type.TFnLibrary>() {
            public type.TFnLibrary apply(Object... args_) {
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 0 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[0] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 1 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[1] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 2 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[2] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 3 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[3] : null;

                com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> sumFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0]; java.math.BigDecimal b = (java.math.BigDecimal)args_[1];return numericAdd(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> subFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0]; java.math.BigDecimal b = (java.math.BigDecimal)args_[1];return numericSubtract(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> multiplyFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0]; java.math.BigDecimal b = (java.math.BigDecimal)args_[1];return numericMultiply(a, b);}};
                com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> divideFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args_) {java.math.BigDecimal a = (java.math.BigDecimal)args_[0]; java.math.BigDecimal b = (java.math.BigDecimal)args_[1];return (booleanEqual(numericEqual(b, number("0")), Boolean.TRUE)) ? null : numericDivide(a, b);}};
                type.TFnLibraryImpl fnLibrary = new type.TFnLibraryImpl();
                fnLibrary.setSumFn(sumFn);
                fnLibrary.setSubFn(subFn);
                fnLibrary.setMultiplyFn(multiplyFn);
                fnLibrary.setDivideFn(divideFn);
                return fnLibrary;
            }
        };
}
