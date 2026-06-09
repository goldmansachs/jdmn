
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "body"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BodyInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.It it_iterator;

    public BodyInput_() {
    }

    public BodyInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object it_iterator = input_.get("http://www.provider.com/dmn/1.1/diagram/2798610dcc0f4068861fcb0f4af25ac7.xml#it_iterator");
            setIt_iterator(type.It.toIt(null));
        }
    }

    public type.It getIt_iterator() {
        return this.it_iterator;
    }

    public void setIt_iterator(type.It it_iterator) {
        this.it_iterator = it_iterator;
    }

}
