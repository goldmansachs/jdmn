package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "priceGt10"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PriceGt10Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private decisioninputs.type.TA decisioninputs_structA;

    public PriceGt10Input_() {
    }

    public PriceGt10Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decisioninputs_structA = input_.get("decisionInputs.structA");
            setDecisioninputs_structA(decisioninputs.type.TA.toTA(decisioninputs_structA));
        }
    }

    public decisioninputs.type.TA getDecisioninputs_structA() {
        return this.decisioninputs_structA;
    }

    public void setDecisioninputs_structA(decisioninputs.type.TA decisioninputs_structA) {
        this.decisioninputs_structA = decisioninputs_structA;
    }

}
