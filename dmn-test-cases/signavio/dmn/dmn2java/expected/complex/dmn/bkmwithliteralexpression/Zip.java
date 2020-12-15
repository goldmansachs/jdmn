
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "zip"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "zip",
    label = "zip",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Zip extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "zip",
        "zip",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Appendall appendall;
    private final RemoveValues removeValues;
    private final Removeall removeall;

    public Zip() {
        this(new Appendall(), new RemoveValues(), new Removeall());
    }

    public Zip(Appendall appendall, RemoveValues removeValues, Removeall removeall) {
        this.appendall = appendall;
        this.removeValues = removeValues;
        this.removeall = removeall;
    }

    public List<type.Zip> apply(String blacklist, String listOfNumbers, String names, String rgb1, String rgb1List, String rgb2, String rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((blacklist != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(blacklist, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (listOfNumbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listOfNumbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (names != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(names, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), rgb1, (rgb1List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb1List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), rgb2, (rgb2List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb2List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip'", e);
            return null;
        }
    }

    public List<type.Zip> apply(String blacklist, String listOfNumbers, String names, String rgb1, String rgb1List, String rgb2, String rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((blacklist != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(blacklist, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (listOfNumbers != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(listOfNumbers, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (names != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(names, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), rgb1, (rgb1List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb1List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), rgb2, (rgb2List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb2List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<String> blacklist, List<java.math.BigDecimal> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(blacklist, listOfNumbers, names, rgb1, rgb1List, rgb2, rgb2List, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<type.Zip> apply(List<String> blacklist, List<java.math.BigDecimal> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'zip'
            long zipStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments zipArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            zipArguments_.put("blacklist", blacklist);
            zipArguments_.put("ListOfNumbers", listOfNumbers);
            zipArguments_.put("names", names);
            zipArguments_.put("rgb1", rgb1);
            zipArguments_.put("rgb1 list", rgb1List);
            zipArguments_.put("rgb2", rgb2);
            zipArguments_.put("rgb2 list", rgb2List);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, zipArguments_);

            // Apply child decisions
            List<String> appendall = this.appendall.apply(rgb1, rgb1List, rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<java.math.BigDecimal> removeValues = this.removeValues.apply(listOfNumbers, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<String> removeall = this.removeall.apply(blacklist, names, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'zip'
            List<type.Zip> output_ = evaluate(appendall, removeValues, removeall, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'zip'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, zipArguments_, output_, (System.currentTimeMillis() - zipStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'zip' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<String> appendall, List<java.math.BigDecimal> removeValues, List<String> removeall, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return zip(asList("n", "e", "t"), asList(removeValues, appendall, removeall)).stream().map(x -> type.Zip.toZip(x)).collect(Collectors.toList());
    }
}
