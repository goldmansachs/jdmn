package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tA"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TA extends com.gs.dmn.runtime.DMNType {
    static TA toTA(Object other) {
        if (other == null) {
            return null;
        } else if (TA.class.isAssignableFrom(other.getClass())) {
            return (TA)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TAImpl result_ = new TAImpl();
            result_.setName((String)((com.gs.dmn.runtime.Context)other).get("name"));
            result_.setPrice((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("price"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTA(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TA.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("price")
    java.math.BigDecimal getPrice();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("name", getName());
        context.put("price", getPrice());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TA other = (TA) o;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;
        if (this.getPrice() != null ? !this.getPrice().equals(other.getPrice()) : other.getPrice() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = 31 * result + (this.getPrice() != null ? this.getPrice().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("name=" + getName());
        result_.append(", price=" + getPrice());
        result_.append("}");
        return result_.toString();
    }
}
