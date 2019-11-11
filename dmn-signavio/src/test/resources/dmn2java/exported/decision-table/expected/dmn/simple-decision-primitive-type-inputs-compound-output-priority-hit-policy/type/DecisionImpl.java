package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DecisionImpl implements Decision {
        private String output1;
        private String output2;

    public DecisionImpl() {
    }

    public DecisionImpl(String output1, String output2) {
        this.setOutput1(output1);
        this.setOutput2(output2);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Output1")
    public String getOutput1() {
        return this.output1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Output1")
    public void setOutput1(String output1) {
        this.output1 = output1;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Output2")
    public String getOutput2() {
        return this.output2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Output2")
    public void setOutput2(String output2) {
        this.output2 = output2;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
