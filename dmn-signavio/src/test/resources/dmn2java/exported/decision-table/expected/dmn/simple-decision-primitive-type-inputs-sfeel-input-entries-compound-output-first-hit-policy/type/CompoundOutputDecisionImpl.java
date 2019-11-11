package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "compoundOutputDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CompoundOutputDecisionImpl implements CompoundOutputDecision {
        private String firstOutput;
        private String secondOutput;

    public CompoundOutputDecisionImpl() {
    }

    public CompoundOutputDecisionImpl(String firstOutput, String secondOutput) {
        this.setFirstOutput(firstOutput);
        this.setSecondOutput(secondOutput);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("First Output")
    public String getFirstOutput() {
        return this.firstOutput;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("First Output")
    public void setFirstOutput(String firstOutput) {
        this.firstOutput = firstOutput;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Second Output")
    public String getSecondOutput() {
        return this.secondOutput;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Second Output")
    public void setSecondOutput(String secondOutput) {
        this.secondOutput = secondOutput;
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
