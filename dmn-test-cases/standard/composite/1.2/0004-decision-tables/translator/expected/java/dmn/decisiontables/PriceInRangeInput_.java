package decisiontables;

import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "priceInRange"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PriceInRangeInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number decisioninputs_numB;
    private java.lang.Number decisioninputs_numC;
    private decisioninputs.type.TA decisioninputs_structA;

    public java.lang.Number getDecisioninputs_numB() {
        return this.decisioninputs_numB;
    }

    public void setDecisioninputs_numB(java.lang.Number decisioninputs_numB) {
        this.decisioninputs_numB = decisioninputs_numB;
    }

    public java.lang.Number getDecisioninputs_numC() {
        return this.decisioninputs_numC;
    }

    public void setDecisioninputs_numC(java.lang.Number decisioninputs_numC) {
        this.decisioninputs_numC = decisioninputs_numC;
    }

    public decisioninputs.type.TA getDecisioninputs_structA() {
        return this.decisioninputs_structA;
    }

    public void setDecisioninputs_structA(decisioninputs.type.TA decisioninputs_structA) {
        this.decisioninputs_structA = decisioninputs_structA;
    }

}
