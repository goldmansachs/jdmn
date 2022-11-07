
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Lending1'
        register("Adjudication", "Adjudication");
        register("AffordabilityCalculation", "AffordabilityCalculation");
        register("ApplicationRiskScore", "ApplicationRiskScore");
        register("ApplicationRiskScoreModel", "ApplicationRiskScoreModel");
        register("BureauCallType", "BureauCallType");
        register("BureauCallTypeTable", "BureauCallTypeTable");
        register("CreditContingencyFactorTable", "CreditContingencyFactorTable");
        register("Eligibility", "Eligibility");
        register("EligibilityRules", "EligibilityRules");
        register("InstallmentCalculation", "InstallmentCalculation");
        register("Post-bureauAffordability", "PostBureauAffordability");
        register("Post-bureauRiskCategory", "PostBureauRiskCategory");
        register("Post-bureauRiskCategoryTable", "PostBureauRiskCategoryTable");
        register("Pre-bureauAffordability", "PreBureauAffordability");
        register("Pre-bureauRiskCategory", "PreBureauRiskCategory");
        register("Pre-bureauRiskCategoryTable", "PreBureauRiskCategoryTable");
        register("RequiredMonthlyInstallment", "RequiredMonthlyInstallment");
        register("Routing", "Routing");
        register("RoutingRules", "RoutingRules");
        register("Strategy", "Strategy");
    }
}
