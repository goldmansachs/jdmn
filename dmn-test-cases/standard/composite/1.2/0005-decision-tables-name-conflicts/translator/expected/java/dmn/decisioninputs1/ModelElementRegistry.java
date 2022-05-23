
package decisioninputs1;

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("dateD", "decisioninputs1.DateD");
        register("numB", "decisioninputs1.NumB");
        register("numC", "decisioninputs1.NumC");
        register("structA", "decisioninputs1.StructA");
    }
}