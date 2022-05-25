
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model 'join01'
        register("DeptTable", "DeptTable")
        register("EmployeeTable", "EmployeeTable")
        register("Join", "Join")
        register("LastName", "LastName")
    }
}
