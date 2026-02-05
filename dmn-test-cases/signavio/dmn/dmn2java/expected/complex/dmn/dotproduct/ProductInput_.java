
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "product"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ProductInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Componentwise3 componentwise4_iterator;

    public ProductInput_() {
    }

    public ProductInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object componentwise4_iterator = input_.get("Componentwise");
            setComponentwise4_iterator(type.Componentwise3.toComponentwise3(null));
        }
    }

    public type.Componentwise3 getComponentwise4_iterator() {
        return this.componentwise4_iterator;
    }

    public void setComponentwise4_iterator(type.Componentwise3 componentwise4_iterator) {
        this.componentwise4_iterator = componentwise4_iterator;
    }

}
