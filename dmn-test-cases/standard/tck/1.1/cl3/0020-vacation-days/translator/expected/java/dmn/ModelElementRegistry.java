
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model '0020-vacation-days'
        register("https://www.drools.org/kie-dmn#Base Vacation Days", "BaseVacationDays");
        register("https://www.drools.org/kie-dmn#Extra days case 1", "ExtraDaysCase1");
        register("https://www.drools.org/kie-dmn#Extra days case 2", "ExtraDaysCase2");
        register("https://www.drools.org/kie-dmn#Extra days case 3", "ExtraDaysCase3");
        register("https://www.drools.org/kie-dmn#Total Vacation Days", "TotalVacationDays");
    }
}
