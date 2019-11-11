package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "compoundOutputDecision"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.CompoundOutputDecisionImpl.class)
public interface CompoundOutputDecision extends com.gs.dmn.runtime.DMNType {
    static CompoundOutputDecision toCompoundOutputDecision(Object other) {
        if (other == null) {
            return null;
        } else if (CompoundOutputDecision.class.isAssignableFrom(other.getClass())) {
            return (CompoundOutputDecision)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            CompoundOutputDecisionImpl result_ = new CompoundOutputDecisionImpl();
            result_.setFirstOutput((String)((com.gs.dmn.runtime.Context)other).get("firstOutput", "First Output"));
            result_.setSecondOutput((String)((com.gs.dmn.runtime.Context)other).get("secondOutput", "Second Output"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toCompoundOutputDecision(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), CompoundOutputDecision.class.getSimpleName()));
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

        CompoundOutputDecision other = (CompoundOutputDecision) o;
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
