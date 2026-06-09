
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "keepInputallTrue"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class KeepInputallTrueInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanAllTrue_iterator;

    public KeepInputallTrueInput_() {
    }

    public KeepInputallTrueInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanAllTrue_iterator = input_.get("http://www.provider.com/dmn/1.1/diagram/7a41c638739441ef88d9fe7501233ef8.xml#booleanAllTrue_iterator");
            setBooleanAllTrue_iterator((Boolean)booleanAllTrue_iterator);
        }
    }

    public Boolean getBooleanAllTrue_iterator() {
        return this.booleanAllTrue_iterator;
    }

    public void setBooleanAllTrue_iterator(Boolean booleanAllTrue_iterator) {
        this.booleanAllTrue_iterator = booleanAllTrue_iterator;
    }

}
