
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "keepInputallFalse"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class KeepInputallFalseInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanAllFalse_iterator;

    public KeepInputallFalseInput_() {
    }

    public KeepInputallFalseInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanAllFalse_iterator = input_.get("booleanAllFalse");
            setBooleanAllFalse_iterator((Boolean)booleanAllFalse_iterator);
        }
    }

    public Boolean getBooleanAllFalse_iterator() {
        return this.booleanAllFalse_iterator;
    }

    public void setBooleanAllFalse_iterator(Boolean booleanAllFalse_iterator) {
        this.booleanAllFalse_iterator = booleanAllFalse_iterator;
    }

}
