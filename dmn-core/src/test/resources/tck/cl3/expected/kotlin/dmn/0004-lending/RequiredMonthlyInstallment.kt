
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "RequiredMonthlyInstallment"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "RequiredMonthlyInstallment",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class RequiredMonthlyInstallment() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return try {
            apply(requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TRequestedProductImpl::class.java) }), annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
        } catch (e: Exception) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e)
            null
        }
    }

    fun apply(requestedProduct: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        return try {
            apply(requestedProduct?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, type.TRequestedProductImpl::class.java) }), annotationSet_, eventListener_, externalExecutor_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'RequiredMonthlyInstallment'", e)
            null
        }
    }

    fun apply(requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): java.math.BigDecimal? {
        return apply(requestedProduct, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor())
    }

    fun apply(requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        try {
            // Start decision 'RequiredMonthlyInstallment'
            val requiredMonthlyInstallmentStartTime_ = System.currentTimeMillis()
            val requiredMonthlyInstallmentArguments_ = com.gs.dmn.runtime.listener.Arguments()
            requiredMonthlyInstallmentArguments_.put("requestedProduct", requestedProduct)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_)

            // Evaluate decision 'RequiredMonthlyInstallment'
            val output_: java.math.BigDecimal? = evaluate(requestedProduct, annotationSet_, eventListener_, externalExecutor_)

            // End decision 'RequiredMonthlyInstallment'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, requiredMonthlyInstallmentArguments_, output_, (System.currentTimeMillis() - requiredMonthlyInstallmentStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'RequiredMonthlyInstallment' evaluation", e)
            return null
        }
    }

    private fun evaluate(requestedProduct: type.TRequestedProduct?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor): java.math.BigDecimal? {
        return InstallmentCalculation.InstallmentCalculation(requestedProduct?.let({ it.productType as String }), requestedProduct?.let({ it.rate as java.math.BigDecimal }), requestedProduct?.let({ it.term as java.math.BigDecimal }), requestedProduct?.let({ it.amount as java.math.BigDecimal }), annotationSet_, eventListener_, externalExecutor_) as java.math.BigDecimal?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "RequiredMonthlyInstallment",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
