package model_b1;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "evaluatingB1SayHello"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class EvaluatingB1SayHelloInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String model_a_personName;

    public EvaluatingB1SayHelloInput_() {
    }

    public EvaluatingB1SayHelloInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object model_a_personName = input_.get("model-a.personName");
            setModel_a_personName((String)model_a_personName);
        }
    }

    public String getModel_a_personName() {
        return this.model_a_personName;
    }

    public void setModel_a_personName(String model_a_personName) {
        this.model_a_personName = model_a_personName;
    }

}
