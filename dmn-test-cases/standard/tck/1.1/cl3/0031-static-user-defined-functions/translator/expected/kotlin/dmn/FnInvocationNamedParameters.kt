
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "'fn invocation named parameters'"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'fn invocation named parameters'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnInvocationNamedParameters(val fnLibrary : FnLibrary = FnLibrary()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(inputA: String?, inputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TFnInvocationNamedResult? {
        return try {
            apply(inputA?.let({ number(it) }), inputB?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e)
            null
        }
    }

    fun apply(inputA: String?, inputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationNamedResult? {
        return try {
            apply(inputA?.let({ number(it) }), inputB?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e)
            null
        }
    }

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TFnInvocationNamedResult? {
        return apply(inputA, inputB, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationNamedResult? {
        try {
            // Start decision ''fn invocation named parameters''
            val fnInvocationNamedParametersStartTime_ = System.currentTimeMillis()
            val fnInvocationNamedParametersArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fnInvocationNamedParametersArguments_.put("inputA", inputA)
            fnInvocationNamedParametersArguments_.put("inputB", inputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_)

            // Evaluate decision ''fn invocation named parameters''
            val output_: type.TFnInvocationNamedResult? = evaluate(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision ''fn invocation named parameters''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationNamedParametersStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in ''fn invocation named parameters'' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationNamedResult? {
        // Apply child decisions
        val fnLibrary: type.TFnLibrary? = this@FnInvocationNamedParameters.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        val subResult: java.math.BigDecimal? = fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val multiplicationResultNamed: java.math.BigDecimal? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val subResultMixed: java.math.BigDecimal? = fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputB, inputA, annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val divisionResultNamed: java.math.BigDecimal? = fnLibrary?.let({ it.divideFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val fnInvocationNamedParameters: type.TFnInvocationNamedResultImpl? = type.TFnInvocationNamedResultImpl() as type.TFnInvocationNamedResultImpl?
        fnInvocationNamedParameters?.subResult = subResult
        fnInvocationNamedParameters?.multiplicationResultNamed = multiplicationResultNamed
        fnInvocationNamedParameters?.subResultMixed = subResultMixed
        fnInvocationNamedParameters?.divisionResultNamed = divisionResultNamed
        return fnInvocationNamedParameters
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "'fn invocation named parameters'",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
