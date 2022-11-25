
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'simple-decision-with-bkm'
        register("bKM", "BKM");
        register("SUM", "SUM");
        register("Sign", "Sign");
    }
}
