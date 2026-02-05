package model_a;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Greet the Person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class GreetThePersonInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String personName;

    public GreetThePersonInput_() {
    }

    public GreetThePersonInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object personName = input_.get("Person name");
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
