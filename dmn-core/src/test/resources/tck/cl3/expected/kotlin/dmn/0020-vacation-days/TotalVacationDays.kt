
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "TotalVacationDays"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "TotalVacationDays",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class TotalVacationDays(val baseVacationDays : BaseVacationDays = BaseVacationDays(), val extraDaysCase1 : ExtraDaysCase1 = ExtraDaysCase1(), val extraDaysCase2 : ExtraDaysCase2 = ExtraDaysCase2(), val extraDaysCase3 : ExtraDaysCase3 = ExtraDaysCase3()) : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(age: String?, yearsOfService: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(age?.let({ number(it) }), yearsOfService?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'TotalVacationDays'", e)
            null
        }
    }

    fun apply(age: String?, yearsOfService: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(age?.let({ number(it) }), yearsOfService?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'TotalVacationDays'", e)
            null
        }
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(age, yearsOfService, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(age: java.math.BigDecimal?, yearsOfService: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'TotalVacationDays'
            val totalVacationDaysStartTime_ = System.currentTimeMillis()
            val totalVacationDaysArguments_ = com.gs.dmn.runtime.listener.Arguments()
            totalVacationDaysArguments_.put("Age", age)
            totalVacationDaysArguments_.put("YearsOfService", yearsOfService)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_)

            // Apply child decisions
            val baseVacationDays: java.math.BigDecimal? = this.baseVacationDays.apply(annotationSet_, eventListener_, externalExecutor_, cache_)
            val extraDaysCase1: java.math.BigDecimal? = this.extraDaysCase1.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_)
            val extraDaysCase2: java.math.BigDecimal? = this.extraDaysCase2.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_)
            val extraDaysCase3: java.math.BigDecimal? = this.extraDaysCase3.apply(age, yearsOfService, annotationSet_, eventListener_, externalExecutor_, cache_)

            // Evaluate decision 'TotalVacationDays'
            val output_: java.math.BigDecimal? = evaluate(baseVacationDays, extraDaysCase1, extraDaysCase2, extraDaysCase3, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'TotalVacationDays'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, totalVacationDaysArguments_, output_, (System.currentTimeMillis() - totalVacationDaysStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'TotalVacationDays' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(baseVacationDays: java.math.BigDecimal?, extraDaysCase1: java.math.BigDecimal?, extraDaysCase2: java.math.BigDecimal?, extraDaysCase3: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return numericAdd(numericAdd(baseVacationDays, max(extraDaysCase1, extraDaysCase3)), extraDaysCase2) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "TotalVacationDays",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
