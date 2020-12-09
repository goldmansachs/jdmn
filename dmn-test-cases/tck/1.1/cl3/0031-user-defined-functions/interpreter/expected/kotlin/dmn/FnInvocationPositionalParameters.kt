
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "fnInvocationPositionalParameters"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fnInvocationPositionalParameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnInvocationPositionalParameters(val fnLibrary : FnLibrary = FnLibrary()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(inputA: String?, inputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TFnInvocationPositionalResult? {
        return try {
            apply(inputA?.let({ number(it) }), inputB?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e)
            null
        }
    }

    fun apply(inputA: String?, inputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationPositionalResult? {
        return try {
            apply(inputA?.let({ number(it) }), inputB?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e)
            null
        }
    }

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TFnInvocationPositionalResult? {
        return apply(inputA, inputB, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationPositionalResult? {
        try {
            // Start decision 'fnInvocationPositionalParameters'
            val fnInvocationPositionalParametersStartTime_ = System.currentTimeMillis()
            val fnInvocationPositionalParametersArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fnInvocationPositionalParametersArguments_.put("inputA", inputA)
            fnInvocationPositionalParametersArguments_.put("inputB", inputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_)

            // Apply child decisions
            val fnLibrary: type.TFnLibrary? = this.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'fnInvocationPositionalParameters'
            val output_: type.TFnInvocationPositionalResult? = evaluate(fnLibrary, inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'fnInvocationPositionalParameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationPositionalParametersStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'fnInvocationPositionalParameters' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(fnLibrary: type.TFnLibrary?, inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationPositionalResult? {
        val sumResult: java.math.BigDecimal? = fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB) as java.math.BigDecimal?
        val multiplicationResultPositional: java.math.BigDecimal? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB) as java.math.BigDecimal?
        val divisionResultPositional: java.math.BigDecimal? = fnLibrary?.let({ it.divideFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB) as java.math.BigDecimal?
        val fnInvocationPositionalParameters: type.TFnInvocationPositionalResultImpl? = type.TFnInvocationPositionalResultImpl() as type.TFnInvocationPositionalResultImpl?
        fnInvocationPositionalParameters?.sumResult = sumResult
        fnInvocationPositionalParameters?.multiplicationResultPositional = multiplicationResultPositional
        fnInvocationPositionalParameters?.divisionResultPositional = divisionResultPositional
        return fnInvocationPositionalParameters
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "fnInvocationPositionalParameters",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
