
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "fn library"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.actico.com/spec/DMN/0.1.0/0031-user-defined-functions",
    name = "fn library",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class FnLibrary() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<type.TFnLibrary?>() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): type.TFnLibrary? {
        try {
            return apply(context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'FnLibrary'", e)
            return null
        }
    }

    fun apply(context_: com.gs.dmn.runtime.ExecutionContext): type.TFnLibrary? {
        try {
            // Start decision 'fn library'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val fnLibraryStartTime_ = System.currentTimeMillis()
            val fnLibraryArguments_ = com.gs.dmn.runtime.listener.Arguments()
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_)

            // Evaluate decision 'fn library'
            val output_: type.TFnLibrary? = evaluate(context_)

            // End decision 'fn library'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, fnLibraryArguments_, output_, (System.currentTimeMillis() - fnLibraryStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'fn library' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(context_: com.gs.dmn.runtime.ExecutionContext): type.TFnLibrary? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        val sumFn: com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? = com.gs.dmn.runtime.LambdaExpression<kotlin.Number> { args_ -> val a: kotlin.Number? = args_[0] as kotlin.Number?; val b: kotlin.Number? = args_[1] as kotlin.Number?; numericAdd(a, b) } as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>?
        val subFn: com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? = com.gs.dmn.runtime.LambdaExpression<kotlin.Number> { args_ -> val a: kotlin.Number? = args_[0] as kotlin.Number?; val b: kotlin.Number? = args_[1] as kotlin.Number?; numericSubtract(a, b) } as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>?
        val multiplyFn: com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? = com.gs.dmn.runtime.LambdaExpression<kotlin.Number> { args_ -> val a: kotlin.Number? = args_[0] as kotlin.Number?; val b: kotlin.Number? = args_[1] as kotlin.Number?; numericMultiply(a, b) } as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>?
        val divideFn: com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>? = com.gs.dmn.runtime.LambdaExpression<kotlin.Number> { args_ -> val a: kotlin.Number? = args_[0] as kotlin.Number?; val b: kotlin.Number? = args_[1] as kotlin.Number?; (if (booleanEqual(numericEqual(b, number("0")), true)) null else numericDivide(a, b)) } as com.gs.dmn.runtime.LambdaExpression<kotlin.Number?>?
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
            "fn library",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.CONTEXT,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
