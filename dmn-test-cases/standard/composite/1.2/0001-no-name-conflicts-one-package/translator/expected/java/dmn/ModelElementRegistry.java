
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model B1'
        register("Evaluating Say Hello", "EvaluatingB1SayHello");
        register("Great the person", "GreetThePerson");
        // Register elements from model 'Model B2'
        register("Evaluating B2 Say Hello", "EvaluatingB2SayHello");
        // Register elements from model 'Model C'
        register("Model C Decision based on Bs", "ModelCDecisionBasedOnBs");
        // Register elements from model 'model-a'
    }
}
