
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Example credit decision'
        register("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#assessApplicantAge", "AssessApplicantAge");
        register("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#assessIssue", "AssessIssue");
        register("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#assessIssueRisk", "AssessIssueRisk");
        register("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#compareAgainstLendingThreshold", "CompareAgainstLendingThreshold");
        register("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#generateOutputData", "GenerateOutputData");
        register("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#makeCreditDecision", "MakeCreditDecision");
        register("http://www.provider.com/dmn/1.1/diagram/9acf44f2b05343d79fc35140c493c1e0.xml#processPriorIssues", "ProcessPriorIssues");
    }
}
