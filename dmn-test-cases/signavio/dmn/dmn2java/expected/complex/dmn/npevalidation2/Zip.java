
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

    private final AccessCertainTemporalUnits accessCertainTemporalUnits;
    private final BuildDateStringInAnnotation buildDateStringInAnnotation;
    private final DescribeAgesList describeAgesList;
    private final NegateSecond negateSecond;
    private final NoRuleMatchesMultiHit noRuleMatchesMultiHit;
    private final NoRuleMatchesSingleHit noRuleMatchesSingleHit;
    private final TemporalDiffs temporalDiffs;

    public Zip() {
        this(new AccessCertainTemporalUnits(), new BuildDateStringInAnnotation(), new DescribeAgesList(), new NegateSecond(), new NoRuleMatchesMultiHit(), new NoRuleMatchesSingleHit(), new TemporalDiffs());
    }

    public Zip(AccessCertainTemporalUnits accessCertainTemporalUnits, BuildDateStringInAnnotation buildDateStringInAnnotation, DescribeAgesList describeAgesList, NegateSecond negateSecond, NoRuleMatchesMultiHit noRuleMatchesMultiHit, NoRuleMatchesSingleHit noRuleMatchesSingleHit, TemporalDiffs temporalDiffs) {
        this.accessCertainTemporalUnits = accessCertainTemporalUnits;
        this.buildDateStringInAnnotation = buildDateStringInAnnotation;
        this.describeAgesList = describeAgesList;
        this.negateSecond = negateSecond;
        this.noRuleMatchesMultiHit = noRuleMatchesMultiHit;
        this.noRuleMatchesSingleHit = noRuleMatchesSingleHit;
        this.temporalDiffs = temporalDiffs;
    }

    @java.lang.Override()
    public List<type.Zip> applyMap(java.util.Map<String, String> input_, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            return apply((input_.get("ages") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("ages"), new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (input_.get("day") != null ? number(input_.get("day")) : null), (input_.get("hour") != null ? number(input_.get("hour")) : null), (input_.get("minute") != null ? number(input_.get("minute")) : null), (input_.get("month") != null ? number(input_.get("month")) : null), (input_.get("names") != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(input_.get("names"), new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (input_.get("second") != null ? number(input_.get("second")) : null), (input_.get("year") != null ? number(input_.get("year")) : null), context_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<java.math.BigDecimal> ages, java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, List<String> names, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.ExecutionContext context_) {
        try {
            // Start decision 'zip'
            com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
            com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
            com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
            com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
            long zipStartTime_ = System.currentTimeMillis();
            com.gs.dmn.runtime.listener.Arguments zipArguments_ = new com.gs.dmn.runtime.listener.Arguments();
            zipArguments_.put("ages", ages);
            zipArguments_.put("day", day);
            zipArguments_.put("hour", hour);
            zipArguments_.put("minute", minute);
            zipArguments_.put("month", month);
            zipArguments_.put("names", names);
            zipArguments_.put("second", second);
            zipArguments_.put("year", year);
            eventListener_.startDRGElement(DRG_ELEMENT_METADATA, zipArguments_);

            // Evaluate decision 'zip'
            List<type.Zip> output_ = evaluate(ages, day, hour, minute, month, names, second, year, context_);

            // End decision 'zip'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, zipArguments_, output_, (System.currentTimeMillis() - zipStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'zip' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<java.math.BigDecimal> ages, java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, List<String> names, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.ExecutionContext context_) {
        com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_ = context_ != null ? context_.getAnnotations() : null;
        com.gs.dmn.runtime.listener.EventListener eventListener_ = context_ != null ? context_.getEventListener() : null;
        com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_ = context_ != null ? context_.getExternalFunctionExecutor() : null;
        com.gs.dmn.runtime.cache.Cache cache_ = context_ != null ? context_.getCache() : null;
        // Apply child decisions
        List<java.math.BigDecimal> accessCertainTemporalUnits = this.accessCertainTemporalUnits.apply(day, hour, minute, month, second, year, context_);
        Boolean buildDateStringInAnnotation = this.buildDateStringInAnnotation.apply(day, month, year, context_);
        List<String> describeAgesList = this.describeAgesList.apply(ages, context_);
        java.math.BigDecimal negateSecond = this.negateSecond.apply(second, context_);
        List<Boolean> noRuleMatchesMultiHit = this.noRuleMatchesMultiHit.apply(second, context_);
        Boolean noRuleMatchesSingleHit = this.noRuleMatchesSingleHit.apply(second, context_);
        List<type.TemporalDiffs> temporalDiffs = this.temporalDiffs.apply(day, hour, minute, month, second, year, context_);

        return zip(asList("names", "ages", "dateDiffs", "dateTimeDiffs", "temporalUnits", "agesListDescription"), asList(names, ages, temporalDiffs.stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDateDiff() : null))).collect(Collectors.toList()), temporalDiffs.stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDateTimeDiff() : null))).collect(Collectors.toList()), accessCertainTemporalUnits, describeAgesList)).stream().map(x -> type.Zip.toZip(x)).collect(Collectors.toList());
    }
}
