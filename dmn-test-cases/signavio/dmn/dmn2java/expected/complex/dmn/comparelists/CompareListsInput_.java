
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "compareLists"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompareListsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number l12_iterator;
    private java.lang.Number l2_iterator;

    public CompareListsInput_() {
    }

    public CompareListsInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object l12_iterator = input_.get("http://www.provider.com/dmn/1.1/diagram/b01e263f2b324caab26b2040a56f8ed1.xml#l12_iterator");
            setL12_iterator((java.lang.Number)l12_iterator);
            Object l2_iterator = input_.get("http://www.provider.com/dmn/1.1/diagram/b01e263f2b324caab26b2040a56f8ed1.xml#l2_iterator");
            setL2_iterator((java.lang.Number)l2_iterator);
        }
    }

    public java.lang.Number getL12_iterator() {
        return this.l12_iterator;
    }

    public void setL12_iterator(java.lang.Number l12_iterator) {
        this.l12_iterator = l12_iterator;
    }

    public java.lang.Number getL2_iterator() {
        return this.l2_iterator;
    }

    public void setL2_iterator(java.lang.Number l2_iterator) {
        this.l2_iterator = l2_iterator;
    }

}
