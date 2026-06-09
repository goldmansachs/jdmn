
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "arithmetic"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ArithmeticInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numberA;
    private java.lang.Number numberB;
    private List<java.lang.Number> numberList;

    public ArithmeticInput_() {
    }

    public ArithmeticInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberA = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#numberA");
            setNumberA((java.lang.Number)numberA);
            Object numberB = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#numberB");
            setNumberB((java.lang.Number)numberB);
            Object numberList = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#numberList");
            setNumberList((List<java.lang.Number>)numberList);
        }
    }

    public java.lang.Number getNumberA() {
        return this.numberA;
    }

    public void setNumberA(java.lang.Number numberA) {
        this.numberA = numberA;
    }

    public java.lang.Number getNumberB() {
        return this.numberB;
    }

    public void setNumberB(java.lang.Number numberB) {
        this.numberB = numberB;
    }

    public List<java.lang.Number> getNumberList() {
        return this.numberList;
    }

    public void setNumberList(List<java.lang.Number> numberList) {
        this.numberList = numberList;
    }

}
