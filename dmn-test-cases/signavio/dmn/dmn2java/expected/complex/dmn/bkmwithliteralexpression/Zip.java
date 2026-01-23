
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
public class Zip extends com.gs.dmn.signavio.runtime.JavaTimeSignavioBaseDecision<List<type.Zip>> {
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

    @java.lang.Override()
    public List<type.Zip> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("blacklist") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("blacklist"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("ListOfNumbers") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ListOfNumbers"), new com.fasterxml.jackson.core.type.TypeReference<List<java.lang.Number>>() {}) : null), (input_.get("names") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("names"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), input_.get("rgb1"), (input_.get("rgb1 list") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("rgb1 list"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), input_.get("rgb2"), (input_.get("rgb2 list") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("rgb2 list"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<String> blacklist, List<java.lang.Number> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'zip'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
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

            // Evaluate decision 'zip'
            List<type.Zip> output_ = evaluate(blacklist, listOfNumbers, names, rgb1, rgb1List, rgb2, rgb2List, context_);

            // End decision 'zip'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, zipArguments_, output_, (System.currentTimeMillis() - zipStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'zip' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<String> blacklist, List<java.lang.Number> listOfNumbers, List<String> names, String rgb1, List<String> rgb1List, String rgb2, List<String> rgb2List, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<String> appendall = this.appendall.apply(rgb1, rgb1List, rgb2, rgb2List, context_);
        List<java.lang.Number> removeValues = this.removeValues.apply(listOfNumbers, context_);
        List<String> removeall = this.removeall.apply(blacklist, names, context_);

        return zip(asList("n", "e", "t"), asList(removeValues, appendall, removeall)).stream().map(x_ -> type.Zip.toZip(x_)).collect(java.util.stream.Collectors.toList());
    }
}
