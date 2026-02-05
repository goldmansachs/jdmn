
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number age;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object age = input_.get("Age");
            setAge((java.lang.Number)age);
        }
    }

    public java.lang.Number getAge() {
        return this.age;
    }

    public void setAge(java.lang.Number age) {
        this.age = age;
    }

}
