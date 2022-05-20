
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

    public List<String> apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply(input_.get("rgb1"), input_.get("rgb1 list"), input_.get("rgb2"), input_.get("rgb2 list"), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
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

            // Evaluate decision 'appendall'
            List<String> output_ = evaluate(rgb1, rgb1List, rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'appendall'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, appendallArguments_, output_, (System.currentTimeMillis() - appendallStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'appendall' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply child decisions
        List<String> append = this.append.apply(rgb1, rgb2, annotationSet_, eventListener_, externalExecutor_, cache_);
        List<String> remove = this.remove.apply(rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);
        List<String> removeall2 = this.removeall2.apply(rgb1, rgb1List, annotationSet_, eventListener_, externalExecutor_, cache_);

        return appendAll(append, appendAll(remove, removeall2));
    }
}
