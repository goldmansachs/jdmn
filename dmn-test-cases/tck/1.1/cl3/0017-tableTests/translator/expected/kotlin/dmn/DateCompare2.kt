
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "dateCompare2"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "dateCompare2",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 2
)
class DateCompare2() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(dateD: String?, dateE: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return try {
            apply(dateD?.let({ date(it) }), dateE?.let({ date(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'DateCompare2'", e)
            null
        }
    }

    fun apply(dateD: String?, dateE: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        return try {
            apply(dateD?.let({ date(it) }), dateE?.let({ date(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'DateCompare2'", e)
            null
        }
    }

    fun apply(dateD: javax.xml.datatype.XMLGregorianCalendar?, dateE: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): Boolean? {
        return apply(dateD, dateE, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(dateD: javax.xml.datatype.XMLGregorianCalendar?, dateE: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        try {
            // Start decision 'dateCompare2'
            val dateCompare2StartTime_ = System.currentTimeMillis()
            val dateCompare2Arguments_ = com.gs.dmn.runtime.listener.Arguments()
            dateCompare2Arguments_.put("dateD", dateD)
            dateCompare2Arguments_.put("dateE", dateE)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, dateCompare2Arguments_)

            // Evaluate decision 'dateCompare2'
            val output_: Boolean? = evaluate(dateD, dateE, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'dateCompare2'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, dateCompare2Arguments_, output_, (System.currentTimeMillis() - dateCompare2StartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'dateCompare2' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(dateD: javax.xml.datatype.XMLGregorianCalendar?, dateE: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): Boolean? {
        // Apply rules and collect results
        val ruleOutputList_ = com.gs.dmn.runtime.RuleOutputList()
        ruleOutputList_.add(rule0(dateD, dateE, annotationSet_, eventListener_, externalExecutor_))
        ruleOutputList_.add(rule1(dateD, dateE, annotationSet_, eventListener_, externalExecutor_))

        // Return results based on hit policy
        var output_: Boolean?
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null
        } else {
            val ruleOutput_: com.gs.dmn.runtime.RuleOutput? = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE)
            output_ = ruleOutput_?.let({ (ruleOutput_ as DateCompare2RuleOutput).dateCompare2 })
        }

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    private fun rule0(dateD: javax.xml.datatype.XMLGregorianCalendar?, dateE: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(0, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: DateCompare2RuleOutput = DateCompare2RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateGreaterThan(dateD, dateE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.dateCompare2 = true

            // Add annotation
            annotationSet_.addAnnotation("dateCompare2", 0, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 1, annotation = "")
    private fun rule1(dateD: javax.xml.datatype.XMLGregorianCalendar?, dateE: javax.xml.datatype.XMLGregorianCalendar?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): com.gs.dmn.runtime.RuleOutput {
        // Rule metadata
        val drgRuleMetadata: com.gs.dmn.runtime.listener.Rule = com.gs.dmn.runtime.listener.Rule(1, "")

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

        // Apply rule
        var output_: DateCompare2RuleOutput = DateCompare2RuleOutput(false)
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (dateLessEqualThan(dateD, dateE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata)

            // Compute output
            output_.setMatched(true)
            output_.dateCompare2 = false

            // Add annotation
            annotationSet_.addAnnotation("dateCompare2", 1, "")
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_)

        return output_
    }


    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "dateCompare2",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
            com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
            2
        )
    }
}
