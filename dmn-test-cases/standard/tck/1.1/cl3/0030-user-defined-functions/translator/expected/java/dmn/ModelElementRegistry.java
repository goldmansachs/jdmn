
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("'named function invocation'", "NamedFunctionInvocation");
        register("'simple function invocation'", "SimpleFunctionInvocation");
        register("stringInputA", "StringInputA");
        register("stringInputB", "StringInputB");
    }
}
