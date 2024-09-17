package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "zip"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ZipImpl implements Zip {
        private java.lang.Number n;
        private String e;
        private String t;

    public ZipImpl() {
    }

    public ZipImpl(String e, java.lang.Number n, String t) {
        this.setE(e);
        this.setN(n);
        this.setT(t);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("e")
    public String getE() {
        return this.e;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("e")
    public void setE(String e) {
        this.e = e;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("n")
    public java.lang.Number getN() {
        return this.n;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("n")
    public void setN(java.lang.Number n) {
        this.n = n;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("t")
    public String getT() {
        return this.t;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("t")
    public void setT(String t) {
        this.t = t;
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
