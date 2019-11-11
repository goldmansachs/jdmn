package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "dotProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DotProductImpl implements DotProduct {
        private java.math.BigDecimal dotProduct2;
        private String outputMessage;

    public DotProductImpl() {
    }

    public DotProductImpl(java.math.BigDecimal dotProduct2, String outputMessage) {
        this.setDotProduct2(dotProduct2);
        this.setOutputMessage(outputMessage);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DotProduct")
    public java.math.BigDecimal getDotProduct2() {
        return this.dotProduct2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DotProduct")
    public void setDotProduct2(java.math.BigDecimal dotProduct2) {
        this.dotProduct2 = dotProduct2;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Output Message")
    public String getOutputMessage() {
        return this.outputMessage;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Output Message")
    public void setOutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
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
