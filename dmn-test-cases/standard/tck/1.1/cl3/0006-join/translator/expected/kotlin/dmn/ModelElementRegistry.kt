

class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        register("DeptTable", "DeptTable")
        register("EmployeeTable", "EmployeeTable")
        register("Join", "Join")
        register("LastName", "LastName")
    }
}