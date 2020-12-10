package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "zip3"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.Zip3Impl.class)
public interface Zip3 extends com.gs.dmn.runtime.DMNType {
    static Zip3 toZip3(Object other) {
        if (other == null) {
            return null;
        } else if (Zip3.class.isAssignableFrom(other.getClass())) {
            return (Zip3)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            Zip3Impl result_ = new Zip3Impl();
            result_.setInputA((String)((com.gs.dmn.runtime.Context)other).get("inputA", "inputA"));
            result_.setInputB((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("inputB", "inputB"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toZip3(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Zip3.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("inputA")
    String getInputA();

    @com.fasterxml.jackson.annotation.JsonGetter("inputB")
    java.math.BigDecimal getInputB();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("inputA", getInputA());
        context.put("inputB", getInputB());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zip3 other = (Zip3) o;
        if (this.getInputA() != null ? !this.getInputA().equals(other.getInputA()) : other.getInputA() != null) return false;
        if (this.getInputB() != null ? !this.getInputB().equals(other.getInputB()) : other.getInputB() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getInputA() != null ? this.getInputA().hashCode() : 0);
        result = 31 * result + (this.getInputB() != null ? this.getInputB().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("inputA=" + getInputA());
        result_.append(", inputB=" + getInputB());
        result_.append("}");
        return result_.toString();
    }
}
