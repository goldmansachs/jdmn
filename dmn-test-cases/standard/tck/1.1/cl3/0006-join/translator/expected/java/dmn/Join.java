
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
public class Join extends com.gs.dmn.runtime.JavaTimeDMNBaseDecision {
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
    public String applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("DeptTable") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("DeptTable"), new com.fasterxml.jackson.core.type.TypeReference<List<type.TDeptTable>>() {}) : null), (input_.get("EmployeeTable") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("EmployeeTable"), new com.fasterxml.jackson.core.type.TypeReference<List<type.TEmployeeTable>>() {}) : null), input_.get("LastName"), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Join'", e);
            return null;
        }
    }

    public String apply(List<type.TDeptTable> deptTable, List<type.TEmployeeTable> employeeTable, String lastName, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'Join'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long joinStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments joinArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            joinArguments_.put("DeptTable", deptTable);
            joinArguments_.put("EmployeeTable", employeeTable);
            joinArguments_.put("LastName", lastName);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, joinArguments_);

            // Evaluate decision 'Join'
            String output_ = lambda.apply(deptTable, employeeTable, lastName, context_);

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
                com.gs.dmn.runtime.ExecutionContext context_ = 3 < args_.length ? (com.gs.dmn.runtime.ExecutionContext) args_[3] : null;
                com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
                com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
                com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
                com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;

                return (String)(elementAt(deptTable.stream().filter(item -> numericEqual(((java.math.BigDecimal)(item != null ? item.getNumber() : null)), (java.math.BigDecimal)(elementAt(employeeTable.stream().filter(item_1_ -> stringEqual(((String)(item_1_ != null ? item_1_.getName() : null)), lastName) == Boolean.TRUE).collect(Collectors.toList()).stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDeptNum() : null))).collect(Collectors.toList()), number("1")))) == Boolean.TRUE).collect(Collectors.toList()).stream().map(x -> ((String)(x != null ? x.getManager() : null))).collect(Collectors.toList()), number("1")));
            }
        };
}
