
package model_b1;

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("Evaluating Say Hello", "model_b1.EvaluatingB1SayHello");
        register("Great the person", "model_b1.GreetThePerson");
    }
}