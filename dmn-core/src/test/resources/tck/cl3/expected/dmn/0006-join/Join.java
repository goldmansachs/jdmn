
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
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            arguments_.put("deptTable", deptTable);
            arguments_.put("employeeTable", employeeTable);
            arguments_.put("lastName", lastName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments_);

            // Evaluate expression
            String output_ = evaluate(deptTable, employeeTable, lastName, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments_, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Join' evaluation", e);
            return null;
        }
    }

    private String evaluate(List<type.TDeptTable> deptTable, List<type.TEmployeeTable> employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return (String)(elementAt(deptTable.stream().filter(item -> numericEqual(((java.math.BigDecimal)item.getNumber()), (java.math.BigDecimal)(elementAt(employeeTable.stream().filter(item_1_ -> stringEqual(((String)item_1_.getName()), lastName)).collect(Collectors.toList()).stream().map(x -> ((java.math.BigDecimal)x.getDeptNum())).collect(Collectors.toList()), number("1"))))).collect(Collectors.toList()).stream().map(x -> ((String)x.getManager())).collect(Collectors.toList()), number("1")));
    }
}
