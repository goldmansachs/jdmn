
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "Join"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "Join",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Join extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "Join",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Join() {
    }

    public String apply(String deptTable, String employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((deptTable != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(deptTable, type.TDeptTableImpl[].class)) : null), (employeeTable != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employeeTable, type.TEmployeeTableImpl[].class)) : null), lastName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'Join'", e);
            return null;
        }
    }

    public String apply(String deptTable, String employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((deptTable != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(deptTable, type.TDeptTableImpl[].class)) : null), (employeeTable != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employeeTable, type.TEmployeeTableImpl[].class)) : null), lastName, annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Join'", e);
            return null;
        }
    }

    public String apply(List<type.TDeptTable> deptTable, List<type.TEmployeeTable> employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(deptTable, employeeTable, lastName, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public String apply(List<type.TDeptTable> deptTable, List<type.TEmployeeTable> employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Start decision 'Join'
            long joinStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments joinArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            joinArguments_.put("deptTable", deptTable);
            joinArguments_.put("employeeTable", employeeTable);
            joinArguments_.put("lastName", lastName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, joinArguments_);

            // Evaluate decision 'Join'
            String output_ = evaluate(deptTable, employeeTable, lastName, annotationSet_, eventListener_, externalExecutor_);

            // End decision 'Join'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, joinArguments_, output_, (System.currentTimeMillis() - joinStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Join' evaluation", e);
            return null;
        }
    }

    private String evaluate(List<type.TDeptTable> deptTable, List<type.TEmployeeTable> employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return (String)(elementAt(deptTable.stream().filter(item -> numericEqual(((java.math.BigDecimal)(item != null ? item.getNumber() : null)), (java.math.BigDecimal)(elementAt(employeeTable.stream().filter(item_1_ -> stringEqual(((String)(item_1_ != null ? item_1_.getName() : null)), lastName)).collect(Collectors.toList()).stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDeptNum() : null))).collect(Collectors.toList()), number("1"))))).collect(Collectors.toList()).stream().map(x -> ((String)(x != null ? x.getManager() : null))).collect(Collectors.toList()), number("1")));
    }
}
