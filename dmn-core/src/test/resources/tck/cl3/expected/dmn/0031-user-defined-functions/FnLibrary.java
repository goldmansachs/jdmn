
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "fnLibrary"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fnLibrary",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnLibrary extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fnLibrary",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public FnLibrary() {
    }

    public type.TFnLibrary apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TFnLibrary apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'fnLibrary'
            long fnLibraryStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnLibraryArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_);

            // Evaluate decision 'fnLibrary'
            type.TFnLibrary output_ = evaluate(annotationSet_, eventListener_, externalExecutor_);

            // End decision 'fnLibrary'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_, output_, (System.currentTimeMillis() - fnLibraryStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fnLibrary' evaluation", e);
            return null;
        }
    }

    private type.TFnLibrary evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> sumFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args) {java.math.BigDecimal a = (java.math.BigDecimal)args[0]; java.math.BigDecimal b = (java.math.BigDecimal)args[1];return numericAdd(a, b);}};
        com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> subFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args) {java.math.BigDecimal a = (java.math.BigDecimal)args[0]; java.math.BigDecimal b = (java.math.BigDecimal)args[1];return numericSubtract(a, b);}};
        com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> multiplyFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args) {java.math.BigDecimal a = (java.math.BigDecimal)args[0]; java.math.BigDecimal b = (java.math.BigDecimal)args[1];return numericMultiply(a, b);}};
        com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> divideFn = new com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>() {public java.math.BigDecimal apply(Object... args) {java.math.BigDecimal a = (java.math.BigDecimal)args[0]; java.math.BigDecimal b = (java.math.BigDecimal)args[1];return (booleanEqual(numericEqual(b, number("0")), Boolean.TRUE)) ? null : numericDivide(a, b);}};
        type.TFnLibraryImpl fnLibrary = new type.TFnLibraryImpl();
        fnLibrary.setSumFn(sumFn);
        fnLibrary.setSubFn(subFn);
        fnLibrary.setMultiplyFn(multiplyFn);
        fnLibrary.setDivideFn(divideFn);
        return fnLibrary;
    }
}
