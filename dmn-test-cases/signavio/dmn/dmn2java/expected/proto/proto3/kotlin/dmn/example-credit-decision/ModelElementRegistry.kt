
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model 'Example credit decision'
        register("Applicant", "Applicant")
        register("Assess applicant age", "AssessApplicantAge")
        register("Assess issue", "AssessIssue")
        register("Assess issue risk", "AssessIssueRisk")
        register("Compare against lending threshold", "CompareAgainstLendingThreshold")
        register("Current risk appetite", "CurrentRiskAppetite")
        register("Generate output data", "GenerateOutputData")
        register("Lending threshold", "LendingThreshold")
        register("Make credit decision", "MakeCreditDecision")
        register("Prior issue", "PriorIssue_iterator")
        register("Process prior issues", "ProcessPriorIssues")
    }
}
