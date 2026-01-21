
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private Boolean employed;
        private type.Person person;

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
