
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "'named function invocation'"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "'named function invocation'",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class NamedFunctionInvocation() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(stringInputA: String?, stringInputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision ''named function invocation''
            val namedFunctionInvocationStartTime_ = System.currentTimeMillis()
            val namedFunctionInvocationArguments_ = com.gs.dmn.runtime.listener.Arguments()
            namedFunctionInvocationArguments_.put("stringInputA", stringInputA)
            namedFunctionInvocationArguments_.put("stringInputB", stringInputB)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_)

            // Evaluate decision ''named function invocation''
            val output_: String? = evaluate(stringInputA, stringInputB, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision ''named function invocation''
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, namedFunctionInvocationArguments_, output_, (System.currentTimeMillis() - namedFunctionInvocationStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in ''named function invocation'' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(stringInputA: String?, stringInputB: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        val boxedFnDefinition: com.gs.dmn.runtime.LambdaExpression<String?>? = com.gs.dmn.runtime.LambdaExpression<String> { args_ -> val a: String? = args_[0] as String?; val b: String? = args_[1] as String?;stringAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<String?>?
        val literalFnDefinition: com.gs.dmn.runtime.LambdaExpression<String?>? = com.gs.dmn.runtime.LambdaExpression<String> { args_ -> val a: String? = args_[0] as String?; val b: String? = args_[1] as String?;stringAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<String?>?
        return stringAdd(boxedFnDefinition?.apply(stringInputB, stringInputA, annotationSet_, eventListener_, externalExecutor_, cache_), literalFnDefinition?.apply(stringInputB, stringInputA, annotationSet_, eventListener_, externalExecutor_, cache_))
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "'named function invocation'",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
