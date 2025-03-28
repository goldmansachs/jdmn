package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "zip3"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Zip3Impl implements Zip3 {
        private String inputA;
        private java.lang.Number inputB;

    public Zip3Impl() {
    }

    public Zip3Impl(String inputA, java.lang.Number inputB) {
        this.setInputA(inputA);
        this.setInputB(inputB);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("inputA")
    public String getInputA() {
        return this.inputA;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("inputA")
    public void setInputA(String inputA) {
        this.inputA = inputA;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("inputB")
    public java.lang.Number getInputB() {
        return this.inputB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("inputB")
    public void setInputB(java.lang.Number inputB) {
        this.inputB = inputB;
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
