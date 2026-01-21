package model_a;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Greet the Person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class GreetThePersonInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private String personName;

    public String getPersonName() {
        return this.personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

}
