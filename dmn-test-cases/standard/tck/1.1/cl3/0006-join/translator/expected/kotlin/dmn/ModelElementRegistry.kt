
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0006-join'
        register("DeptTable", "DeptTable")
        register("EmployeeTable", "EmployeeTable")
        register("Join", "Join")
        register("LastName", "LastName")
    }
}
