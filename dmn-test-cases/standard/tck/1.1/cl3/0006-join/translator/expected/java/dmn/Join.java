
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

    @java.lang.Override()
    public String apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("DeptTable"), input_.get("EmployeeTable"), input_.get("LastName"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Join'", e);
            return null;
        }
    }

    public String apply(String deptTable, String employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((deptTable != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(deptTable, new com.fasterxml.jackson.core.type.TypeReference<List<type.TDeptTable>>() {}) : null), (employeeTable != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(employeeTable, new com.fasterxml.jackson.core.type.TypeReference<List<type.TEmployeeTable>>() {}) : null), lastName, annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Join'", e);
            return null;
        }
    }

    public String apply(List<type.TDeptTable> deptTable, List<type.TEmployeeTable> employeeTable, String lastName, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'Join'
            long joinStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments joinArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            joinArguments_.put("DeptTable", deptTable);
            joinArguments_.put("EmployeeTable", employeeTable);
            joinArguments_.put("LastName", lastName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, joinArguments_);

            // Evaluate decision 'Join'
            String output_ = lambda.apply(deptTable, employeeTable, lastName, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'Join'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, joinArguments_, output_, (System.currentTimeMillis() - joinStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'Join' evaluation", e);
            return null;
        }
    }

    public com.gs.dmn.runtime.LambdaExpression<String> lambda =
        new com.gs.dmn.runtime.LambdaExpression<String>() {
            public String apply(Object... args_) {
                List<type.TDeptTable> deptTable = 0 < args_.length ? (List<type.TDeptTable>) args_[0] : null;
                List<type.TEmployeeTable> employeeTable = 1 < args_.length ? (List<type.TEmployeeTable>) args_[1] : null;
                String lastName = 2 < args_.length ? (String) args_[2] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = 3 < args_.length ? (com.gs.dmn.runtime.annotation.AnnotationSet) args_[3] : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = 4 < args_.length ? (com.gs.dmn.runtime.listener.EventListener) args_[4] : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = 5 < args_.length ? (com.gs.dmn.runtime.external.ExternalFunctionExecutor) args_[5] : null;
                com.gs.dmn.runtime.cache.Cache cache_ = 6 < args_.length ? (com.gs.dmn.runtime.cache.Cache) args_[6] : null;

                return (String)(elementAt(deptTable.stream().filter(item -> numericEqual(((java.math.BigDecimal)(item != null ? item.getNumber() : null)), (java.math.BigDecimal)(elementAt(employeeTable.stream().filter(item_1_ -> stringEqual(((String)(item_1_ != null ? item_1_.getName() : null)), lastName)).collect(Collectors.toList()).stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDeptNum() : null))).collect(Collectors.toList()), number("1"))))).collect(Collectors.toList()).stream().map(x -> ((String)(x != null ? x.getManager() : null))).collect(Collectors.toList()), number("1")));
            }
        };
}
