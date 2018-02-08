package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tEmployeeTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TEmployeeTable extends com.gs.dmn.runtime.DMNType {
    static TEmployeeTable toTEmployeeTable(Object other) {
        if (other == null) {
            return null;
        } else if (TEmployeeTable.class.isAssignableFrom(other.getClass())) {
            return (TEmployeeTable)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TEmployeeTableImpl result_ = new TEmployeeTableImpl();
            result_.setId((String)((com.gs.dmn.runtime.Context)other).get("id"));
            result_.setName((String)((com.gs.dmn.runtime.Context)other).get("name"));
            result_.setDeptNum((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("deptNum"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTEmployeeTable(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TEmployeeTable.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("id")
    String getId();

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("deptNum")
    java.math.BigDecimal getDeptNum();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("id", getId());
        context.put("name", getName());
        context.put("deptNum", getDeptNum());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TEmployeeTable other = (TEmployeeTable) o;
        if (this.getDeptNum() != null ? !this.getDeptNum().equals(other.getDeptNum()) : other.getDeptNum() != null) return false;
        if (this.getId() != null ? !this.getId().equals(other.getId()) : other.getId() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getDeptNum() != null ? this.getDeptNum().hashCode() : 0);
        result = 31 * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("deptNum=" + getDeptNum());
        result_.append(", id=" + getId());
        result_.append(", name=" + getName());
        result_.append("}");
        return result_.toString();
    }
}
