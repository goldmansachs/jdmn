package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "incompleteDecisionTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class IncompleteDecisionTableImpl implements IncompleteDecisionTable {
        private String a;
        private java.math.BigDecimal b;

    public IncompleteDecisionTableImpl() {
    }

    public IncompleteDecisionTableImpl(String a, java.math.BigDecimal b) {
        this.setA(a);
        this.setB(b);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("a")
    public String getA() {
        return this.a;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("a")
    public void setA(String a) {
        this.a = a;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("b")
    public java.math.BigDecimal getB() {
        return this.b;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("b")
    public void setB(java.math.BigDecimal b) {
        this.b = b;
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
