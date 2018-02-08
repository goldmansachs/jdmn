
import java.util.*;
import java.util.stream.Collectors;

import static IsFactor.isFactor;

@javax.annotation.Generated(value = {"decision.ftl", "checkFactors"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "checkFactors",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class CheckFactors extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "checkFactors",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public CheckFactors() {
    }

    public List<Boolean> apply(String factors, String value, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((factors != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(factors, java.math.BigDecimal[].class)) : null), (value != null ? number(value) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
        } catch (Exception e) {
            logError("Cannot apply decision 'CheckFactors'", e);
            return null;
        }
    }

    public List<Boolean> apply(String factors, String value, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            return apply((factors != null ? asList(com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(factors, java.math.BigDecimal[].class)) : null), (value != null ? number(value) : null), annotationSet_, eventListener_, externalExecutor_);
        } catch (Exception e) {
            logError("Cannot apply decision 'CheckFactors'", e);
            return null;
        }
    }

    public List<Boolean> apply(List<java.math.BigDecimal> factors, java.math.BigDecimal value, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(factors, value, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor());
    }

    public List<Boolean> apply(List<java.math.BigDecimal> factors, java.math.BigDecimal value, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        try {
            // Decision start
            long startTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments arguments = new com.gs.dmn.runtime.listener.Arguments();
            arguments.put("factors", factors);
            arguments.put("value", value);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, arguments);

            // Evaluate expression
            List<Boolean> output_ = evaluate(factors, value, annotationSet_, eventListener_, externalExecutor_);

            // Decision end
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, arguments, output_, (System.currentTimeMillis() - startTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'checkFactors' evaluation", e);
            return null;
        }
    }

    private List<Boolean> evaluate(List<java.math.BigDecimal> factors, java.math.BigDecimal value, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        return factors.stream().map(f -> isFactor(value, f, annotationSet_, eventListener_, externalExecutor_)).collect(Collectors.toList());
    }
}
