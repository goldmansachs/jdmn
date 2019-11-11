package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "compile"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.CompileImpl.class)
public interface Compile extends com.gs.dmn.runtime.DMNType {
    static Compile toCompile(Object other) {
        if (other == null) {
            return null;
        } else if (Compile.class.isAssignableFrom(other.getClass())) {
            return (Compile)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            CompileImpl result_ = new CompileImpl();
            result_.setNextTrafficLight((String)((com.gs.dmn.runtime.Context)other).get("nextTrafficLight", "Next traffic light"));
            result_.setAvgOfNumbers((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("avgOfNumbers", "avg of numbers"));
            result_.setName((String)((com.gs.dmn.runtime.Context)other).get("name", "name"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toCompile(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Compile.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Next traffic light")
    String getNextTrafficLight();

    @com.fasterxml.jackson.annotation.JsonGetter("avg of numbers")
    java.math.BigDecimal getAvgOfNumbers();

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("nextTrafficLight", getNextTrafficLight());
        context.put("avgOfNumbers", getAvgOfNumbers());
        context.put("name", getName());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compile other = (Compile) o;
        if (this.getAvgOfNumbers() != null ? !this.getAvgOfNumbers().equals(other.getAvgOfNumbers()) : other.getAvgOfNumbers() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;
        if (this.getNextTrafficLight() != null ? !this.getNextTrafficLight().equals(other.getNextTrafficLight()) : other.getNextTrafficLight() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAvgOfNumbers() != null ? this.getAvgOfNumbers().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = 31 * result + (this.getNextTrafficLight() != null ? this.getNextTrafficLight().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("avg of numbers=" + getAvgOfNumbers());
        result_.append(", name=" + getName());
        result_.append(", Next traffic light=" + getNextTrafficLight());
        result_.append("}");
        return result_.toString();
    }
}
