
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "comparator"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ComparatorInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numberA;

    public ComparatorInput_() {
    }

    public ComparatorInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberA = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#numberA");
            setNumberA((java.lang.Number)numberA);
        }
    }

    public java.lang.Number getNumberA() {
        return this.numberA;
    }

    public void setNumberA(java.lang.Number numberA) {
        this.numberA = numberA;
    }

}
