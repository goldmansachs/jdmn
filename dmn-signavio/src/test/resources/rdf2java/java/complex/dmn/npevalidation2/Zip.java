
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

    public List<type.Zip> apply(String ages, String day, String hour, String minute, String month, String names, String second, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        try {
            return apply((ages != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(ages, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (day != null ? number(day) : null), (hour != null ? number(hour) : null), (minute != null ? number(minute) : null), (month != null ? number(month) : null), (names != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(names, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (second != null ? number(second) : null), (year != null ? number(year) : null), annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip'", e);
            return null;
        }
    }

    public List<type.Zip> apply(String ages, String day, String hour, String minute, String month, String names, String second, String year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            return apply((ages != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(ages, new com.fasterxml.jackson.core.type.TypeReference<List<java.math.BigDecimal>>() {}) : null), (day != null ? number(day) : null), (hour != null ? number(hour) : null), (minute != null ? number(minute) : null), (month != null ? number(month) : null), (names != null ? com.gs.dmn.serialization.JsonSerializer.OBJECT_MAPPER.readValue(names, new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {}) : null), (second != null ? number(second) : null), (year != null ? number(year) : null), annotationSet_, eventListener_, externalExecutor_, cache_);
        } catch (Exception e) {
            logError("Cannot apply decision 'Zip'", e);
            return null;
        }
    }

    public List<type.Zip> apply(List<java.math.BigDecimal> ages, java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, List<String> names, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_) {
        return apply(ages, day, hour, minute, month, names, second, year, annotationSet_, new com.gs.dmn.runtime.listener.LoggingEventListener(LOGGER), new com.gs.dmn.runtime.external.DefaultExternalFunctionExecutor(), new com.gs.dmn.runtime.cache.DefaultCache());
    }

    public List<type.Zip> apply(List<java.math.BigDecimal> ages, java.math.BigDecimal day, java.math.BigDecimal hour, java.math.BigDecimal minute, java.math.BigDecimal month, List<String> names, java.math.BigDecimal second, java.math.BigDecimal year, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        try {
            // Start decision 'zip'
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

            // Apply child decisions
            List<java.math.BigDecimal> accessCertainTemporalUnits = this.accessCertainTemporalUnits.apply(day, hour, minute, month, second, year, annotationSet_, eventListener_, externalExecutor_, cache_);
            Boolean buildDateStringInAnnotation = this.buildDateStringInAnnotation.apply(day, month, year, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<String> describeAgesList = this.describeAgesList.apply(ages, annotationSet_, eventListener_, externalExecutor_, cache_);
            java.math.BigDecimal negateSecond = this.negateSecond.apply(second, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<Boolean> noRuleMatchesMultiHit = this.noRuleMatchesMultiHit.apply(second, annotationSet_, eventListener_, externalExecutor_, cache_);
            Boolean noRuleMatchesSingleHit = this.noRuleMatchesSingleHit.apply(second, annotationSet_, eventListener_, externalExecutor_, cache_);
            List<type.TemporalDiffs> temporalDiffs = this.temporalDiffs.apply(day, hour, minute, month, second, year, annotationSet_, eventListener_, externalExecutor_, cache_);

            // Evaluate decision 'zip'
            List<type.Zip> output_ = evaluate(accessCertainTemporalUnits, ages, buildDateStringInAnnotation, describeAgesList, names, negateSecond, noRuleMatchesMultiHit, noRuleMatchesSingleHit, temporalDiffs, annotationSet_, eventListener_, externalExecutor_, cache_);

            // End decision 'zip'
            eventListener_.endDRGElement(DRG_ELEMENT_METADATA, zipArguments_, output_, (System.currentTimeMillis() - zipStartTime_));

            return output_;
        } catch (Exception e) {
            logError("Exception caught in 'zip' evaluation", e);
            return null;
        }
    }

    protected List<type.Zip> evaluate(List<java.math.BigDecimal> accessCertainTemporalUnits, List<java.math.BigDecimal> ages, Boolean buildDateStringInAnnotation, List<String> describeAgesList, List<String> names, java.math.BigDecimal negateSecond, List<Boolean> noRuleMatchesMultiHit, Boolean noRuleMatchesSingleHit, List<type.TemporalDiffs> temporalDiffs, com.gs.dmn.runtime.annotation.AnnotationSet annotationSet_, com.gs.dmn.runtime.listener.EventListener eventListener_, com.gs.dmn.runtime.external.ExternalFunctionExecutor externalExecutor_, com.gs.dmn.runtime.cache.Cache cache_) {
        return zip(asList("names", "ages", "dateDiffs", "dateTimeDiffs", "temporalUnits", "agesListDescription"), asList(names, ages, temporalDiffs.stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDateDiff() : null))).collect(Collectors.toList()), temporalDiffs.stream().map(x -> ((java.math.BigDecimal)(x != null ? x.getDateTimeDiff() : null))).collect(Collectors.toList()), accessCertainTemporalUnits, describeAgesList)).stream().map(x -> type.Zip.toZip(x)).collect(Collectors.toList());
    }
}
