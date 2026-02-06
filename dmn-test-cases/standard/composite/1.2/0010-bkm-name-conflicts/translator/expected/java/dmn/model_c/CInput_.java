package model_c;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "c"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number aa;
    private String ba;

    public CInput_() {
    }

    public CInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object aa = input_.get("aa");
            setAa((java.lang.Number)aa);
            Object ba = input_.get("ba");
            setBa((String)ba);
        }
    }

    public java.lang.Number getAa() {
        return this.aa;
    }

    public void setAa(java.lang.Number aa) {
        this.aa = aa;
    }

    public String getBa() {
        return this.ba;
    }

    public void setBa(String ba) {
        this.ba = ba;
    }

}
