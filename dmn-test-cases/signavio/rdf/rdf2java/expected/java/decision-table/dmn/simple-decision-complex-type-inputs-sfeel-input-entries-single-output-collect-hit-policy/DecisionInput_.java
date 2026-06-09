
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
            Object employed = input_.get("http://www.omg.org/spec/DMN/20151101/dmn.xsd#employed");
            setEmployed((Boolean)employed);
            Object person = input_.get("http://www.omg.org/spec/DMN/20151101/dmn.xsd#person");
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
