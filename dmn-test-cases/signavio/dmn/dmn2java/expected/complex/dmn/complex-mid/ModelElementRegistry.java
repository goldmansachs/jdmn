
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Complex MID'
        register("http://www.provider.com/dmn/1.1/diagram/3652588c6383423c9774f4dfd4393cb1.xml#bigMid", "BigMid");
        register("http://www.provider.com/dmn/1.1/diagram/3652588c6383423c9774f4dfd4393cb1.xml#decide", "Decide");
        register("http://www.provider.com/dmn/1.1/diagram/3652588c6383423c9774f4dfd4393cb1.xml#decision", "Decision");
        register("http://www.provider.com/dmn/1.1/diagram/3652588c6383423c9774f4dfd4393cb1.xml#smallMid", "SmallMid");
        register("http://www.provider.com/dmn/1.1/diagram/3652588c6383423c9774f4dfd4393cb1.xml#topDecision", "TopDecision");
    }
}
