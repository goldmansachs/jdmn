
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["bkm.ftl", "gtTen"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "gtTen",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class GtTen : com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
    private constructor() {}

    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            return apply(input_.get("theNumber")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'GtTen'", e)
            return null
        }
    }

    fun apply(theNumber: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        try {
            // Start BKM 'gtTen'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val gtTenStartTime_ = System.currentTimeMillis()
            val gtTenArguments_ = com.gs.dmn.runtime.listener.Arguments()
            gtTenArguments_.put("theNumber", theNumber)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, gtTenArguments_)

            // Evaluate BKM 'gtTen'
            val output_: Boolean? = evaluate(theNumber, context_)

            // End BKM 'gtTen'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, gtTenArguments_, output_, (System.currentTimeMillis() - gtTenStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'gtTen' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(theNumber: kotlin.Number?, context_: com.gs.dmn.runtime.ExecutionContext): Boolean? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return numericGreaterThan(theNumber, number("10")) as Boolean?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "gtTen",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )

        private val INSTANCE = GtTen()

        @JvmStatic
        fun instance(): GtTen {
            return INSTANCE
        }
    }
}
