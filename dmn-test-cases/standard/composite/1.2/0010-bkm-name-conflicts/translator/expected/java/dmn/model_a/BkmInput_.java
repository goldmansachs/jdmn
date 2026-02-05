package model_a;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "bkm"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class BkmInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number x;

    public BkmInput_() {
    }

    public BkmInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object x = input_.get("x");
            setX((java.lang.Number)x);
        }
    }

    public java.lang.Number getX() {
        return this.x;
    }

    public void setX(java.lang.Number x) {
        this.x = x;
    }

}
