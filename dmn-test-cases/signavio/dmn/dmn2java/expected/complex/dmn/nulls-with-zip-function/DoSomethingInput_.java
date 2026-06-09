
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "doSomething"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DoSomethingInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Zip3 zip4_iterator;

    public DoSomethingInput_() {
    }

    public DoSomethingInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object zip4_iterator = input_.get("http://www.provider.com/dmn/1.1/diagram/7bf105649e8445b39cb4d936497fbc1c.xml#zip4_iterator");
            setZip4_iterator(type.Zip3.toZip3(null));
        }
    }

    public type.Zip3 getZip4_iterator() {
        return this.zip4_iterator;
    }

    public void setZip4_iterator(type.Zip3 zip4_iterator) {
        this.zip4_iterator = zip4_iterator;
    }

}
