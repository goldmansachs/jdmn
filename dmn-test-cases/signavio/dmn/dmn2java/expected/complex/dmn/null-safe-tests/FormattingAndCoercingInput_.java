
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "formattingAndCoercing"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class FormattingAndCoercingInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number numberB;
    private String string;

    public FormattingAndCoercingInput_() {
    }

    public FormattingAndCoercingInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object numberB = input_.get("numberB");
            setNumberB((java.lang.Number)numberB);
            Object string = input_.get("string");
            setString((String)string);
        }
    }

    public java.lang.Number getNumberB() {
        return this.numberB;
    }

    public void setNumberB(java.lang.Number numberB) {
        this.numberB = numberB;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }

}
