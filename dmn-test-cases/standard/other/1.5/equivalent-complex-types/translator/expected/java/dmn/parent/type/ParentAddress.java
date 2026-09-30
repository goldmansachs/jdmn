package parent.type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "ParentAddress"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = parent.type.ParentAddressImpl.class)
public interface ParentAddress extends com.gs.dmn.runtime.DMNType {
    static ParentAddress toParentAddress(Object other) {
        if (other == null) {
            return null;
        } else if (ParentAddress.class.isAssignableFrom(other.getClass())) {
            return (ParentAddress)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            ParentAddressImpl result_ = new ParentAddressImpl();
            Object num = ((com.gs.dmn.runtime.Context)other).get("num");
            result_.setNum((java.lang.Number)num);
            Object street = ((com.gs.dmn.runtime.Context)other).get("street");
            result_.setStreet((String)street);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toParentAddress(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), ParentAddress.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("num")
    java.lang.Number getNum();

    @com.fasterxml.jackson.annotation.JsonGetter("street")
    String getStreet();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.add("num", getNum());
        context.add("street", getStreet());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentAddress other = (ParentAddress) o;
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
