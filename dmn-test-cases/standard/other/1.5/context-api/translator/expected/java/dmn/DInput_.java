
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "D"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private java.time.LocalDate date;
        private type.Person person;

    public java.time.LocalDate getDate() {
        return this.date;
    }

    public void setDate(java.time.LocalDate date) {
        this.date = date;
    }

    public type.Person getPerson() {
        return this.person;
    }

    public void setPerson(type.Person person) {
        this.person = person;
    }

}
