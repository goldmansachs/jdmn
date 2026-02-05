
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "logical"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class LogicalInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanA;
    private Boolean booleanB;

    public LogicalInput_() {
    }

    public LogicalInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanA = input_.get("booleanA");
            setBooleanA((Boolean)booleanA);
            Object booleanB = input_.get("booleanB");
            setBooleanB((Boolean)booleanB);
        }
    }

    public Boolean getBooleanA() {
        return this.booleanA;
    }

    public void setBooleanA(Boolean booleanA) {
        this.booleanA = booleanA;
    }

    public Boolean getBooleanB() {
        return this.booleanB;
    }

    public void setBooleanB(Boolean booleanB) {
        this.booleanB = booleanB;
    }

}
