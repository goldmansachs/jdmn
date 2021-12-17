
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"decision.ftl", "decision_002_input"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision_002_input",
    label = "",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
    rulesCount = -1
)
public class Decision_002_input extends com.gs.dmn.runtime.DefaultDMNBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision_002_input",
        "",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.LITERAL_EXPRESSION,
        com.gs.dmn.runtime.annotation.HitPolicy.UNKNOWN,
        -1
    );

    public Decision_002_input() {
    }

    public String apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision_002_input'
            long decision_002_inputStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decision_002_inputArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decision_002_inputArguments_);

            if (cache_.contains("decision_002_input")) {
                // Retrieve value from cache
                String output_ = (String)cache_.lookup("decision_002_input");

                // End decision 'decision_002_input'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_002_inputArguments_, output_, (System.currentTimeMillis() - decision_002_inputStartTime_));

                return output_;
            } else {
                // Evaluate decision 'decision_002_input'
                String output_ = evaluate(annotationSet_, eventListener_, externalExecutor_, cache_);
                cache_.bind("decision_002_input", output_);

                // End decision 'decision_002_input'
                eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decision_002_inputArguments_, output_, (System.currentTimeMillis() - decision_002_inputStartTime_));

                return output_;
            }
        } catch (Exception e) {
            logError("Exception caught in 'decision_002_input' evaluation", e);
            return null;
        }
    }

    protected String evaluate(com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return "bar";
    }
}
