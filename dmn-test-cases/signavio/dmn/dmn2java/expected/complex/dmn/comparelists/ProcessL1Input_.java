
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "processL1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ProcessL1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<java.lang.Number> l1;
    private List<java.lang.Number> l23;

    public ProcessL1Input_() {
    }

    public ProcessL1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object l1 = input_.get("http://www.provider.com/dmn/1.1/diagram/b01e263f2b324caab26b2040a56f8ed1.xml#l1");
            setL1((List<java.lang.Number>)l1);
            Object l23 = input_.get("http://www.provider.com/dmn/1.1/diagram/b01e263f2b324caab26b2040a56f8ed1.xml#l23");
            setL23((List<java.lang.Number>)l23);
        }
    }

    public List<java.lang.Number> getL1() {
        return this.l1;
    }

    public void setL1(List<java.lang.Number> l1) {
        this.l1 = l1;
    }

    public List<java.lang.Number> getL23() {
        return this.l23;
    }

    public void setL23(List<java.lang.Number> l23) {
        this.l23 = l23;
    }

}
