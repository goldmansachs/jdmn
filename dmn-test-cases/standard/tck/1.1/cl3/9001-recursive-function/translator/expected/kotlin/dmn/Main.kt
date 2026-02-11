
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "main"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.gs.com/spec/DMN/9001-recursive-function",
    name = "main",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Main() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<kotlin.Number?>() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            return apply(input_.get("n")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Main'", e)
            return null
        }
    }

    fun apply(n: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        try {
            // Start decision 'main'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val mainStartTime_ = System.currentTimeMillis()
            val mainArguments_ = com.gs.dmn.runtime.listener.Arguments()
            mainArguments_.put("n", n)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, mainArguments_)

            // Evaluate decision 'main'
            val output_: kotlin.Number? = evaluate(n, context_)

            // End decision 'main'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, mainArguments_, output_, (System.currentTimeMillis() - mainStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'main' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(n: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): kotlin.Number? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return FACT.instance()?.apply(n, context_) as kotlin.Number?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "main",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
