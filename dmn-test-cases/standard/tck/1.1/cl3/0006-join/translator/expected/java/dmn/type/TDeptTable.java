package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tDeptTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TDeptTableImpl.class)
public interface TDeptTable extends com.gs.dmn.runtime.DMNType {
    static TDeptTable toTDeptTable(Object other) {
        if (other == null) {
            return null;
        } else if (TDeptTable.class.isAssignableFrom(other.getClass())) {
            return (TDeptTable)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TDeptTableImpl result_ = new TDeptTableImpl();
            Object number = ((com.gs.dmn.runtime.Context)other).get("number");
            result_.setNumber((java.lang.Number)number);
            Object name = ((com.gs.dmn.runtime.Context)other).get("name");
            result_.setName((String)name);
            Object manager = ((com.gs.dmn.runtime.Context)other).get("manager");
            result_.setManager((String)manager);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTDeptTable(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TDeptTable.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("number")
    java.lang.Number getNumber();

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("manager")
    String getManager();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("number", getNumber());
        context.put("name", getName());
        context.put("manager", getManager());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TDeptTable other = (TDeptTable) o;
        if (this.getManager() != null ? !this.getManager().equals(other.getManager()) : other.getManager() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;
        if (this.getNumber() != null ? !this.getNumber().equals(other.getNumber()) : other.getNumber() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getManager() != null ? this.getManager().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = 31 * result + (this.getNumber() != null ? this.getNumber().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("manager=" + getManager());
        result_.append(", name=" + getName());
        result_.append(", number=" + getNumber());
        result_.append("}");
        return result_.toString();
    }
}
