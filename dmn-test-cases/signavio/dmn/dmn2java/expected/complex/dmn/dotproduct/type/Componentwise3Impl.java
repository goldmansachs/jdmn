package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "componentwise3"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Componentwise3Impl implements Componentwise3 {
        private java.math.BigDecimal a;
        private java.math.BigDecimal b;

    public Componentwise3Impl() {
    }

    public Componentwise3Impl(java.math.BigDecimal a, java.math.BigDecimal b) {
        this.setA(a);
        this.setB(b);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("A")
    public java.math.BigDecimal getA() {
        return this.a;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("A")
    public void setA(java.math.BigDecimal a) {
        this.a = a;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("B")
    public java.math.BigDecimal getB() {
        return this.b;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("B")
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
