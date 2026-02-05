
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "processL2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ProcessL2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number l12_iterator;
    private List<java.lang.Number> l23;

    public ProcessL2Input_() {
    }

    public ProcessL2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object l12_iterator = input_.get("L1");
            setL12_iterator((java.lang.Number)l12_iterator);
            Object l23 = input_.get("L2");
            setL23((List<java.lang.Number>)l23);
        }
    }

    public java.lang.Number getL12_iterator() {
        return this.l12_iterator;
    }

    public void setL12_iterator(java.lang.Number l12_iterator) {
        this.l12_iterator = l12_iterator;
    }

    public List<java.lang.Number> getL23() {
        return this.l23;
    }

    public void setL23(List<java.lang.Number> l23) {
        this.l23 = l23;
    }

}
