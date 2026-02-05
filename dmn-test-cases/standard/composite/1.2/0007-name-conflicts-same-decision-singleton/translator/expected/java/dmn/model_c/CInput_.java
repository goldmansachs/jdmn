package model_c;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "c"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String model_a_a;
    private String model_b_a;

    public CInput_() {
    }

    public CInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object model_a_a = input_.get("Model A.A");
            setModel_a_a((String)model_a_a);
            Object model_b_a = input_.get("Model B.A");
            setModel_b_a((String)model_b_a);
        }
    }

    public String getModel_a_a() {
        return this.model_a_a;
    }

    public void setModel_a_a(String model_a_a) {
        this.model_a_a = model_a_a;
    }

    public String getModel_b_a() {
        return this.model_b_a;
    }

    public void setModel_b_a(String model_b_a) {
        this.model_b_a = model_b_a;
    }

}
