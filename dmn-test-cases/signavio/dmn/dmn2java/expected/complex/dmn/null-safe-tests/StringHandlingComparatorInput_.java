
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "stringHandlingComparator"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class StringHandlingComparatorInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numberA;
    private java.lang.Number numberB;
    private List<String> stringList;

    public StringHandlingComparatorInput_() {
    }

    public StringHandlingComparatorInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberA = input_.get("numberA");
            setNumberA((java.lang.Number)numberA);
            Object numberB = input_.get("numberB");
            setNumberB((java.lang.Number)numberB);
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

    public List<String> getStringList() {
        return this.stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

}
