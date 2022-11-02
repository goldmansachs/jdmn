
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model 'Lending1'
        register("Adjudication", "Adjudication")
        register("AffordabilityCalculation", "AffordabilityCalculation")
        register("ApplicantData", "ApplicantData")
        register("ApplicationRiskScore", "ApplicationRiskScore")
        register("ApplicationRiskScoreModel", "ApplicationRiskScoreModel")
        register("BureauCallType", "BureauCallType")
        register("BureauCallTypeTable", "BureauCallTypeTable")
        register("BureauData", "BureauData")
        register("CreditContingencyFactorTable", "CreditContingencyFactorTable")
        register("Eligibility", "Eligibility")
        register("EligibilityRules", "EligibilityRules")
        register("InstallmentCalculation", "InstallmentCalculation")
        register("Post-bureauAffordability", "PostBureauAffordability")
        register("Post-bureauRiskCategory", "PostBureauRiskCategory")
        register("Post-bureauRiskCategoryTable", "PostBureauRiskCategoryTable")
        register("Pre-bureauAffordability", "PreBureauAffordability")
        register("Pre-bureauRiskCategory", "PreBureauRiskCategory")
        register("Pre-bureauRiskCategoryTable", "PreBureauRiskCategoryTable")
        register("RequestedProduct", "RequestedProduct")
        register("RequiredMonthlyInstallment", "RequiredMonthlyInstallment")
        register("Routing", "Routing")
        register("RoutingRules", "RoutingRules")
        register("Strategy", "Strategy")
        register("SupportingDocuments", "SupportingDocuments")
    }
}
