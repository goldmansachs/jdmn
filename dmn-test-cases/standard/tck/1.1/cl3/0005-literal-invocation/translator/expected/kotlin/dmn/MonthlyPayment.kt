
import java.util.*
import java.util.stream.Collectors

@jakarta.annotation.Generated(value = ["decision.ftl", "MonthlyPayment"])
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
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            return apply(input_.get("Loan")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<type.TLoanImpl>() {}) }), input_.get("fee")?.let({ number(it) }), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'MonthlyPayment'", e)
            return null
        }
    }

    fun apply(loan: type.TLoan?, fee: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        try {
            // Start decision 'MonthlyPayment'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val monthlyPaymentStartTime_ = System.currentTimeMillis()
            val monthlyPaymentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            monthlyPaymentArguments_.put("Loan", loan)
            monthlyPaymentArguments_.put("fee", fee)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_)

            // Evaluate decision 'MonthlyPayment'
            val output_: java.math.BigDecimal? = evaluate(loan, fee, context_)

            // End decision 'MonthlyPayment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, monthlyPaymentArguments_, output_, (System.currentTimeMillis() - monthlyPaymentStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'MonthlyPayment' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(loan: type.TLoan?, fee: java.math.BigDecimal?, context_: com.gs.dmn.runtime.ExecutionContext): java.math.BigDecimal? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return numericAdd(PMT.instance()?.apply(loan?.let({ it.amount as java.math.BigDecimal? }), loan?.let({ it.rate as java.math.BigDecimal? }), loan?.let({ it.term as java.math.BigDecimal? }), context_), fee) as java.math.BigDecimal?
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
