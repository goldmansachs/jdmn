
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "evaluatingB1SayHello"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class EvaluatingB1SayHelloInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String personName;

    public EvaluatingB1SayHelloInput_() {
    }

    public EvaluatingB1SayHelloInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object personName = input_.get("personName");
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
