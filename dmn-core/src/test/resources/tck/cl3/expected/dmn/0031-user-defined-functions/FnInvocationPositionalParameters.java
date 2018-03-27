
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "fnInvocationPositionalParameters"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fnInvocationPositionalParameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationPositionalParameters extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fnInvocationPositionalParameters",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final FnLibrary fnLibrary;

    public FnInvocationPositionalParameters() {
        this(new FnLibrary());
    }

    public FnInvocationPositionalParameters(FnLibrary fnLibrary) {
        this.fnLibrary = fnLibrary;
    }

    public type.TFnInvocationPositionalResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationPositionalResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationPositionalResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputA, inputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TFnInvocationPositionalResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'fnInvocationPositionalParameters'
            long fnInvocationPositionalParametersStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments fnInvocationPositionalParametersArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            fnInvocationPositionalParametersArguments_.put("inputA", inputA);
            fnInvocationPositionalParametersArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_);

            // Apply child decisions
            type.TFnLibrary fnLibrary = this.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate decision 'fnInvocationPositionalParameters'
            type.TFnInvocationPositionalResult output_ = evaluate(fnLibrary, inputA, inputB, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'fnInvocationPositionalParameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationPositionalParametersStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fnInvocationPositionalParameters' evaluation", e);
            return null;
        }
    }

    private type.TFnInvocationPositionalResult evaluate(type.TFnLibrary fnLibrary, java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        java.math.BigDecimal sumResult = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getSumFn() : null)).apply(inputA, inputB);
        java.math.BigDecimal multiplicationResultPositional = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getMultiplyFn() : null)).apply(inputA, inputB);
        java.math.BigDecimal divisionResultPositional = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)(fnLibrary != null ? fnLibrary.getDivideFn() : null)).apply(inputA, inputB);
        type.TFnInvocationPositionalResultImpl fnInvocationPositionalParameters = new type.TFnInvocationPositionalResultImpl();
        fnInvocationPositionalParameters.setSumResult(sumResult);
        fnInvocationPositionalParameters.setMultiplicationResultPositional(multiplicationResultPositional);
        fnInvocationPositionalParameters.setDivisionResultPositional(divisionResultPositional);
        return fnInvocationPositionalParameters;
    }
}
