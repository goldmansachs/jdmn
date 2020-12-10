
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Bankrates"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Bankrates",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Bankrates() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): List<type.TLoanProduct?>? {
        return apply(annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.TLoanProduct?>? {
        try {
            // Start decision 'Bankrates'
            val bankratesStartTime_ = System.currentTimeMillis()
            val bankratesArguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, bankratesArguments_)

            // Evaluate decision 'Bankrates'
            val output_: List<type.TLoanProduct?>? = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'Bankrates'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, bankratesArguments_, output_, (System.currentTimeMillis() - bankratesStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Bankrates' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): List<type.TLoanProduct?>? {
        return asList(type.TLoanProductImpl(number("0"), "Oceans Capital", number("0"), number(".03500")),
				type.TLoanProductImpl(number("2700"), "eClick Lending", number("1.1"), number(".03200")),
				type.TLoanProductImpl(number("1200"), "eClickLending", number("0.1"), number(".03375")),
				type.TLoanProductImpl(number("3966"), "AimLoan", number("1.1"), number(".03000")),
				type.TLoanProductImpl(number("285"), "Home Loans Today", number("1.1"), number(".03125")),
				type.TLoanProductImpl(number("4028"), "Sebonic", number("0.1"), number(".03125")),
				type.TLoanProductImpl(number("4317"), "AimLoan", number("0.1"), number(".03125")),
				type.TLoanProductImpl(number("2518"), "eRates Mortgage", number("1.1"), number(".03125")),
				type.TLoanProductImpl(number("822"), "Home Loans Today", number("0.1"), number(".03250")),
				type.TLoanProductImpl(number("1995"), "AimLoan", number("0"), number(".03250"))) as List<type.TLoanProduct?>?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Bankrates",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.RELATION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
