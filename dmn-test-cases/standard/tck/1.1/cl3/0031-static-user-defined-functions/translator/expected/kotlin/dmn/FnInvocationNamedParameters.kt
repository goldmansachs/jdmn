
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "fn invocation named parameters"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fn invocation named parameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnInvocationNamedParameters(val fnLibrary : FnLibrary = FnLibrary()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationNamedResult? {
        try {
            return apply(input_.get("inputA")?.let({ number(it) }), input_.get("inputB")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationNamedParameters'", e)
            return null
        }
    }

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationNamedResult? {
        try {
            // Start decision 'fn invocation named parameters'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val fnInvocationNamedParametersStartTime_ = System.currentTimeMillis()
            val fnInvocationNamedParametersArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fnInvocationNamedParametersArguments_.put("inputA", inputA)
            fnInvocationNamedParametersArguments_.put("inputB", inputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_)

            // Evaluate decision 'fn invocation named parameters'
            val output_: type.TFnInvocationNamedResult? = evaluate(inputA, inputB, context_)

            // End decision 'fn invocation named parameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationNamedParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationNamedParametersStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'fn invocation named parameters' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationNamedResult? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val fnLibrary: type.TFnLibrary? = this@FnInvocationNamedParameters.fnLibrary.apply(context_)

        val subResult: java.math.BigDecimal? = fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, context_) as java.math.BigDecimal?
        val multiplicationResultNamed: java.math.BigDecimal? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, context_) as java.math.BigDecimal?
        val subResultMixed: java.math.BigDecimal? = fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputB, inputA, context_) as java.math.BigDecimal?
        val divisionResultNamed: java.math.BigDecimal? = fnLibrary?.let({ it.divideFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, context_) as java.math.BigDecimal?
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
            "fn invocation named parameters",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
