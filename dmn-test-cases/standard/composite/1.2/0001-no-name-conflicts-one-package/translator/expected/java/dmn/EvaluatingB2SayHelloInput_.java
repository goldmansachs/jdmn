
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "evaluatingB2SayHello"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class EvaluatingB2SayHelloInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String personName;

    public EvaluatingB2SayHelloInput_() {
    }

    public EvaluatingB2SayHelloInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object personName = input_.get("Person Name");
            setPersonName((String)personName);
        }
    }

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

}
