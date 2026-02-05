package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "priceGt10"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PriceGt10Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private decisioninputs1.type.TA decisioninputs1_structA;

    public PriceGt10Input_() {
    }

    public PriceGt10Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decisioninputs1_structA = input_.get("decisionInputs1.structA");
            setDecisioninputs1_structA(decisioninputs1.type.TA.toTA(decisioninputs1_structA));
        }
    }

    public decisioninputs1.type.TA getDecisioninputs1_structA() {
        return this.decisioninputs1_structA;
    }

    public void setDecisioninputs1_structA(decisioninputs1.type.TA decisioninputs1_structA) {
        this.decisioninputs1_structA = decisioninputs1_structA;
    }

}
