
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "fnInvocationComplexParameters"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fnInvocationComplexParameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnInvocationComplexParameters(val fnLibrary : FnLibrary = FnLibrary()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(inputA: String?, inputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TFnInvocationComplexParamsResult? {
        return try {
            apply(inputA?.let({ number(it) }), inputB?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e)
            null
        }
    }

    fun apply(inputA: String?, inputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationComplexParamsResult? {
        return try {
            apply(inputA?.let({ number(it) }), inputB?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e)
            null
        }
    }

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TFnInvocationComplexParamsResult? {
        return apply(inputA, inputB, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationComplexParamsResult? {
        try {
            // Start decision 'fnInvocationComplexParameters'
            val fnInvocationComplexParametersStartTime_ = System.currentTimeMillis()
            val fnInvocationComplexParametersArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fnInvocationComplexParametersArguments_.put("inputA", inputA)
            fnInvocationComplexParametersArguments_.put("inputB", inputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_)

            // Apply child decisions
            val fnLibrary: type.TFnLibrary? = this.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'fnInvocationComplexParameters'
            val output_: type.TFnInvocationComplexParamsResult? = evaluate(fnLibrary, inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'fnInvocationComplexParameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationComplexParametersStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'fnInvocationComplexParameters' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(fnLibrary: type.TFnLibrary?, inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationComplexParamsResult? {
        val functionInvocationInParameter: java.math.BigDecimal? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputA), fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputB, inputB)) as java.math.BigDecimal?
        val functionInvocationLiteralExpressionInParameter: java.math.BigDecimal? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(numericMultiply(inputA, inputA), (if (booleanEqual((booleanAnd(numericGreaterEqualThan(fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB), number("0")), numericLessEqualThan(fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB), number("10")))), true)) number("5") else number("10"))) as java.math.BigDecimal?
        val circumference: java.math.BigDecimal? = Circumference.Circumference(numericAdd(inputA, inputB), annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val fnInvocationComplexParameters: type.TFnInvocationComplexParamsResultImpl? = type.TFnInvocationComplexParamsResultImpl() as type.TFnInvocationComplexParamsResultImpl?
        fnInvocationComplexParameters?.functionInvocationInParameter = functionInvocationInParameter
        fnInvocationComplexParameters?.functionInvocationLiteralExpressionInParameter = functionInvocationLiteralExpressionInParameter
        fnInvocationComplexParameters?.circumference = circumference
        return fnInvocationComplexParameters
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "fnInvocationComplexParameters",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
