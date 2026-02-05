package model_b2;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Evaluating B2 Say Hello"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class EvaluatingB2SayHelloInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String modela_personName;

    public EvaluatingB2SayHelloInput_() {
    }

    public EvaluatingB2SayHelloInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object modela_personName = input_.get("modelA.Person name");
            setModela_personName((String)modela_personName);
        }
    }

    public String getModela_personName() {
        return this.modela_personName;
    }

    public void setModela_personName(String modela_personName) {
        this.modela_personName = modela_personName;
    }

}
