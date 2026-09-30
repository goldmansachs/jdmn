package child.type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "ChildAddress"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = child.type.ChildAddressImpl.class)
public interface ChildAddress extends com.gs.dmn.runtime.DMNType {
    static ChildAddress toChildAddress(Object other) {
        if (other == null) {
            return null;
        } else if (ChildAddress.class.isAssignableFrom(other.getClass())) {
            return (ChildAddress)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ChildAddressImpl result_ = new ChildAddressImpl();
            Object street = ((com.gs.dmn.runtime.Context)other).get("street");
            result_.setStreet((String)street);
            Object num = ((com.gs.dmn.runtime.Context)other).get("num");
            result_.setNum((java.lang.Number)num);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toChildAddress(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), ChildAddress.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("street")
    String getStreet();

    @com.fasterxml.jackson.annotation.JsonGetter("num")
    java.lang.Number getNum();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.add("street", getStreet());
        context.add("num", getNum());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChildAddress other = (ChildAddress) o;
        if (this.getNum() != null ? !this.getNum().equals(other.getNum()) : other.getNum() != null) return false;
        if (this.getStreet() != null ? !this.getStreet().equals(other.getStreet()) : other.getStreet() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getNum() != null ? this.getNum().hashCode() : 0);
        result = 31 * result + (this.getStreet() != null ? this.getStreet().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("num=" + getNum());
        result_.append(", street=" + getStreet());
        result_.append("}");
        return result_.toString();
    }
}
