
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model B1'
        register("http://www.provider.com/definitions/model-b1#Evaluating Say Hello", "model_b1.EvaluatingSayHello");
        register("http://www.provider.com/definitions/model-b1#Great the person", "model_b1.GreetThePerson");
        // Register elements from model 'Model B2'
        register("http://www.provider.com/definitions/model-b2#Evaluating B2 Say Hello", "model_b2.EvaluatingSayHello");
        // Register elements from model 'Model C'
        register("http://www.provider.com/definitions/model-c#Model C Decision based on Bs", "model_c.ModelCDecisionBasedOnBs");
        // Register elements from model 'model-a'
    }
}
