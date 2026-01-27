
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "stringHandling"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class StringHandlingInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numberA;
    private java.lang.Number numberB;
    private List<String> stringList;

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
