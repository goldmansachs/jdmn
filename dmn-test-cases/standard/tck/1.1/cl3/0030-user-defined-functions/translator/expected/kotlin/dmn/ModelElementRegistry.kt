

class ModelElementRegistry : com.gs.dmn.runtime.discovery.ModelElementRegistry {
    constructor() {
        register("'named function invocation'", "NamedFunctionInvocation")
        register("'simple function invocation'", "SimpleFunctionInvocation")
        register("stringInputA", "StringInputA")
        register("stringInputB", "StringInputB")
    }
}