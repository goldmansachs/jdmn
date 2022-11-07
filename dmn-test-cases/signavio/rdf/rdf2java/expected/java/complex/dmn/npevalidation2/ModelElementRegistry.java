
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'NPEValidation2'
        register("accessCertainTemporalUnits", "AccessCertainTemporalUnits");
        register("buildDateStringInAnnotation", "BuildDateStringInAnnotation");
        register("describeAgesList", "DescribeAgesList");
        register("generateTemporalObjects", "GenerateTemporalObjects");
        register("negateSecond", "NegateSecond");
        register("noRuleMatchesMultiHit", "NoRuleMatchesMultiHit");
        register("noRuleMatchesSingleHit", "NoRuleMatchesSingleHit");
        register("temporalDiffs", "TemporalDiffs");
        register("zip", "Zip");
    }
}
