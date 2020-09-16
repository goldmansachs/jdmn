
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "partC"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "partC",
    label = "partC",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
    rulesCount = 1
)
public class PartC extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "partC",
        "partC",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE,
        1
    );

    private final Logical logical;
    private final Temporal temporal;
    private final TemporalComparator temporalComparator;
    private final TemporalDiff temporalDiff;

    public PartC() {
        this(new Logical(), new Temporal(), new TemporalComparator(), new TemporalDiff());
    }

    public PartC(Logical logical, Temporal temporal, TemporalComparator temporalComparator, TemporalDiff temporalDiff) {
        this.logical = logical;
        this.temporal = temporal;
        this.temporalComparator = temporalComparator;
        this.temporalDiff = temporalDiff;
    }

    public String apply(String booleanA, String booleanB, String date, String dateTime, String time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((booleanA != null ? Boolean.valueOf(booleanA) : null), (booleanB != null ? Boolean.valueOf(booleanB) : null), (date != null ? date(date) : null), (dateTime != null ? dateAndTime(dateTime) : null), (time != null ? time(time) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'PartC'", e);
            return null;
        }
    }

    public String apply(String booleanA, String booleanB, String date, String dateTime, String time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((booleanA != null ? Boolean.valueOf(booleanA) : null), (booleanB != null ? Boolean.valueOf(booleanB) : null), (date != null ? date(date) : null), (dateTime != null ? dateAndTime(dateTime) : null), (time != null ? time(time) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'PartC'", e);
            return null;
        }
    }

    public String apply(Boolean booleanA, Boolean booleanB, javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar dateTime, javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(booleanA, booleanB, date, dateTime, time, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public String apply(Boolean booleanA, Boolean booleanB, javax.xml.datatype.XMLGregorianCalendar date, javax.xml.datatype.XMLGregorianCalendar dateTime, javax.xml.datatype.XMLGregorianCalendar time, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'partC'
            long partCStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments partCArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            partCArguments_.put("booleanA", booleanA);
            partCArguments_.put("booleanB", booleanB);
            partCArguments_.put("date", date);
            partCArguments_.put("dateTime", dateTime);
            partCArguments_.put("time", time);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, partCArguments_);

            // Apply child decisions
            Boolean logical = this.logical.apply(booleanA, booleanB, annotationSet_, eventListener_, externalExecutor_, cache_);
            Boolean temporal = this.temporal.apply(dateTime, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<String> temporalComparator = this.temporalComparator.apply(dateTime, annotationSet_, eventListener_, externalExecutor_, cache_);
            Boolean temporalDiff = this.temporalDiff.apply(date, dateTime, time, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'partC'
            String output_ = evaluate(logical, temporal, temporalComparator, temporalDiff, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'partC'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, partCArguments_, output_, (System.currentTimeMillis() - partCStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'partC' evaluation", e);
            return null;
        }
    }

    protected String evaluate(Boolean logical, Boolean temporal, List<String> temporalComparator, Boolean temporalDiff, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(logical, temporal, temporalComparator, temporalDiff, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        String output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
        } else {
            com.gs.dmn.runtime.RuleOutput ruleOutput_ = ruleOutputList_.applySingle(com.gs.dmn.runtime.annotation.HitPolicy.UNIQUE);
            output_ = ruleOutput_ == null ? null : ((PartCRuleOutput)ruleOutput_).getPartC();
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "\"\"")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean logical, Boolean temporal, List<String> temporalComparator, Boolean temporalDiff, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "\"\"");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        PartCRuleOutput output_ = new PartCRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            booleanNot((logical == null)),
            booleanNot((temporalDiff == null)),
            (notContainsAny(temporalComparator, asList("notDefined"))),
            booleanNot((temporal == null))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setPartC("NotNull");

            // Add annotation
            annotationSet_.addAnnotation("partC", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
