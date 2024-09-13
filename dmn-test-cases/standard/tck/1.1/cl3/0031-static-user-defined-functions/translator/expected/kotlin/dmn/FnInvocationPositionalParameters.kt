
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "fn invocation positional parameters"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fn invocation positional parameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnInvocationPositionalParameters(val fnLibrary : FnLibrary = FnLibrary()) : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationPositionalResult? {
        try {
            return apply(input_.get("inputA")?.let({ number(it) }), input_.get("inputB")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationPositionalParameters'", e)
            return null
        }
    }

    fun apply(inputA: kotlin.Number?, inputB: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationPositionalResult? {
        try {
            // Start decision 'fn invocation positional parameters'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val fnInvocationPositionalParametersStartTime_ = System.currentTimeMillis()
            val fnInvocationPositionalParametersArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fnInvocationPositionalParametersArguments_.put("inputA", inputA)
            fnInvocationPositionalParametersArguments_.put("inputB", inputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_)

            // Evaluate decision 'fn invocation positional parameters'
            val output_: type.TFnInvocationPositionalResult? = evaluate(inputA, inputB, context_)

            // End decision 'fn invocation positional parameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationPositionalParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationPositionalParametersStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'fn invocation positional parameters' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(inputA: kotlin.Number?, inputB: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationPositionalResult? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val fnLibrary: type.TFnLibrary? = this@FnInvocationPositionalParameters.fnLibrary.apply(context_)

        val sumResult: kotlin.Number? = fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(inputA, inputB, context_) as kotlin.Number?
        val multiplicationResultPositional: kotlin.Number? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(inputA, inputB, context_) as kotlin.Number?
        val divisionResultPositional: kotlin.Number? = fnLibrary?.let({ it.divideFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(inputA, inputB, context_) as kotlin.Number?
        val fnInvocationPositionalParameters: type.TFnInvocationPositionalResultImpl? = type.TFnInvocationPositionalResultImpl() as type.TFnInvocationPositionalResultImpl?
        fnInvocationPositionalParameters?.sumResult = sumResult
        fnInvocationPositionalParameters?.multiplicationResultPositional = multiplicationResultPositional
        fnInvocationPositionalParameters?.divisionResultPositional = divisionResultPositional
        return fnInvocationPositionalParameters
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "fn invocation positional parameters",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
