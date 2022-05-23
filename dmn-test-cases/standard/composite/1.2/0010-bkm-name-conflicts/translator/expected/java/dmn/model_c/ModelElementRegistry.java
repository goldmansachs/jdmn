
package model_c;

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("AA", "model_c.Aa");
        register("BA", "model_c.Ba");
        register("C", "model_c.C");
    }
}