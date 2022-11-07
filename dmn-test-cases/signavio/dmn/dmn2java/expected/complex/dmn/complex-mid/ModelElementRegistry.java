
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Complex MID'
        register("Big mid", "BigMid");
        register("decide", "Decide");
        register("decision", "Decision");
        register("small mid", "SmallMid");
        register("top decision", "TopDecision");
    }
}
