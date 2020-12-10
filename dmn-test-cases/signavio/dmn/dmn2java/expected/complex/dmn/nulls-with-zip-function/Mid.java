
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "mid"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "mid",
    label = "mid",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Mid extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "mid",
        "mid",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Zip zip;

    public Mid() {
        this(new Zip());
    }

    public Mid(Zip zip) {
        this.zip = zip;
    }

    public List<String> apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((inputA != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(inputA, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (inputB != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(inputB, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Mid'", e);
            return null;
        }
    }

    public List<String> apply(String inputA, String inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((inputA != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(inputA, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (inputB != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(inputB, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Mid'", e);
            return null;
        }
    }

    public List<String> apply(List<String> inputA, List<java.math.BigDecimal> inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(inputA, inputB, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(List<String> inputA, List<java.math.BigDecimal> inputB, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'mid'
            long midStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments midArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            midArguments_.put("inputA", inputA);
            midArguments_.put("inputB", inputB);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, midArguments_);

            // Apply child decisions
            List<type.Zip> zip = this.zip.apply(inputA, inputB, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Iterate and aggregate
            List<String> output_ = evaluate(inputA, inputB, zip, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'mid'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, midArguments_, output_, (System.currentTimeMillis() - midStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'mid' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(List<String> inputA, List<java.math.BigDecimal> inputB, List<type.Zip> zip, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        DoSomething doSomething = new DoSomething();
        return zip.stream().map(zip4_iterator -> doSomething.apply(type.Zip3.toZip3(zip4_iterator), annotationSet_, eventListener_, externalExecutor_, cache_)).collect(Collectors.toList());
    }
}
