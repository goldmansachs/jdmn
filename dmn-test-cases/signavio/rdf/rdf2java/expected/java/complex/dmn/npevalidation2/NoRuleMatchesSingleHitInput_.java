
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "noRuleMatchesSingleHit"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class NoRuleMatchesSingleHitInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number second;

    public NoRuleMatchesSingleHitInput_() {
    }

    public NoRuleMatchesSingleHitInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object second = input_.get("second");
            setSecond((java.lang.Number)second);
        }
    }

    public java.lang.Number getSecond() {
        return this.second;
    }

    public void setSecond(java.lang.Number second) {
        this.second = second;
    }

}
