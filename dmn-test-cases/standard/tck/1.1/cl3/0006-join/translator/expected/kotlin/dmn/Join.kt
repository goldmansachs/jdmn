
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Join"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "http://www.trisotech.com/definitions/_16bf03c7-8f3d-46d0-a921-6e335ccc7e29",
    name = "Join",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Join() : com.gs.dmn.runtime.JavaTimeDMNBaseDecision<String?>() {
    override fun applyMap(input_: MutableMap<String, String>, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            return apply(input_.get("DeptTable")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TDeptTable?>?>() {}) }), input_.get("EmployeeTable")?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TEmployeeTable?>?>() {}) }), input_.get("LastName"), context_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Join'", e)
            return null
        }
    }

    fun apply(deptTable: List<type.TDeptTable?>?, employeeTable: List<type.TEmployeeTable?>?, lastName: String?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        try {
            // Start decision 'Join'
            var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
            var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
            var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
            var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
            val joinStartTime_ = System.currentTimeMillis()
            val joinArguments_ = com.gs.dmn.runtime.listener.Arguments()
            joinArguments_.put("DeptTable", deptTable)
            joinArguments_.put("EmployeeTable", employeeTable)
            joinArguments_.put("LastName", lastName)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, joinArguments_)

            // Evaluate decision 'Join'
            val output_: String? = evaluate(deptTable, employeeTable, lastName, context_)

            // End decision 'Join'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, joinArguments_, output_, (System.currentTimeMillis() - joinStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Join' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(deptTable: List<type.TDeptTable?>?, employeeTable: List<type.TEmployeeTable?>?, lastName: String?, context_: com.gs.dmn.runtime.ExecutionContext): String? {
        var annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet = context_.getAnnotations()
        var eventListener_: com.gs.dmn.runtime.listener.EventListener = context_.getEventListener()
        var externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor = context_.getExternalFunctionExecutor()
        var cache_: com.gs.dmn.runtime.cache.Cache = context_.getCache()
        return (elementAt(deptTable?.filter({ item -> numericEqual(item?.let({ it.number as kotlin.Number? }), (elementAt(employeeTable?.filter({ item_1_ -> stringEqual(item_1_?.let({ it.name as String? }), lastName) })?.map({ x_ -> x_?.let({ it.deptNum as kotlin.Number? }) }), number("1")) as kotlin.Number?)) })?.map({ x_ -> x_?.let({ it.manager as String? }) }), number("1")) as String?) as String?
    }

    companion object {
        val DRG_ELEMENT_METADATA : com.gs.dmn.runtime.listener.DRGElement = com.gs.dmn.runtime.listener.DRGElement(
            "",
            "Join",
            "",
            com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
            com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
            com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
            -1
        )
    }
}
