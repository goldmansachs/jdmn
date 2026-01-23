package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "Address"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.AddressImpl.class)
public interface Address extends com.gs.dmn.runtime.DMNType {
    static Address toAddress(Object other) {
        if (other == null) {
            return null;
        } else if (Address.class.isAssignableFrom(other.getClass())) {
            return (Address)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            AddressImpl result_ = new AddressImpl();
            Object street = ((com.gs.dmn.runtime.Context)other).get("street");
            result_.setStreet((String)street);
            Object no = ((com.gs.dmn.runtime.Context)other).get("no");
            result_.setNo((java.lang.Number)no);
            Object country = ((com.gs.dmn.runtime.Context)other).get("country");
            result_.setCountry(type.Country.toCountry(country));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toAddress(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), Address.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("street")
    String getStreet();

    @com.fasterxml.jackson.annotation.JsonGetter("no")
    java.lang.Number getNo();

    @com.fasterxml.jackson.annotation.JsonGetter("country")
    type.Country getCountry();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("street", getStreet());
        context.put("no", getNo());
        context.put("country", getCountry());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address other = (Address) o;
        if (this.getCountry() != null ? !this.getCountry().equals(other.getCountry()) : other.getCountry() != null) return false;
        if (this.getNo() != null ? !this.getNo().equals(other.getNo()) : other.getNo() != null) return false;
        if (this.getStreet() != null ? !this.getStreet().equals(other.getStreet()) : other.getStreet() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getCountry() != null ? this.getCountry().hashCode() : 0);
        result = 31 * result + (this.getNo() != null ? this.getNo().hashCode() : 0);
        result = 31 * result + (this.getStreet() != null ? this.getStreet().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("country=" + getCountry());
        result_.append(", no=" + getNo());
        result_.append(", street=" + getStreet());
        result_.append("}");
        return result_.toString();
    }
}
