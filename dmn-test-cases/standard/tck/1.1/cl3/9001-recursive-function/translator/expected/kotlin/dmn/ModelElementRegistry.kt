
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '9001-recursive-function'
        register("FACT", "FACT")
        register("main", "Main")
        register("n", "N")
    }
}
