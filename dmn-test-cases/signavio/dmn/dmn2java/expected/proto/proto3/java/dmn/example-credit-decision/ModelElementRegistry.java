
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Example credit decision'
        register("Assess applicant age", "AssessApplicantAge");
        register("Assess issue", "AssessIssue");
        register("Assess issue risk", "AssessIssueRisk");
        register("Compare against lending threshold", "CompareAgainstLendingThreshold");
        register("Generate output data", "GenerateOutputData");
        register("Make credit decision", "MakeCreditDecision");
        register("Process prior issues", "ProcessPriorIssues");
    }
}
