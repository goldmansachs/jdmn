
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["signavio-decision.ftl", "assessIssue"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "assessIssue",
    label = "Assess issue",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class AssessIssue() : com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision() {
    fun apply(currentRiskAppetite: String?, priorIssue_iterator: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(currentRiskAppetite?.let({ number(it) }), priorIssue_iterator?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssue'", e)
            null
        }
    }

    fun apply(currentRiskAppetite: String?, priorIssue_iterator: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        return try {
            apply(currentRiskAppetite?.let({ number(it) }), priorIssue_iterator?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'AssessIssue'", e)
            null
        }
    }

    fun apply(currentRiskAppetite: java.math.BigDecimal?, priorIssue_iterator: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(currentRiskAppetite, priorIssue_iterator, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
    }

    fun apply(currentRiskAppetite: java.math.BigDecimal?, priorIssue_iterator: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        try {
            // Start decision 'assessIssue'
            val assessIssueStartTime_ = System.currentTimeMillis()
            val assessIssueArguments_ = com.gs.dmn.runtime.listener.Arguments()
            assessIssueArguments_.put("Current risk appetite", currentRiskAppetite);
            assessIssueArguments_.put("Prior issue", priorIssue_iterator);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_)

            // Evaluate decision 'assessIssue'
            val output_: java.math.BigDecimal? = evaluate(currentRiskAppetite, priorIssue_iterator, annotationSet_, eventListener_, externalExecutor_)

            // End decision 'assessIssue'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, assessIssueArguments_, output_, (System.currentTimeMillis() - assessIssueStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'assessIssue' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(currentRiskAppetite: java.math.BigDecimal?, priorIssue_iterator: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        return numericMultiply(priorIssue_iterator, numericMultiply(max(asList(number("0"), numericSubtract(number("100"), currentRiskAppetite))), number("0.01"))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "assessIssue",
            "Assess issue",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
