
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "priceInRange"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PriceInRangeInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numB;
    private java.lang.Number numC;
    private type.TA structA;

    public PriceInRangeInput_() {
    }

    public PriceInRangeInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numB = input_.get("numB");
            setNumB((java.lang.Number)numB);
            Object numC = input_.get("numC");
            setNumC((java.lang.Number)numC);
            Object structA = input_.get("structA");
            setStructA(type.TA.toTA(structA));
        }
    }

    public java.lang.Number getNumB() {
        return this.numB;
    }

    public void setNumB(java.lang.Number numB) {
        this.numB = numB;
    }

    public java.lang.Number getNumC() {
        return this.numC;
    }

    public void setNumC(java.lang.Number numC) {
        this.numC = numC;
    }

    public type.TA getStructA() {
        return this.structA;
    }

    public void setStructA(type.TA structA) {
        this.structA = structA;
    }

}
