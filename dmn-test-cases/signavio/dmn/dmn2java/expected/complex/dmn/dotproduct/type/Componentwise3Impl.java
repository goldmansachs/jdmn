package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "componentwise3"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Componentwise3Impl implements Componentwise3 {
        private java.lang.Number a;
        private java.lang.Number b;

    public Componentwise3Impl() {
    }

    public Componentwise3Impl(java.lang.Number a, java.lang.Number b) {
        this.setA(a);
        this.setB(b);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("A")
    public java.lang.Number getA() {
        return this.a;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("A")
    public void setA(java.lang.Number a) {
        this.a = a;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("B")
    public java.lang.Number getB() {
        return this.b;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("B")
    public void setB(java.lang.Number b) {
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
