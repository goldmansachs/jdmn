
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

    public static final LitexpLogic INSTANCE = new LitexpLogic();

    private LitexpLogic() {
    }

    public static List<type.Zip> litexpLogic(List<String> blacklist, List<java.math.BigDecimal> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return INSTANCE.apply(blacklist, listOfNumbers, names, rgb1, rgb1List, rgb2, rgb2List, annotationSet_, eventListener_, externalExecutor_, cache_);
    }

    private List<type.Zip> apply(List<String> blacklist, List<java.math.BigDecimal> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
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
