
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "D"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate date;
    private type.Person person;

    public DInput_() {
    }

    public DInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object date = input_.get("https://kie.org/dmn/_33CD912B-9FDA-4DE2-ABB1-C02212AECEAB#Date");
            setDate((java.time.LocalDate)date);
            Object person = input_.get("https://kie.org/dmn/_33CD912B-9FDA-4DE2-ABB1-C02212AECEAB#Person");
            setPerson(type.Person.toPerson(person));
        }
    }

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
