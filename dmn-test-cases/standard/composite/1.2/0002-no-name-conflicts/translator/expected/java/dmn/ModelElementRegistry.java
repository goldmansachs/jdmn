
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model B1'
        register("http://www.provider.com/definitions/model-b1#evaluatingB1SayHello", "model_b1.EvaluatingB1SayHello");
        register("http://www.provider.com/definitions/model-b1#greetThePerson", "model_b1.GreetThePerson");
        // Register elements from model 'Model B2'
        register("http://www.provider.com/definitions/model-b2#evaluatingB2SayHello", "model_b2.EvaluatingB2SayHello");
        // Register elements from model 'Model C'
        register("http://www.provider.com/definitions/model-c#modelCDecisionBasedOnBs", "model_c.ModelCDecisionBasedOnBs");
        // Register elements from model 'model-a'
    }
}
