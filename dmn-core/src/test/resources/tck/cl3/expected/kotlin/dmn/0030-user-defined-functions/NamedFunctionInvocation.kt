
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "namedFunctionInvocation"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "namedFunctionInvocation",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class NamedFunctionInvocation() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(stringInputA: String?, stringInputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(stringInputA, stringInputB, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(stringInputA: String?, stringInputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'namedFunctionInvocation'
            val namedFunctionInvocationStartTime_ = System.currentTimeMillis()
            val namedFunctionInvocationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            namedFunctionInvocationArguments_.put("stringInputA", stringInputA)
            namedFunctionInvocationArguments_.put("stringInputB", stringInputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_)

            // Evaluate decision 'namedFunctionInvocation'
            val output_: String? = evaluate(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'namedFunctionInvocation'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_, output_, (System.currentTimeMillis() - namedFunctionInvocationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'namedFunctionInvocation' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(stringInputA: String?, stringInputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        val boxedFnDefinition: com.gs.dmn.runtime.LambdaExpression<String?>? = com.gs.dmn.runtime.LambdaExpression<String> { args -> val a: String? = args[0] as String?; val b: String? = args[1] as String?;stringAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<String?>?
        val literalFnDefinition: com.gs.dmn.runtime.LambdaExpression<String?>? = com.gs.dmn.runtime.LambdaExpression<String> { args -> val a: String? = args[0] as String?; val b: String? = args[1] as String?;stringAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<String?>?
        return stringAdd(boxedFnDefinition?.apply(stringInputB, stringInputA), literalFnDefinition?.apply(stringInputB, stringInputA))
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "namedFunctionInvocation",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
