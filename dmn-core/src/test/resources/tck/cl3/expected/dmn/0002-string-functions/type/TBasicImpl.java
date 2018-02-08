package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tBasic"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TBasicImpl implements TBasic {
        private Boolean startsWithX;
        private Boolean startsWithB;
        private Boolean endsWithX;
        private Boolean endsWithB;
        private Boolean containsX;
        private Boolean containsB;
        private String substringC1;
        private java.math.BigDecimal stringlength;
        private String uppercase;
        private String lowercase;
        private String substringBeforeB;
        private String substringAfterB;

    public TBasicImpl() {
    }

    public TBasicImpl(Boolean containsB, Boolean containsX, Boolean endsWithB, Boolean endsWithX, String lowercase, Boolean startsWithB, Boolean startsWithX, java.math.BigDecimal stringlength, String substringAfterB, String substringBeforeB, String substringC1, String uppercase) {
        this.setContainsB(containsB);
        this.setContainsX(containsX);
        this.setEndsWithB(endsWithB);
        this.setEndsWithX(endsWithX);
        this.setLowercase(lowercase);
        this.setStartsWithB(startsWithB);
        this.setStartsWithX(startsWithX);
        this.setStringlength(stringlength);
        this.setSubstringAfterB(substringAfterB);
        this.setSubstringBeforeB(substringBeforeB);
        this.setSubstringC1(substringC1);
        this.setUppercase(uppercase);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("containsB")
    public Boolean getContainsB() {
        return this.containsB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("containsB")
    public void setContainsB(Boolean containsB) {
        this.containsB = containsB;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("containsX")
    public Boolean getContainsX() {
        return this.containsX;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("containsX")
    public void setContainsX(Boolean containsX) {
        this.containsX = containsX;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("endsWithB")
    public Boolean getEndsWithB() {
        return this.endsWithB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("endsWithB")
    public void setEndsWithB(Boolean endsWithB) {
        this.endsWithB = endsWithB;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("endsWithX")
    public Boolean getEndsWithX() {
        return this.endsWithX;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("endsWithX")
    public void setEndsWithX(Boolean endsWithX) {
        this.endsWithX = endsWithX;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("lowercase")
    public String getLowercase() {
        return this.lowercase;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("lowercase")
    public void setLowercase(String lowercase) {
        this.lowercase = lowercase;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("startsWithB")
    public Boolean getStartsWithB() {
        return this.startsWithB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("startsWithB")
    public void setStartsWithB(Boolean startsWithB) {
        this.startsWithB = startsWithB;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("startsWithX")
    public Boolean getStartsWithX() {
        return this.startsWithX;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("startsWithX")
    public void setStartsWithX(Boolean startsWithX) {
        this.startsWithX = startsWithX;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("stringlength")
    public java.math.BigDecimal getStringlength() {
        return this.stringlength;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("stringlength")
    public void setStringlength(java.math.BigDecimal stringlength) {
        this.stringlength = stringlength;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("substringAfterB")
    public String getSubstringAfterB() {
        return this.substringAfterB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("substringAfterB")
    public void setSubstringAfterB(String substringAfterB) {
        this.substringAfterB = substringAfterB;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("substringBeforeB")
    public String getSubstringBeforeB() {
        return this.substringBeforeB;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("substringBeforeB")
    public void setSubstringBeforeB(String substringBeforeB) {
        this.substringBeforeB = substringBeforeB;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("substringC1")
    public String getSubstringC1() {
        return this.substringC1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("substringC1")
    public void setSubstringC1(String substringC1) {
        this.substringC1 = substringC1;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("uppercase")
    public String getUppercase() {
        return this.uppercase;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("uppercase")
    public void setUppercase(String uppercase) {
        this.uppercase = uppercase;
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
