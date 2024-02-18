
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "Total Vacation Days"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Total Vacation Days",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class TotalVacationDays(val baseVacationDays : BaseVacationDays = BaseVacationDays(), val extraDaysCase1 : ExtraDaysCase1 = ExtraDaysCase1(), val extraDaysCase2 : ExtraDaysCase2 = ExtraDaysCase2(), val extraDaysCase3 : ExtraDaysCase3 = ExtraDaysCase3()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("Age")?.let({ number(it) }), input_.get("Years of Service")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'TotalVacationDays'", e)
            return null
        }
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start decision 'Total Vacation Days'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val totalVacationDaysStartTime_ = System.currentTimeMillis()
            val totalVacationDaysArguments_ = com.gs.dmn.runtime.listener.Arguments()
            totalVacationDaysArguments_.put("Age", age)
            totalVacationDaysArguments_.put("Years of Service", yearsOfService)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_)

            // Evaluate decision 'Total Vacation Days'
            val output_: java.math.BigDecimal? = evaluate(age, yearsOfService, context_)

            // End decision 'Total Vacation Days'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_, output_, (System.currentTimeMillis() - totalVacationDaysStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Total Vacation Days' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        // Apply child decisions
        val baseVacationDays: java.math.BigDecimal? = this@TotalVacationDays.baseVacationDays.apply(context_)
        val extraDaysCase1: java.math.BigDecimal? = this@TotalVacationDays.extraDaysCase1.apply(age, yearsOfService, context_)
        val extraDaysCase2: java.math.BigDecimal? = this@TotalVacationDays.extraDaysCase2.apply(age, yearsOfService, context_)
        val extraDaysCase3: java.math.BigDecimal? = this@TotalVacationDays.extraDaysCase3.apply(age, yearsOfService, context_)

        return numericAdd(numericAdd(baseVacationDays, max(extraDaysCase1, extraDaysCase3)), extraDaysCase2) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Total Vacation Days",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
