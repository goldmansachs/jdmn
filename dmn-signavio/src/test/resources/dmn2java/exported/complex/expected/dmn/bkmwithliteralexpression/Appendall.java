
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "appendall"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "appendall",
    label = "appendall",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Appendall extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "appendall",
        "appendall",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private final Append append;
    private final Remove remove;
    private final Removeall2 removeall2;

    public Appendall() {
        this(new Append(), new Remove(), new Removeall2());
    }

    public Appendall(Append append, Remove remove, Removeall2 removeall2) {
        this.append = append;
        this.remove = remove;
        this.removeall2 = removeall2;
    }

    public List<String> apply(String rgb1, String rgb1List, String rgb2, String rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply(rgb1, (rgb1List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb1List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), rgb2, (rgb2List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb2List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Appendall'", e);
            return null;
        }
    }

    public List<String> apply(String rgb1, String rgb1List, String rgb2, String rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply(rgb1, (rgb1List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb1List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), rgb2, (rgb2List != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(rgb2List, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Appendall'", e);
            return null;
        }
    }

    public List<String> apply(String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(rgb1, rgb1List, rgb2, rgb2List, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'appendall'
            long appendallStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments appendallArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            appendallArguments_.put("rgb1", rgb1);
            appendallArguments_.put("rgb1 list", rgb1List);
            appendallArguments_.put("rgb2", rgb2);
            appendallArguments_.put("rgb2 list", rgb2List);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, appendallArguments_);

            // Apply child decisions
            List<String> append = this.append.apply(rgb1, rgb2, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<String> remove = this.remove.apply(rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<String> removeall2 = this.removeall2.apply(rgb1, rgb1List, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'appendall'
            List<String> output_ = evaluate(append, remove, removeall2, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'appendall'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, appendallArguments_, output_, (System.currentTimeMillis() - appendallStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'appendall' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(List<String> append, List<String> remove, List<String> removeall2, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return appendAll(append, appendAll(remove, removeall2));
    }
}
