package model_b;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bkm"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BkmInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private String x;

    public BkmInput_() {
    }

    public BkmInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object x = input_.get("x");
            setX((String)x);
        }
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

}
