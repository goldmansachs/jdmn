package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "Country"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.CountryImpl.class)
public interface Country extends com.gs.dmn.runtime.DMNType {
    static Country toCountry(Object other) {
        if (other == null) {
            return null;
        } else if (Country.class.isAssignableFrom(other.getClass())) {
            return (Country)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            CountryImpl result_ = new CountryImpl();
            Object name = ((com.gs.dmn.runtime.Context)other).get("name");
            result_.setName((String)name);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toCountry(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Country.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("name", getName());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country other = (Country) o;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("name=" + getName());
        result_.append("}");
        return result_.toString();
    }
}
