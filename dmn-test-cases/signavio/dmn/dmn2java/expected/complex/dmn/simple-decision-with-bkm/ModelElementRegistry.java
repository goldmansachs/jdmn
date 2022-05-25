
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'simple-decision-with-bkm'
        register("A", "A");
        register("A", "A2");
        register("B", "B");
        register("B", "B3");
        register("bKM", "BKM");
        register("SUM", "SUM");
        register("Sign", "Sign");
    }
}
