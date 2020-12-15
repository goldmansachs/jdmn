
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "main"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "main",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Main() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(n: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(n?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Main'", e)
            null
        }
    }

    fun apply(n: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(n?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Main'", e)
            null
        }
    }

    fun apply(n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(n, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'main'
            val mainStartTime_ = System.currentTimeMillis()
            val mainArguments_ = com.gs.dmn.runtime.listener.Arguments()
            mainArguments_.put("n", n)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, mainArguments_)

            // Evaluate decision 'main'
            val output_: java.math.BigDecimal? = evaluate(n, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'main'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, mainArguments_, output_, (System.currentTimeMillis() - mainStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'main' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(n: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return FACT.FACT(n, annotationSet_, eventListener_, externalExecutor_, cache_) as java.math.BigDecimal?
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
