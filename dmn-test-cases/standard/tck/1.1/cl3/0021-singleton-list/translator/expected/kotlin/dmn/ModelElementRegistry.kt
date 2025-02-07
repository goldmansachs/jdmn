
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0021-singleton-list'
        register("Employees", "Employees")
        register("decision1", "Decision1")
        register("decision2", "Decision2")
        register("decision3", "Decision3")
        register("decision4", "Decision4")
        register("decision5", "Decision5")
    }
}
