
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model B1'
        register("http://www.provider.com/definitions/model-b1#evaluatingB1SayHello", "EvaluatingB1SayHello");
        register("http://www.provider.com/definitions/model-b1#greetThePerson", "GreetThePerson");
        // Register elements from model 'Model B2'
        register("http://www.provider.com/definitions/model-b2#evaluatingB2SayHello", "EvaluatingB2SayHello");
        // Register elements from model 'Model C'
        register("http://www.provider.com/definitions/model-c#modelCDecisionBasedOnBs", "ModelCDecisionBasedOnBs");
        // Register elements from model 'model-a'
    }
}
