package parent.type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "ParentPerson"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = parent.type.ParentPersonImpl.class)
public interface ParentPerson extends com.gs.dmn.runtime.DMNType {
    static ParentPerson toParentPerson(Object other) {
        if (other == null) {
            return null;
        } else if (ParentPerson.class.isAssignableFrom(other.getClass())) {
            return (ParentPerson)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ParentPersonImpl result_ = new ParentPersonImpl();
            Object name = ((com.gs.dmn.runtime.Context)other).get("name");
            result_.setName((String)name);
            Object age = ((com.gs.dmn.runtime.Context)other).get("age");
            result_.setAge((java.lang.Number)age);
            Object addresses = ((com.gs.dmn.runtime.Context)other).get("addresses");
            result_.setAddresses((List<parent.type.ParentAddress>)((java.util.List)addresses).stream().map(x_ -> parent.type.ParentAddress.toParentAddress(x_)).collect(java.util.stream.Collectors.toList()));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toParentPerson(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), ParentPerson.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("age")
    java.lang.Number getAge();

    @com.fasterxml.jackson.annotation.JsonGetter("addresses")
    List<parent.type.ParentAddress> getAddresses();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.add("name", getName());
        context.add("age", getAge());
        context.add("addresses", getAddresses());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentPerson other = (ParentPerson) o;
        if (this.getAddresses() != null ? !this.getAddresses().equals(other.getAddresses()) : other.getAddresses() != null) return false;
        if (this.getAge() != null ? !this.getAge().equals(other.getAge()) : other.getAge() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAddresses() != null ? this.getAddresses().hashCode() : 0);
        result = 31 * result + (this.getAge() != null ? this.getAge().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("addresses=" + getAddresses());
        result_.append(", age=" + getAge());
        result_.append(", name=" + getName());
        result_.append("}");
        return result_.toString();
    }
}
