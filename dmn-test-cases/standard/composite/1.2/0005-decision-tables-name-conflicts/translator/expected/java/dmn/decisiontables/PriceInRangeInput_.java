package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "priceInRange"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PriceInRangeInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number decisioninputs1_numB;
    private java.lang.Number decisioninputs1_numC;
    private decisioninputs1.type.TA decisioninputs1_structA;

    public PriceInRangeInput_() {
    }

    public PriceInRangeInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object decisioninputs1_numB = input_.get("decisionInputs1.numB");
            setDecisioninputs1_numB((java.lang.Number)decisioninputs1_numB);
            Object decisioninputs1_numC = input_.get("decisionInputs1.numC");
            setDecisioninputs1_numC((java.lang.Number)decisioninputs1_numC);
            Object decisioninputs1_structA = input_.get("decisionInputs1.structA");
            setDecisioninputs1_structA(decisioninputs1.type.TA.toTA(decisioninputs1_structA));
        }
    }

    public java.lang.Number getDecisioninputs1_numB() {
        return this.decisioninputs1_numB;
    }

    public void setDecisioninputs1_numB(java.lang.Number decisioninputs1_numB) {
        this.decisioninputs1_numB = decisioninputs1_numB;
    }

    public java.lang.Number getDecisioninputs1_numC() {
        return this.decisioninputs1_numC;
    }

    public void setDecisioninputs1_numC(java.lang.Number decisioninputs1_numC) {
        this.decisioninputs1_numC = decisioninputs1_numC;
    }

    public decisioninputs1.type.TA getDecisioninputs1_structA() {
        return this.decisioninputs1_structA;
    }

    public void setDecisioninputs1_structA(decisioninputs1.type.TA decisioninputs1_structA) {
        this.decisioninputs1_structA = decisioninputs1_structA;
    }

}
