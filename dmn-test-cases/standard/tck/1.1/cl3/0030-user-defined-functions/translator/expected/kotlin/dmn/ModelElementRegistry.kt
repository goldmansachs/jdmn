
class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        // Register elements from model '0030-user-defined-functions'
        register("'named function invocation'", "NamedFunctionInvocation")
        register("'simple function invocation'", "SimpleFunctionInvocation")
        register("stringInputA", "StringInputA")
        register("stringInputB", "StringInputB")
    }
}
