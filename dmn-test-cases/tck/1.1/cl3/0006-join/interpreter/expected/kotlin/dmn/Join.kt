
import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "Join"])
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Join",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
class Join() : com.gs.dmn.runtime.DefaultDMNBaseDecision() {
    fun apply(deptTable: String?, employeeTable: String?, lastName: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return try {
            apply(deptTable?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TDeptTable?>?>() {}) }), employeeTable?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TEmployeeTable?>?>() {}) }), lastName, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
        } catch (e: Exception) {
            logError("Cannot apply decision 'Join'", e)
            null
        }
    }

    fun apply(deptTable: String?, employeeTable: String?, lastName: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return try {
            apply(deptTable?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TDeptTable?>?>() {}) }), employeeTable?.let({ com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(it, object : com.fasterxml.jackson.core.type.TypeReference<List<type.TEmployeeTable?>?>() {}) }), lastName, annotationSet_, eventListener_, externalExecutor_, cache_)
        } catch (e: Exception) {
            logError("Cannot apply decision 'Join'", e)
            null
        }
    }

    fun apply(deptTable: List<type.TDeptTable?>?, employeeTable: List<type.TEmployeeTable?>?, lastName: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet): String? {
        return apply(deptTable, employeeTable, lastName, annotationSet_, com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), com.gs.dmn.runtime.cache.DefaultCache())
    }

    fun apply(deptTable: List<type.TDeptTable?>?, employeeTable: List<type.TEmployeeTable?>?, lastName: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        try {
            // Start decision 'Join'
            val joinStartTime_ = System.currentTimeMillis()
            val joinArguments_ = com.gs.dmn.runtime.listener.Arguments()
            joinArguments_.put("DeptTable", deptTable)
            joinArguments_.put("EmployeeTable", employeeTable)
            joinArguments_.put("LastName", lastName)
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, joinArguments_)

            // Evaluate decision 'Join'
            val output_: String? = evaluate(deptTable, employeeTable, lastName, annotationSet_, eventListener_, externalExecutor_, cache_)

            // End decision 'Join'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, joinArguments_, output_, (System.currentTimeMillis() - joinStartTime_))

            return output_
        } catch (e: Exception) {
            logError("Exception caught in 'Join' evaluation", e)
            return null
        }
    }

    private inline fun evaluate(deptTable: List<type.TDeptTable?>?, employeeTable: List<type.TEmployeeTable?>?, lastName: String?, annotationSet_: com.gs.dmn.runtime.annotation.AnnotationSet, eventListener_: com.gs.dmn.runtime.listener.EventListener, externalExecutor_: com.gs.dmn.runtime.external.ExternalFunctionExecutor, cache_: com.gs.dmn.runtime.cache.Cache): String? {
        return (elementAt(deptTable?.filter({ item -> numericEqual(item?.let({ it.number as java.math.BigDecimal? }), (elementAt(employeeTable?.filter({ item_1_ -> stringEqual(item_1_?.let({ it.name as String? }), lastName) })?.map({ x -> x?.let({ it.deptNum as java.math.BigDecimal? }) }), number("1")) as java.math.BigDecimal?)) })?.map({ x -> x?.let({ it.manager as String? }) }), number("1")) as String?) as String?
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
