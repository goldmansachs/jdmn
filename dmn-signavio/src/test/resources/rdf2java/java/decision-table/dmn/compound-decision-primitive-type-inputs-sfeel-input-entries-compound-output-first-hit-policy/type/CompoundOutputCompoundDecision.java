package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "compoundOutputCompoundDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.CompoundOutputCompoundDecisionImpl.class)
public interface CompoundOutputCompoundDecision extends com.gs.dmn.runtime.DMNType {
    static CompoundOutputCompoundDecision toCompoundOutputCompoundDecision(Object other) {
        if (other == null) {
            return null;
        } else if (CompoundOutputCompoundDecision.class.isAssignableFrom(other.getClass())) {
            return (CompoundOutputCompoundDecision)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            CompoundOutputCompoundDecisionImpl result_ = new CompoundOutputCompoundDecisionImpl();
            result_.setFirstOutput((String)((com.gs.dmn.runtime.Context)other).get("firstOutput", "First Output"));
            result_.setSecondOutput((String)((com.gs.dmn.runtime.Context)other).get("secondOutput", "Second Output"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toCompoundOutputCompoundDecision(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), CompoundOutputCompoundDecision.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("First Output")
    String getFirstOutput();

    @com.fasterxml.jackson.annotation.JsonGetter("Second Output")
    String getSecondOutput();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("firstOutput", getFirstOutput());
        context.put("secondOutput", getSecondOutput());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompoundOutputCompoundDecision other = (CompoundOutputCompoundDecision) o;
        if (this.getFirstOutput() != null ? !this.getFirstOutput().equals(other.getFirstOutput()) : other.getFirstOutput() != null) return false;
        if (this.getSecondOutput() != null ? !this.getSecondOutput().equals(other.getSecondOutput()) : other.getSecondOutput() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getFirstOutput() != null ? this.getFirstOutput().hashCode() : 0);
        result = 31 * result + (this.getSecondOutput() != null ? this.getSecondOutput().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("First Output=" + getFirstOutput());
        result_.append(", Second Output=" + getSecondOutput());
        result_.append("}");
        return result_.toString();
    }
}
