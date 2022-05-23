

public class ModelElementRegistry extends com.gs.dmn.runtime.discovery.ModelElementRegistry {
    public ModelElementRegistry() {
        register("ageClassification", "AgeClassification");
        register("calculateDiscountedPrice", "CalculateDiscountedPrice");
        register("price", "Price");
        register("student", "Student");
    }
}