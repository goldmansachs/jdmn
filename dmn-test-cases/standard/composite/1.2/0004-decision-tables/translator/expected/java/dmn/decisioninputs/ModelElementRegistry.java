
package decisioninputs;

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("dateD", "decisioninputs.DateD");
        register("dateE", "decisioninputs.DateE");
        register("numB", "decisioninputs.NumB");
        register("numC", "decisioninputs.NumC");
        register("structA", "decisioninputs.StructA");
    }
}