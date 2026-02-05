
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "partB"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PartBInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numberA;
    private java.lang.Number numberB;
    private List<java.lang.Number> numberList;
    private String string;
    private List<String> stringList;

    public PartBInput_() {
    }

    public PartBInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberA = input_.get("numberA");
            setNumberA((java.lang.Number)numberA);
            Object numberB = input_.get("numberB");
            setNumberB((java.lang.Number)numberB);
            Object numberList = input_.get("numberList");
            setNumberList((List<java.lang.Number>)numberList);
            Object string = input_.get("string");
            setString((String)string);
            Object stringList = input_.get("stringList");
            setStringList((List<String>)stringList);
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

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<String> getStringList() {
        return this.stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

}
