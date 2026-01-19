
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "fn invocation complex parameters"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fn invocation complex parameters",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnInvocationComplexParameters(val fnLibrary : FnLibrary = FnLibrary()) : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<type.TFnInvocationComplexParamsResult?>() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationComplexParamsResult? {
        try {
            return apply(input_.get("inputA")?.let({ number(it) }), input_.get("inputB")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e)
            return null
        }
    }

    fun apply(inputA: kotlin.Number?, inputB: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationComplexParamsResult? {
        try {
            // Start decision 'fn invocation complex parameters'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val fnInvocationComplexParametersStartTime_ = System.currentTimeMillis()
            val fnInvocationComplexParametersArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fnInvocationComplexParametersArguments_.put("inputA", inputA)
            fnInvocationComplexParametersArguments_.put("inputB", inputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_)

            // Evaluate decision 'fn invocation complex parameters'
            val output_: type.TFnInvocationComplexParamsResult? = evaluate(inputA, inputB, context_)

            // End decision 'fn invocation complex parameters'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationComplexParametersStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'fn invocation complex parameters' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(inputA: kotlin.Number?, inputB: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationComplexParamsResult? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val fnLibrary: type.TFnLibrary? = this@FnInvocationComplexParameters.fnLibrary.apply(context_)

        val functionInvocationInParameter: kotlin.Number? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(inputA, inputA, context_), fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(inputB, inputB, context_), context_) as kotlin.Number?
        val functionInvocationLiteralExpressionInParameter: kotlin.Number? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(numericMultiply(inputA, inputA), (if (booleanEqual(booleanAnd(numericGreaterEqualThan(fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(inputA, inputB, context_), number("0")), numericLessEqualThan(fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? })?.apply(inputA, inputB, context_), number("10"))), true)) number("5") else number("10")), context_) as kotlin.Number?
        val circumference: kotlin.Number? = Circumference.instance()?.apply(numericAdd(inputA, inputB), context_) as kotlin.Number?
        val fnInvocationComplexParameters: type.TFnInvocationComplexParamsResultImpl? = type.TFnInvocationComplexParamsResultImpl() as type.TFnInvocationComplexParamsResultImpl?
        fnInvocationComplexParameters?.functionInvocationInParameter = functionInvocationInParameter
        fnInvocationComplexParameters?.functionInvocationLiteralExpressionInParameter = functionInvocationLiteralExpressionInParameter
        fnInvocationComplexParameters?.circumference = circumference
        return fnInvocationComplexParameters
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "fn invocation complex parameters",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
