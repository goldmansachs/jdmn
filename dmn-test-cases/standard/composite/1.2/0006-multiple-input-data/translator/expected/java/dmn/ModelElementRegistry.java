
public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        // Register elements from model 'Model A'
        register("http://www.trisotech.com/definitions/_ae5b3c17-1ac3-4e1d-b4f9-2cf861aec6d9#Greet the Person", "model_a.GreetThePerson");
        register("http://www.trisotech.com/definitions/_ae5b3c17-1ac3-4e1d-b4f9-2cf861aec6d9#Person name", "model_a.PersonName");
        // Register elements from model 'Model B'
        register("http://www.trisotech.com/definitions/_2a1d771a-a899-4fef-abd6-fc894332337c#Evaluating Say Hello", "model_b.EvaluatingSayHello");
        // Register elements from model 'Model B2'
        register("http://www.trisotech.com/definitions/_9d46ece4-a96c-4cb0-abc0-0ca121ac3768#Evaluating B2 Say Hello", "model_b2.EvaluatingB2SayHello");
        // Register elements from model 'Nested Input Data Imports'
        register("http://www.trisotech.com/definitions/_10435dcd-8774-4575-a338-49dd554a0928#Model C Decision based on Bs", "nested_input_data_imports.ModelCDecisionBasedOnBs");
    }
}
