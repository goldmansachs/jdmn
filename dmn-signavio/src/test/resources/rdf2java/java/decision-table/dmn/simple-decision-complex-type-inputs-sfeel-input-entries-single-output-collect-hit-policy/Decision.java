
import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"signavio-decision.ftl", "decision"})
@com.gs.dmn.runtime.annotation.DRGElement(
    namespace = "",
    name = "decision",
    label = "Decision",
    elementKind = com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
    expressionKind = com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
    hitPolicy = com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
    rulesCount = 1
)
public class Decision extends com.gs.dmn.signavio.runtime.DefaultSignavioBaseDecision {
    public static final com.gs.dmn.runtime.listener.DRGElement DRG_ELEMENT_METADATA = new com.gs.dmn.runtime.listener.DRGElement(
        "",
        "decision",
        "Decision",
        com.gs.dmn.runtime.annotation.DRGElementKind.DECISION,
        com.gs.dmn.runtime.annotation.ExpressionKind.DECISION_TABLE,
        com.gs.dmn.runtime.annotation.HitPolicy.COLLECT,
        1
    );

    public Decision() {
    }

    public List<String> apply(String employed, String person, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((employed != null ? Boolean.valueOf(employed) : null), (person != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(person, new com.fasterxml.jackson.core.type.TypeReference<type.PersonImpl>() {}) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public List<String> apply(String employed, String person, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((employed != null ? Boolean.valueOf(employed) : null), (person != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(person, new com.fasterxml.jackson.core.type.TypeReference<type.PersonImpl>() {}) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Decision'", e);
            return null;
        }
    }

    public List<String> apply(Boolean employed, type.Person person, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(employed, person, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<String> apply(Boolean employed, type.Person person, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'decision'
            long decisionStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments decisionArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            decisionArguments_.put("Employed", employed);
            decisionArguments_.put("Person", person);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, decisionArguments_);

            // Evaluate decision 'decision'
            List<String> output_ = evaluate(employed, person, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'decision'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, decisionArguments_, output_, (System.currentTimeMillis() - decisionStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'decision' evaluation", e);
            return null;
        }
    }

    protected List<String> evaluate(Boolean employed, type.Person person, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        // Apply rules and collect results
        com.gs.dmn.runtime.RuleOutputList ruleOutputList_ = new com.gs.dmn.runtime.RuleOutputList();
        ruleOutputList_.add(rule0(employed, person, annotationSet_, eventListener_, externalExecutor_));

        // Return results based on hit policy
        List<String> output_;
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = null;
            if (output_ == null) {
                output_ = asList();
            }
        } else {
            List<? extends com.gs.dmn.runtime.RuleOutput> ruleOutputs_ = ruleOutputList_.applyMultiple(com.gs.dmn.runtime.annotation.HitPolicy.COLLECT);
            output_ = ruleOutputs_.stream().map(o -> ((DecisionRuleOutput)o).getResult()).collect(Collectors.toList());
        }

        return output_;
    }

    @com.gs.dmn.runtime.annotation.Rule(index = 0, annotation = "")
    public com.gs.dmn.runtime.RuleOutput rule0(Boolean employed, type.Person person, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_) {
        // Rule metadata
        com.gs.dmn.runtime.listener.Rule drgRuleMetadata = new com.gs.dmn.runtime.listener.Rule(0, "");

        // Rule start
        eventListener_.startRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

        // Apply rule
        DecisionRuleOutput output_ = new DecisionRuleOutput(false);
        if (ruleMatches(eventListener_, drgRuleMetadata,
            (numericEqual(((java.math.BigDecimal)(person != null ? person.getId() : null)), number("4"))),
            (stringEqual(((String)(person != null ? person.getFirstName() : null)), "Peter")),
            (stringEqual(((String)(person != null ? person.getLastName() : null)), "Sellers")),
            (stringEqual(((String)(person != null ? person.getGender() : null)), "male")),
            (dateEqual(((javax.xml.datatype.XMLGregorianCalendar)(person != null ? person.getDateOfBirth() : null)), date("2016-10-01"))),
            (timeEqual(((javax.xml.datatype.XMLGregorianCalendar)(person != null ? person.getTimeOfBirth() : null)), time("01:00:00Z"))),
            (dateTimeEqual(((javax.xml.datatype.XMLGregorianCalendar)(person != null ? person.getDateTimeOfBirth() : null)), dateAndTime("2016-10-01T00:00:00Z"))),
            (elementOf(((List<String>)(person != null ? person.getList() : null)), asList("abc"))),
            (booleanEqual(((Boolean)(person != null ? person.getMarried() : null)), Boolean.FALSE)),
            (booleanEqual(employed, Boolean.TRUE))
        )) {
            // Rule match
            eventListener_.matchRule(DRG_ELEMENT_METADATA, drgRuleMetadata);

            // Compute output
            output_.setMatched(true);
            output_.setResult("3");

            // Add annotation
            annotationSet_.addAnnotation("decision", 0, "");
        }

        // Rule end
        eventListener_.endRule(DRG_ELEMENT_METADATA, drgRuleMetadata, output_);

        return output_;
    }

}
