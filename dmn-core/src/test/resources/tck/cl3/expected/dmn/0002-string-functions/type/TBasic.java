package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tBasic"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TBasic extends com.gs.dmn.runtime.DMNType {
    static TBasic toTBasic(Object other) {
        if (other == null) {
            return null;
        } else if (TBasic.class.isAssignableFrom(other.getClass())) {
            return (TBasic)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TBasicImpl result_ = new TBasicImpl();
            result_.setStartsWithX((Boolean)((com.gs.dmn.runtime.Context)other).get("startsWithX"));
            result_.setStartsWithB((Boolean)((com.gs.dmn.runtime.Context)other).get("startsWithB"));
            result_.setEndsWithX((Boolean)((com.gs.dmn.runtime.Context)other).get("endsWithX"));
            result_.setEndsWithB((Boolean)((com.gs.dmn.runtime.Context)other).get("endsWithB"));
            result_.setContainsX((Boolean)((com.gs.dmn.runtime.Context)other).get("containsX"));
            result_.setContainsB((Boolean)((com.gs.dmn.runtime.Context)other).get("containsB"));
            result_.setSubstringC1((String)((com.gs.dmn.runtime.Context)other).get("substringC1"));
            result_.setStringlength((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("stringlength"));
            result_.setUppercase((String)((com.gs.dmn.runtime.Context)other).get("uppercase"));
            result_.setLowercase((String)((com.gs.dmn.runtime.Context)other).get("lowercase"));
            result_.setSubstringBeforeB((String)((com.gs.dmn.runtime.Context)other).get("substringBeforeB"));
            result_.setSubstringAfterB((String)((com.gs.dmn.runtime.Context)other).get("substringAfterB"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTBasic(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TBasic.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("startsWithX")
    Boolean getStartsWithX();

    @com.fasterxml.jackson.annotation.JsonGetter("startsWithB")
    Boolean getStartsWithB();

    @com.fasterxml.jackson.annotation.JsonGetter("endsWithX")
    Boolean getEndsWithX();

    @com.fasterxml.jackson.annotation.JsonGetter("endsWithB")
    Boolean getEndsWithB();

    @com.fasterxml.jackson.annotation.JsonGetter("containsX")
    Boolean getContainsX();

    @com.fasterxml.jackson.annotation.JsonGetter("containsB")
    Boolean getContainsB();

    @com.fasterxml.jackson.annotation.JsonGetter("substringC1")
    String getSubstringC1();

    @com.fasterxml.jackson.annotation.JsonGetter("stringlength")
    java.math.BigDecimal getStringlength();

    @com.fasterxml.jackson.annotation.JsonGetter("uppercase")
    String getUppercase();

    @com.fasterxml.jackson.annotation.JsonGetter("lowercase")
    String getLowercase();

    @com.fasterxml.jackson.annotation.JsonGetter("substringBeforeB")
    String getSubstringBeforeB();

    @com.fasterxml.jackson.annotation.JsonGetter("substringAfterB")
    String getSubstringAfterB();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("startsWithX", getStartsWithX());
        context.put("startsWithB", getStartsWithB());
        context.put("endsWithX", getEndsWithX());
        context.put("endsWithB", getEndsWithB());
        context.put("containsX", getContainsX());
        context.put("containsB", getContainsB());
        context.put("substringC1", getSubstringC1());
        context.put("stringlength", getStringlength());
        context.put("uppercase", getUppercase());
        context.put("lowercase", getLowercase());
        context.put("substringBeforeB", getSubstringBeforeB());
        context.put("substringAfterB", getSubstringAfterB());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TBasic other = (TBasic) o;
        if (this.getContainsB() != null ? !this.getContainsB().equals(other.getContainsB()) : other.getContainsB() != null) return false;
        if (this.getContainsX() != null ? !this.getContainsX().equals(other.getContainsX()) : other.getContainsX() != null) return false;
        if (this.getEndsWithB() != null ? !this.getEndsWithB().equals(other.getEndsWithB()) : other.getEndsWithB() != null) return false;
        if (this.getEndsWithX() != null ? !this.getEndsWithX().equals(other.getEndsWithX()) : other.getEndsWithX() != null) return false;
        if (this.getLowercase() != null ? !this.getLowercase().equals(other.getLowercase()) : other.getLowercase() != null) return false;
        if (this.getStartsWithB() != null ? !this.getStartsWithB().equals(other.getStartsWithB()) : other.getStartsWithB() != null) return false;
        if (this.getStartsWithX() != null ? !this.getStartsWithX().equals(other.getStartsWithX()) : other.getStartsWithX() != null) return false;
        if (this.getStringlength() != null ? !this.getStringlength().equals(other.getStringlength()) : other.getStringlength() != null) return false;
        if (this.getSubstringAfterB() != null ? !this.getSubstringAfterB().equals(other.getSubstringAfterB()) : other.getSubstringAfterB() != null) return false;
        if (this.getSubstringBeforeB() != null ? !this.getSubstringBeforeB().equals(other.getSubstringBeforeB()) : other.getSubstringBeforeB() != null) return false;
        if (this.getSubstringC1() != null ? !this.getSubstringC1().equals(other.getSubstringC1()) : other.getSubstringC1() != null) return false;
        if (this.getUppercase() != null ? !this.getUppercase().equals(other.getUppercase()) : other.getUppercase() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getContainsB() != null ? this.getContainsB().hashCode() : 0);
        result = 31 * result + (this.getContainsX() != null ? this.getContainsX().hashCode() : 0);
        result = 31 * result + (this.getEndsWithB() != null ? this.getEndsWithB().hashCode() : 0);
        result = 31 * result + (this.getEndsWithX() != null ? this.getEndsWithX().hashCode() : 0);
        result = 31 * result + (this.getLowercase() != null ? this.getLowercase().hashCode() : 0);
        result = 31 * result + (this.getStartsWithB() != null ? this.getStartsWithB().hashCode() : 0);
        result = 31 * result + (this.getStartsWithX() != null ? this.getStartsWithX().hashCode() : 0);
        result = 31 * result + (this.getStringlength() != null ? this.getStringlength().hashCode() : 0);
        result = 31 * result + (this.getSubstringAfterB() != null ? this.getSubstringAfterB().hashCode() : 0);
        result = 31 * result + (this.getSubstringBeforeB() != null ? this.getSubstringBeforeB().hashCode() : 0);
        result = 31 * result + (this.getSubstringC1() != null ? this.getSubstringC1().hashCode() : 0);
        result = 31 * result + (this.getUppercase() != null ? this.getUppercase().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("containsB=" + getContainsB());
        result_.append(", containsX=" + getContainsX());
        result_.append(", endsWithB=" + getEndsWithB());
        result_.append(", endsWithX=" + getEndsWithX());
        result_.append(", lowercase=" + getLowercase());
        result_.append(", startsWithB=" + getStartsWithB());
        result_.append(", startsWithX=" + getStartsWithX());
        result_.append(", stringlength=" + getStringlength());
        result_.append(", substringAfterB=" + getSubstringAfterB());
        result_.append(", substringBeforeB=" + getSubstringBeforeB());
        result_.append(", substringC1=" + getSubstringC1());
        result_.append(", uppercase=" + getUppercase());
        result_.append("}");
        return result_.toString();
    }
}
