import jdmn.runtime.discovery.ModelElementRegistry


class ModelElementRegistry(jdmn.runtime.discovery.ModelElementRegistry.ModelElementRegistry):
    def __init__(self):
        # Register elements from model 'Lending1'
        self.register("Adjudication", "Adjudication.Adjudication")
        self.register("AffordabilityCalculation", "AffordabilityCalculation.AffordabilityCalculation")
        self.register("ApplicantData", "ApplicantData.ApplicantData")
        self.register("ApplicationRiskScore", "ApplicationRiskScore.ApplicationRiskScore")
        self.register("ApplicationRiskScoreModel", "ApplicationRiskScoreModel.ApplicationRiskScoreModel")
        self.register("BureauCallType", "BureauCallType.BureauCallType")
        self.register("BureauCallTypeTable", "BureauCallTypeTable.BureauCallTypeTable")
        self.register("BureauData", "BureauData.BureauData")
        self.register("CreditContingencyFactorTable", "CreditContingencyFactorTable.CreditContingencyFactorTable")
        self.register("Eligibility", "Eligibility.Eligibility")
        self.register("EligibilityRules", "EligibilityRules.EligibilityRules")
        self.register("InstallmentCalculation", "InstallmentCalculation.InstallmentCalculation")
        self.register("'Post-bureauAffordability'", "PostBureauAffordability.PostBureauAffordability")
        self.register("'Post-bureauRiskCategory'", "PostBureauRiskCategory.PostBureauRiskCategory")
        self.register("'Post-bureauRiskCategoryTable'", "PostBureauRiskCategoryTable.PostBureauRiskCategoryTable")
        self.register("'Pre-bureauAffordability'", "PreBureauAffordability.PreBureauAffordability")
        self.register("'Pre-bureauRiskCategory'", "PreBureauRiskCategory.PreBureauRiskCategory")
        self.register("'Pre-bureauRiskCategoryTable'", "PreBureauRiskCategoryTable.PreBureauRiskCategoryTable")
        self.register("RequestedProduct", "RequestedProduct.RequestedProduct")
        self.register("RequiredMonthlyInstallment", "RequiredMonthlyInstallment.RequiredMonthlyInstallment")
        self.register("Routing", "Routing.Routing")
        self.register("RoutingRules", "RoutingRules.RoutingRules")
        self.register("Strategy", "Strategy.Strategy")
        self.register("SupportingDocuments", "SupportingDocuments.SupportingDocuments")
