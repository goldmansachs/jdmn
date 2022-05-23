
package model_a;

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("'Greet the Person'", "model_a.GreetThePerson");
        register("'Person name'", "model_a.PersonName");
    }
}