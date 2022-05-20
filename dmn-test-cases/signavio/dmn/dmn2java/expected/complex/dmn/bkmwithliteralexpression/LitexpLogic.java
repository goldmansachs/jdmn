
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-bkm.ftl", "litexpLogic"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "litexpLogic",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class LitexpLogic extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "litexpLogic",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.BUSINESS_KNOWLEDGE_MODEL,
        com.gs.dmn.runtime.annotation.ExpressionKind.OTHER,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    private static class LitexpLogicLazyHolder {
        static final LitexpLogic INSTANCE = new LitexpLogic();
    }
    public static LitexpLogic instance() {
        return LitexpLogicLazyHolder.INSTANCE;
    }

    private LitexpLogic() {
    }

    public List<type.Zip> apply(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("blacklist") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("blacklist"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("listOfNumbers") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("listOfNumbers"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (input_.get("names") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("names"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), input_.get("rgb1"), (input_.get("rgb1List") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("rgb1List"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), input_.get("rgb2"), (input_.get("rgb2List") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("rgb2List"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_.getAnnotations(), context_.getEventListener(), context_.getExternalFunctionExecutor(), context_.getCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'LitexpLogic'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<String> blacklist, List<java.math.BigDecimal> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start BKM 'litexpLogic'
            long litexpLogicStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments litexpLogicArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            litexpLogicArguments_.put("blacklist", blacklist);
            litexpLogicArguments_.put("ListOfNumbers", listOfNumbers);
            litexpLogicArguments_.put("names", names);
            litexpLogicArguments_.put("rgb1", rgb1);
            litexpLogicArguments_.put("rgb1 list", rgb1List);
            litexpLogicArguments_.put("rgb2", rgb2);
            litexpLogicArguments_.put("rgb2 list", rgb2List);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, litexpLogicArguments_);

            // Evaluate BKM 'litexpLogic'
            List<type.Zip> output_ = evaluate(blacklist, listOfNumbers, names, rgb1, rgb1List, rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End BKM 'litexpLogic'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, litexpLogicArguments_, output_, (System.currentTimeMillis() - litexpLogicStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'litexpLogic' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<String> blacklist, List<java.math.BigDecimal> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return new Zip().apply(blacklist, listOfNumbers, names, rgb1, rgb1List, rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);
    }
}
