
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "keepInputanyTrue"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class KeepInputanyTrueInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean booleanAnyTrue_iterator;

    public KeepInputanyTrueInput_() {
    }

    public KeepInputanyTrueInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object booleanAnyTrue_iterator = input_.get("booleanAnyTrue");
            setBooleanAnyTrue_iterator((Boolean)booleanAnyTrue_iterator);
        }
    }

    public Boolean getBooleanAnyTrue_iterator() {
        return this.booleanAnyTrue_iterator;
    }

    public void setBooleanAnyTrue_iterator(Boolean booleanAnyTrue_iterator) {
        this.booleanAnyTrue_iterator = booleanAnyTrue_iterator;
    }

}
