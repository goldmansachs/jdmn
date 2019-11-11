package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ZipImpl implements Zip {
        private String inputA;
        private java.math.BigDecimal inputB;

    public ZipImpl() {
    }

    public ZipImpl(String inputA, java.math.BigDecimal inputB) {
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
    public java.math.BigDecimal getInputB() {
        return this.inputB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("inputB")
    public void setInputB(java.math.BigDecimal inputB) {
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
