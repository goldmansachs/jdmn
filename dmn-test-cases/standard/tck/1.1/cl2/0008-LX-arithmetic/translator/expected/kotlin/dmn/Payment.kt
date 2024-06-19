
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
class Payment() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("loan")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TLoanImpl>() {}) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Payment'", e)
            return null
        }
    }

    fun apply(loan: type.TLoan?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start decision 'payment'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val paymentStartTime_ = System.currentTimeMillis()
            val paymentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            paymentArguments_.put("loan", loan)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, paymentArguments_)

            // Evaluate decision 'payment'
            val output_: java.math.BigDecimal? = evaluate(loan, context_)

            // End decision 'payment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, paymentArguments_, output_, (System.currentTimeMillis() - paymentStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'payment' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(loan: type.TLoan?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
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
