
package model_c;

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("A", "model_c.A");
        register("C", "model_c.C");
    }
}