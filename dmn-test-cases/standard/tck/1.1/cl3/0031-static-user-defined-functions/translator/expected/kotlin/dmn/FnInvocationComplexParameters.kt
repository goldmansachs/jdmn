
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "'fn invocation complex parameters'"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'fn invocation complex parameters'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnInvocationComplexParameters(val fnLibrary : FnLibrary = FnLibrary()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun apply(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnInvocationComplexParamsResult? {
        try {
            return apply(input_.get("inputA"), input_.get("inputB"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnInvocationComplexParameters'", e)
            return null
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

    fun apply(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationComplexParamsResult? {
        try {
            // Start decision ''fn invocation complex parameters''
            val fnInvocationComplexParametersStartTime_ = System.currentTimeMillis()
            val fnInvocationComplexParametersArguments_ = com.gs.dmn.runtime.listener.Arguments()
            fnInvocationComplexParametersArguments_.put("inputA", inputA)
            fnInvocationComplexParametersArguments_.put("inputB", inputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_)

            // Evaluate decision ''fn invocation complex parameters''
            val output_: type.TFnInvocationComplexParamsResult? = evaluate(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision ''fn invocation complex parameters''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnInvocationComplexParametersArguments_, output_, (System.currentTimeMillis() - fnInvocationComplexParametersStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in ''fn invocation complex parameters'' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(inputA: java.math.BigDecimal?, inputB: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnInvocationComplexParamsResult? {
        // Apply child decisions
        val fnLibrary: type.TFnLibrary? = this@FnInvocationComplexParameters.fnLibrary.apply(annotationSet_, eventListener_, externalExecutor_, cache_)

        val functionInvocationInParameter: java.math.BigDecimal? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputA, annotationSet_, eventListener_, externalExecutor_, cache_), fnLibrary?.let({ it.sumFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputB, inputB, annotationSet_, eventListener_, externalExecutor_, cache_), annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val functionInvocationLiteralExpressionInParameter: java.math.BigDecimal? = fnLibrary?.let({ it.multiplyFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(numericMultiply(inputA, inputA), (if (booleanEqual((booleanAnd(numericGreaterEqualThan(fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_), number("0")), numericLessEqualThan(fnLibrary?.let({ it.subFn as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? })?.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_), number("10")))), true)) number("5") else number("10")), annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val circumference: java.math.BigDecimal? = Circumference.instance()?.apply(numericAdd(inputA, inputB), annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
        val fnInvocationComplexParameters: type.TFnInvocationComplexParamsResultImpl? = type.TFnInvocationComplexParamsResultImpl() as type.TFnInvocationComplexParamsResultImpl?
        fnInvocationComplexParameters?.functionInvocationInParameter = functionInvocationInParameter
        fnInvocationComplexParameters?.functionInvocationLiteralExpressionInParameter = functionInvocationLiteralExpressionInParameter
        fnInvocationComplexParameters?.circumference = circumference
        return fnInvocationComplexParameters
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "'fn invocation complex parameters'",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
