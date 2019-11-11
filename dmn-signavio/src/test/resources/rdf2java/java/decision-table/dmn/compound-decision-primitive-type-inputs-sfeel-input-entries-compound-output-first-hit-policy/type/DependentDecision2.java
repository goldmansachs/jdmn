package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "dependentDecision2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.DependentDecision2Impl.class)
public interface DependentDecision2 extends com.gs.dmn.runtime.DMNType {
    static DependentDecision2 toDependentDecision2(Object other) {
        if (other == null) {
            return null;
        } else if (DependentDecision2.class.isAssignableFrom(other.getClass())) {
            return (DependentDecision2)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            DependentDecision2Impl result_ = new DependentDecision2Impl();
            result_.setDD2O1((String)((com.gs.dmn.runtime.Context)other).get("dD2O1", "DD2O1"));
            result_.setDD2O2((String)((com.gs.dmn.runtime.Context)other).get("dD2O2", "DD2O2"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toDependentDecision2(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), DependentDecision2.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DD2O1")
    String getDD2O1();

    @com.fasterxml.jackson.annotation.JsonGetter("DD2O2")
    String getDD2O2();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("dD2O1", getDD2O1());
        context.put("dD2O2", getDD2O2());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DependentDecision2 other = (DependentDecision2) o;
        if (this.getDD2O1() != null ? !this.getDD2O1().equals(other.getDD2O1()) : other.getDD2O1() != null) return false;
        if (this.getDD2O2() != null ? !this.getDD2O2().equals(other.getDD2O2()) : other.getDD2O2() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDD2O1() != null ? this.getDD2O1().hashCode() : 0);
        result = 31 * result + (this.getDD2O2() != null ? this.getDD2O2().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("DD2O1=" + getDD2O1());
        result_.append(", DD2O2=" + getDD2O2());
        result_.append("}");
        return result_.toString();
    }
}
