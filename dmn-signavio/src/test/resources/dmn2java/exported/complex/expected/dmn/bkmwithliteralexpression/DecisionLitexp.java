
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decisionLitexp"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decisionLitexp",
    label = "decision litexp",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class DecisionLitexp extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decisionLitexp",
        "decision litexp",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.INVOCATION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public DecisionLitexp() {
    }

    public List<type.Zip> apply(String censored, String labels, String numz, String redgreenblue1, String redgreenblue2, String redgreenbluelist1, String redgreenbluelist2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((censored != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(censored, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (labels != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(labels, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (numz != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numz, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), redgreenblue1, redgreenblue2, (redgreenbluelist1 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(redgreenbluelist1, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (redgreenbluelist2 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(redgreenbluelist2, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionLitexp'", e);
            return null;
        }
    }

    public List<type.Zip> apply(String censored, String labels, String numz, String redgreenblue1, String redgreenblue2, String redgreenbluelist1, String redgreenbluelist2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((censored != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(censored, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (labels != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(labels, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (numz != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(numz, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), redgreenblue1, redgreenblue2, (redgreenbluelist1 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(redgreenbluelist1, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (redgreenbluelist2 != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(redgreenbluelist2, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'DecisionLitexp'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<String> censored, List<String> labels, List<java.math.BigDecimal> numz, String redgreenblue1, String redgreenblue2, List<String> redgreenbluelist1, List<String> redgreenbluelist2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(censored, labels, numz, redgreenblue1, redgreenblue2, redgreenbluelist1, redgreenbluelist2, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<type.Zip> apply(List<String> censored, List<String> labels, List<java.math.BigDecimal> numz, String redgreenblue1, String redgreenblue2, List<String> redgreenbluelist1, List<String> redgreenbluelist2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decisionLitexp'
            long decisionLitexpStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionLitexpArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionLitexpArguments_.put("censored", censored);
            decisionLitexpArguments_.put("labels", labels);
            decisionLitexpArguments_.put("numz", numz);
            decisionLitexpArguments_.put("redgreenblue1", redgreenblue1);
            decisionLitexpArguments_.put("redgreenblue2", redgreenblue2);
            decisionLitexpArguments_.put("redgreenbluelist1", redgreenbluelist1);
            decisionLitexpArguments_.put("redgreenbluelist2", redgreenbluelist2);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionLitexpArguments_);

            // Evaluate decision 'decisionLitexp'
            List<type.Zip> output_ = evaluate(censored, labels, numz, redgreenblue1, redgreenblue2, redgreenbluelist1, redgreenbluelist2, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decisionLitexp'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionLitexpArguments_, output_, (System.currentTimeMillis() - decisionLitexpStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decisionLitexp' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<String> censored, List<String> labels, List<java.math.BigDecimal> numz, String redgreenblue1, String redgreenblue2, List<String> redgreenbluelist1, List<String> redgreenbluelist2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return LitexpLogic.litexpLogic(censored, numz, labels, redgreenblue1, redgreenbluelist1, redgreenblue2, redgreenbluelist2, annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
