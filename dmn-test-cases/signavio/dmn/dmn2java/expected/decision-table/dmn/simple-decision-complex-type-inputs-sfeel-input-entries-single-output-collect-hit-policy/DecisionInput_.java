
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private Boolean employed;
    private type.Person person;

    public DecisionInput_() {
    }

    public DecisionInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object employed = input_.get("http://www.provider.com/dmn/1.1/diagram/2798baf8f6de4bd3b984e599fa9ff016.xml#employed");
            setEmployed((Boolean)employed);
            Object person = input_.get("http://www.provider.com/dmn/1.1/diagram/2798baf8f6de4bd3b984e599fa9ff016.xml#person");
            setPerson(type.Person.toPerson(null));
        }
    }

    public Boolean getEmployed() {
        return this.employed;
    }

    public void setEmployed(Boolean employed) {
        this.employed = employed;
    }

    public type.Person getPerson() {
        return this.person;
    }

    public void setPerson(type.Person person) {
        this.person = person;
    }

}
