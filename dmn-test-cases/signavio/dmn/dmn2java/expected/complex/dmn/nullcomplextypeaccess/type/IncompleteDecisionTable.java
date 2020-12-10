package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "incompleteDecisionTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.IncompleteDecisionTableImpl.class)
public interface IncompleteDecisionTable extends com.gs.dmn.runtime.DMNType {
    static IncompleteDecisionTable toIncompleteDecisionTable(Object other) {
        if (other == null) {
            return null;
        } else if (IncompleteDecisionTable.class.isAssignableFrom(other.getClass())) {
            return (IncompleteDecisionTable)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            IncompleteDecisionTableImpl result_ = new IncompleteDecisionTableImpl();
            result_.setA((String)((com.gs.dmn.runtime.Context)other).get("a", "a"));
            result_.setB((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("b", "b"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toIncompleteDecisionTable(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), IncompleteDecisionTable.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("a")
    String getA();

    @com.fasterxml.jackson.annotation.JsonGetter("b")
    java.math.BigDecimal getB();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("a", getA());
        context.put("b", getB());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IncompleteDecisionTable other = (IncompleteDecisionTable) o;
        if (this.getA() != null ? !this.getA().equals(other.getA()) : other.getA() != null) return false;
        if (this.getB() != null ? !this.getB().equals(other.getB()) : other.getB() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getA() != null ? this.getA().hashCode() : 0);
        result = 31 * result + (this.getB() != null ? this.getB().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("a=" + getA());
        result_.append(", b=" + getB());
        result_.append("}");
        return result_.toString();
    }
}
