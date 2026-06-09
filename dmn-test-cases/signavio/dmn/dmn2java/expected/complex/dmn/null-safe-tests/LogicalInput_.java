
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
            Object booleanA = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#booleanA");
            setBooleanA((Boolean)booleanA);
            Object booleanB = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#booleanB");
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
