
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "fnLibrary"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "fnLibrary",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnLibrary() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): type.TFnLibrary? {
        return apply(annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnLibrary? {
        try {
            // Start decision 'fnLibrary'
            val fnLibraryStartTime_ = System.currentTimeMillis()
            val fnLibraryArguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_)

            // Evaluate decision 'fnLibrary'
            val output_: type.TFnLibrary? = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'fnLibrary'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_, output_, (System.currentTimeMillis() - fnLibraryStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'fnLibrary' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): type.TFnLibrary? {
        val sumFn: com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? = com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> { args -> val a: java.math.BigDecimal? = args[0] as java.math.BigDecimal?; val b: java.math.BigDecimal? = args[1] as java.math.BigDecimal?;numericAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>?
        val subFn: com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? = com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> { args -> val a: java.math.BigDecimal? = args[0] as java.math.BigDecimal?; val b: java.math.BigDecimal? = args[1] as java.math.BigDecimal?;numericSubtract(a, b) } as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>?
        val multiplyFn: com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? = com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> { args -> val a: java.math.BigDecimal? = args[0] as java.math.BigDecimal?; val b: java.math.BigDecimal? = args[1] as java.math.BigDecimal?;numericMultiply(a, b) } as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>?
        val divideFn: com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>? = com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal> { args -> val a: java.math.BigDecimal? = args[0] as java.math.BigDecimal?; val b: java.math.BigDecimal? = args[1] as java.math.BigDecimal?;(if (booleanEqual(numericEqual(b, number("0")), true)) null else numericDivide(a, b)) } as com.gs.dmn.runtime.LambdaExpression<java.math.BigDecimal?>?
        val fnLibrary: type.TFnLibraryImpl? = type.TFnLibraryImpl() as type.TFnLibraryImpl?
        fnLibrary?.sumFn = sumFn
        fnLibrary?.subFn = subFn
        fnLibrary?.multiplyFn = multiplyFn
        fnLibrary?.divideFn = divideFn
        return fnLibrary
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "fnLibrary",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
