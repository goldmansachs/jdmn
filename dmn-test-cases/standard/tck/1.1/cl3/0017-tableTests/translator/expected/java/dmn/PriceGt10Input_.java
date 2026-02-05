
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "priceGt10"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PriceGt10Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.TA structA;

    public PriceGt10Input_() {
    }

    public PriceGt10Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object structA = input_.get("structA");
            setStructA(type.TA.toTA(structA));
        }
    }

    public type.TA getStructA() {
        return this.structA;
    }

    public void setStructA(type.TA structA) {
        this.structA = structA;
    }

}
