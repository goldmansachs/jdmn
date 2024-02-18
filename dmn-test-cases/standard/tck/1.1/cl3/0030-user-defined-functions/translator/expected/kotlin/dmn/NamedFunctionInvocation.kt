
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "named function invocation"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "named function invocation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class NamedFunctionInvocation() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("stringInputA"), input_.get("stringInputB"), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'NamedFunctionInvocation'", e)
            return null
        }
    }

    fun apply(stringInputA: String?, stringInputB: String?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            // Start decision 'named function invocation'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val namedFunctionInvocationStartTime_ = System.currentTimeMillis()
            val namedFunctionInvocationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            namedFunctionInvocationArguments_.put("stringInputA", stringInputA)
            namedFunctionInvocationArguments_.put("stringInputB", stringInputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_)

            // Evaluate decision 'named function invocation'
            val output_: String? = evaluate(stringInputA, stringInputB, context_)

            // End decision 'named function invocation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_, output_, (System.currentTimeMillis() - namedFunctionInvocationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'named function invocation' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(stringInputA: String?, stringInputB: String?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        val boxedFnDefinition: com.gs.dmn.runtime.LambdaExpression<String?>? = com.gs.dmn.runtime.LambdaExpression<String> { args_ -> val a: String? = args_[0] as String?; val b: String? = args_[1] as String?; stringAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<String?>?
        val literalFnDefinition: com.gs.dmn.runtime.LambdaExpression<String?>? = com.gs.dmn.runtime.LambdaExpression<String> { args_ -> val a: String? = args_[0] as String?; val b: String? = args_[1] as String?; stringAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<String?>?
        return stringAdd(boxedFnDefinition?.apply(stringInputB, stringInputA, context_), literalFnDefinition?.apply(stringInputB, stringInputA, context_))
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "named function invocation",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
