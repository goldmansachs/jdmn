
import java.util.*;
import java.util.stream.Collectors;

@jakarta.annotation.Generated(value = {"signavio-decision.ftl", "zip1"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "zip1",
    label = "Zip1",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Zip1 extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "zip1",
        "Zip1",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Zip1() {
    }

    @java.lang.Override()
    public List<type.Zip1> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("A") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("A"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (input_.get("B") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("B"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip1'", e);
            return null;
        }
    }

    public List<type.Zip1> apply(List<java.math.BigDecimal> a4, List<java.math.BigDecimal> b, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'zip1'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long zip1StartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments zip1Arguments_ = new com.gs.dmn.runtime.listener.Arguments();
            zip1Arguments_.put("A", a4);
            zip1Arguments_.put("B", b);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, zip1Arguments_);

            // Evaluate decision 'zip1'
            List<type.Zip1> output_ = evaluate(a4, b, context_);

            // End decision 'zip1'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, zip1Arguments_, output_, (System.currentTimeMillis() - zip1StartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'zip1' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip1> evaluate(List<java.math.BigDecimal> a4, List<java.math.BigDecimal> b, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        return zip(asList("A", "B"), asList(a4, b)).stream().map(x -> type.Zip1.toZip1(x)).collect(Collectors.toList());
    }
}
