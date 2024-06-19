package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tRow"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TRowImpl implements TRow {
        private java.lang.Number col1;
        private java.lang.Number col2;
        private java.lang.Number col3;
        private java.lang.Number col4;

    public TRowImpl() {
    }

    public TRowImpl(java.lang.Number col1, java.lang.Number col2, java.lang.Number col3, java.lang.Number col4) {
        this.setCol1(col1);
        this.setCol2(col2);
        this.setCol3(col3);
        this.setCol4(col4);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("col1")
    public java.lang.Number getCol1() {
        return this.col1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("col1")
    public void setCol1(java.lang.Number col1) {
        this.col1 = col1;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("col2")
    public java.lang.Number getCol2() {
        return this.col2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("col2")
    public void setCol2(java.lang.Number col2) {
        this.col2 = col2;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("col3")
    public java.lang.Number getCol3() {
        return this.col3;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("col3")
    public void setCol3(java.lang.Number col3) {
        this.col3 = col3;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("col4")
    public java.lang.Number getCol4() {
        return this.col4;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("col4")
    public void setCol4(java.lang.Number col4) {
        this.col4 = col4;
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
