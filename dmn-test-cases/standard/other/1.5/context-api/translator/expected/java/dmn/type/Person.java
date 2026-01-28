package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "Person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.PersonImpl.class)
public interface Person extends com.gs.dmn.runtime.DMNType {
    static Person toPerson(Object other) {
        if (other == null) {
            return null;
        } else if (Person.class.isAssignableFrom(other.getClass())) {
            return (Person)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            PersonImpl result_ = new PersonImpl();
            Object name = ((com.gs.dmn.runtime.Context)other).get("name");
            result_.setName((String)name);
            Object addresses = ((com.gs.dmn.runtime.Context)other).get("addresses");
            result_.setAddresses((List<type.Address>)((java.util.List)addresses).stream().map(x_ -> type.Address.toAddress(x_)).collect(java.util.stream.Collectors.toList()));
            Object aliases = ((com.gs.dmn.runtime.Context)other).get("aliases");
            result_.setAliases((List<String>)aliases);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toPerson(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Person.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("addresses")
    List<type.Address> getAddresses();

    @com.fasterxml.jackson.annotation.JsonGetter("aliases")
    List<String> getAliases();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.add("name", getName());
        context.add("addresses", getAddresses());
        context.add("aliases", getAliases());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person other = (Person) o;
        if (this.getAddresses() != null ? !this.getAddresses().equals(other.getAddresses()) : other.getAddresses() != null) return false;
        if (this.getAliases() != null ? !this.getAliases().equals(other.getAliases()) : other.getAliases() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAddresses() != null ? this.getAddresses().hashCode() : 0);
        result = 31 * result + (this.getAliases() != null ? this.getAliases().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("addresses=" + getAddresses());
        result_.append(", aliases=" + getAliases());
        result_.append(", name=" + getName());
        result_.append("}");
        return result_.toString();
    }
}
