
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'join01'
        register("DeptTable", "DeptTable");
        register("EmployeeTable", "EmployeeTable");
        register("Join", "Join");
        register("LastName", "LastName");
    }
}
