package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "dependentDecision1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.DependentDecision1Impl.class)
public interface DependentDecision1 extends com.gs.dmn.runtime.DMNType {
    static DependentDecision1 toDependentDecision1(Object other) {
        if (other == null) {
            return null;
        } else if (DependentDecision1.class.isAssignableFrom(other.getClass())) {
            return (DependentDecision1)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            DependentDecision1Impl result_ = new DependentDecision1Impl();
            result_.setDD1O1((String)((com.gs.dmn.runtime.Context)other).get("dD1O1", "DD1O1"));
            result_.setDD1O2((String)((com.gs.dmn.runtime.Context)other).get("dD1O2", "DD1O2"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toDependentDecision1(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), DependentDecision1.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DD1O1")
    String getDD1O1();

    @com.fasterxml.jackson.annotation.JsonGetter("DD1O2")
    String getDD1O2();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("dD1O1", getDD1O1());
        context.put("dD1O2", getDD1O2());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DependentDecision1 other = (DependentDecision1) o;
        if (this.getDD1O1() != null ? !this.getDD1O1().equals(other.getDD1O1()) : other.getDD1O1() != null) return false;
        if (this.getDD1O2() != null ? !this.getDD1O2().equals(other.getDD1O2()) : other.getDD1O2() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDD1O1() != null ? this.getDD1O1().hashCode() : 0);
        result = 31 * result + (this.getDD1O2() != null ? this.getDD1O2().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("DD1O1=" + getDD1O1());
        result_.append(", DD1O2=" + getDD1O2());
        result_.append("}");
        return result_.toString();
    }
}
