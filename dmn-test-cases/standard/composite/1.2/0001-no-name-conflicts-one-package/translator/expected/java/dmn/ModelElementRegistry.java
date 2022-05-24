
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("http://www.provider.com/definitions/model-a#Person Name", "PersonName");
        register("http://www.provider.com/definitions/model-b1#Evaluating Say Hello", "EvaluatingB1SayHello");
        register("http://www.provider.com/definitions/model-b1#Great the person", "GreetThePerson");
        register("http://www.provider.com/definitions/model-b2#Evaluating B2 Say Hello", "EvaluatingB2SayHello");
        register("http://www.provider.com/definitions/model-c#Model C Decision based on Bs", "ModelCDecisionBasedOnBs");
    }
}
