package nested_input_data_imports;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Model C Decision based on Bs"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ModelCDecisionBasedOnBsInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String model_b2_modela_personName;
    private String model_b_modela_personName;

    public ModelCDecisionBasedOnBsInput_() {
    }

    public ModelCDecisionBasedOnBsInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object model_b2_modela_personName = input_.get("Model B2.modelA.Person name");
            setModel_b2_modela_personName((String)model_b2_modela_personName);
            Object model_b_modela_personName = input_.get("Model B.modelA.Person name");
            setModel_b_modela_personName((String)model_b_modela_personName);
        }
    }

    public String getModel_b2_modela_personName() {
        return this.model_b2_modela_personName;
    }

    public void setModel_b2_modela_personName(String model_b2_modela_personName) {
        this.model_b2_modela_personName = model_b2_modela_personName;
    }

    public String getModel_b_modela_personName() {
        return this.model_b_modela_personName;
    }

    public void setModel_b_modela_personName(String model_b_modela_personName) {
        this.model_b_modela_personName = model_b_modela_personName;
    }

}
