
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "fnInvocationNamedParameters"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fnInvocationNamedParameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class FnInvocationNamedParameters extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "fnInvocationNamedParameters",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );
    private final FnLibrary fnLibrary;

    public FnInvocationNamedParameters() {
        this(new FnLibrary());
    }

    public FnInvocationNamedParameters(FnLibrary fnLibrary) {
        this.fnLibrary = fnLibrary;
    }

    public type.TFnInvocationNamedResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationNamedResult apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((inputA != null ? number(inputA) : null), (inputB != null ? number(inputB) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e);
            return null;
        }
    }

    public type.TFnInvocationNamedResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputA, inputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public type.TFnInvocationNamedResult apply(java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arguments_.put("inputA", inputA);
            arguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments_);

            // Apply child decisions
            type.TFnLibrary fnLibraryOutput = fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_);

            // Evaluate expression
            type.TFnInvocationNamedResult output_ = evaluate(fnLibraryOutput, inputA, inputB, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'fnInvocationNamedParameters' evaluation", e);
            return null;
        }
    }

    private type.TFnInvocationNamedResult evaluate(type.TFnLibrary fnLibrary, java.math.BigDecimal inputA, java.math.BigDecimal inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        java.math.BigDecimal subResult = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)fnLibrary.getSubFn()).apply(inputA, inputB);
        java.math.BigDecimal multiplicationResultNamed = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)fnLibrary.getMultiplyFn()).apply(inputA, inputB);
        java.math.BigDecimal subResultMixed = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)fnLibrary.getSubFn()).apply(inputB, inputA);
        java.math.BigDecimal divisionResultNamed = ((com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal>)fnLibrary.getDivideFn()).apply(inputA, inputB);
        type.TFnInvocationNamedResultImpl fnInvocationNamedParameters = new type.TFnInvocationNamedResultImpl();
        fnInvocationNamedParameters.setSubResult(subResult);
        fnInvocationNamedParameters.setMultiplicationResultNamed(multiplicationResultNamed);
        fnInvocationNamedParameters.setSubResultMixed(subResultMixed);
        fnInvocationNamedParameters.setDivisionResultNamed(divisionResultNamed);
        return fnInvocationNamedParameters;
    }
}
