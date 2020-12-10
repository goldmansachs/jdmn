
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "MonthlyPayment"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "MonthlyPayment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class MonthlyPayment() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(loan: String?, fee: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(loan?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TLoanImpl>() {}) }), fee?.let({ number(it) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'MonthlyPayment'", e)
            null
        }
    }

    fun apply(loan: String?, fee: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(loan?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TLoanImpl>() {}) }), fee?.let({ number(it) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'MonthlyPayment'", e)
            null
        }
    }

    fun apply(loan: type.TLoan?, fee: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(loan, fee, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(loan: type.TLoan?, fee: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'MonthlyPayment'
            val monthlyPaymentStartTime_ = System.currentTimeMillis()
            val monthlyPaymentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            monthlyPaymentArguments_.put("Loan", loan)
            monthlyPaymentArguments_.put("fee", fee)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_)

            // Evaluate decision 'MonthlyPayment'
            val output_: java.math.BigDecimal? = evaluate(loan, fee, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'MonthlyPayment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_, output_, (System.currentTimeMillis() - monthlyPaymentStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'MonthlyPayment' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(loan: type.TLoan?, fee: java.math.BigDecimal?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return numericAdd(PMT.PMT(loan?.let({ it.amount as java.math.BigDecimal? }), loan?.let({ it.rate as java.math.BigDecimal? }), loan?.let({ it.term as java.math.BigDecimal? }), annotationSet_, eventListener_, externalExecutor_, cache_), fee) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "MonthlyPayment",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
