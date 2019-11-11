package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "decision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.DecisionImpl.class)
public interface Decision extends com.gs.dmn.runtime.DMNType {
    static Decision toDecision(Object other) {
        if (other == null) {
            return null;
        } else if (Decision.class.isAssignableFrom(other.getClass())) {
            return (Decision)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            DecisionImpl result_ = new DecisionImpl();
            result_.setOutput1((String)((com.gs.dmn.runtime.Context)other).get("output1", "Output1"));
            result_.setOutput2((String)((com.gs.dmn.runtime.Context)other).get("output2", "Output2"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toDecision(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Decision.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Output1")
    String getOutput1();

    @com.fasterxml.jackson.annotation.JsonGetter("Output2")
    String getOutput2();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("output1", getOutput1());
        context.put("output2", getOutput2());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Decision other = (Decision) o;
        if (this.getOutput1() != null ? !this.getOutput1().equals(other.getOutput1()) : other.getOutput1() != null) return false;
        if (this.getOutput2() != null ? !this.getOutput2().equals(other.getOutput2()) : other.getOutput2() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getOutput1() != null ? this.getOutput1().hashCode() : 0);
        result = 31 * result + (this.getOutput2() != null ? this.getOutput2().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Output1=" + getOutput1());
        result_.append(", Output2=" + getOutput2());
        result_.append("}");
        return result_.toString();
    }
}
