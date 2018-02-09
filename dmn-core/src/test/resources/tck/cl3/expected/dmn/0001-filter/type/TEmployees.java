package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tEmployees"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TEmployees extends com.gs.dmn.runtime.DMNType {
    static TEmployees toTEmployees(Object other) {
        if (other == null) {
            return null;
        } else if (TEmployees.class.isAssignableFrom(other.getClass())) {
            return (TEmployees)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TEmployeesImpl result_ = new TEmployeesImpl();
            result_.setId((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("id"));
            result_.setDept((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("dept"));
            result_.setName((String)((com.gs.dmn.runtime.Context)other).get("name"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTEmployees(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TEmployees.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("id")
    java.math.BigDecimal getId();

    @com.fasterxml.jackson.annotation.JsonGetter("dept")
    java.math.BigDecimal getDept();

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("id", getId());
        context.put("dept", getDept());
        context.put("name", getName());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TEmployees other = (TEmployees) o;
        if (this.getDept() != null ? !this.getDept().equals(other.getDept()) : other.getDept() != null) return false;
        if (this.getId() != null ? !this.getId().equals(other.getId()) : other.getId() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDept() != null ? this.getDept().hashCode() : 0);
        result = 31 * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("dept=" + getDept());
        result_.append(", id=" + getId());
        result_.append(", name=" + getName());
        result_.append("}");
        return result_.toString();
    }
}
