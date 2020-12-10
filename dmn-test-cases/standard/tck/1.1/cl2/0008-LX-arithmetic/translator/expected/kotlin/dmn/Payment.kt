
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "payment"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "payment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Payment() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(loan: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(loan?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TLoanImpl>() {}) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Payment'", e)
            null
        }
    }

    fun apply(loan: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return try {
            apply(loan?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TLoanImpl>() {}) }), annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Payment'", e)
            null
        }
    }

    fun apply(loan: type.TLoan?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(loan, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(loan: type.TLoan?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        try {
            // Start decision 'payment'
            val paymentStartTime_ = System.currentTimeMillis()
            val paymentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            paymentArguments_.put("loan", loan)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, paymentArguments_)

            // Evaluate decision 'payment'
            val output_: java.math.BigDecimal? = evaluate(loan, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'payment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, paymentArguments_, output_, (System.currentTimeMillis() - paymentStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'payment' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(loan: type.TLoan?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): java.math.BigDecimal? {
        return numericDivide(numericDivide(numericMultiply(loan?.let({ it.principal as java.math.BigDecimal? }), loan?.let({ it.rate as java.math.BigDecimal? })), number("12")), numericSubtract(number("1"), numericExponentiation(numericAdd(number("1"), numericDivide(loan?.let({ it.rate as java.math.BigDecimal? }), number("12"))), numericUnaryMinus(loan?.let({ it.termMonths as java.math.BigDecimal? }))))) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "payment",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
