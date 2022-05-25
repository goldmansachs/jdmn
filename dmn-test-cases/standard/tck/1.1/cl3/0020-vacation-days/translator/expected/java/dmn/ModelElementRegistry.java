
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model '0020-vacation-days'
        register("Age", "Age");
        register("'Base Vacation Days'", "BaseVacationDays");
        register("'Extra days case 1'", "ExtraDaysCase1");
        register("'Extra days case 2'", "ExtraDaysCase2");
        register("'Extra days case 3'", "ExtraDaysCase3");
        register("'Total Vacation Days'", "TotalVacationDays");
        register("'Years of Service'", "YearsOfService");
    }
}
