package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tItemPrice"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public interface TItemPrice extends com.gs.dmn.runtime.DMNType {
    static TItemPrice toTItemPrice(Object other) {
        if (other == null) {
            return null;
        } else if (TItemPrice.class.isAssignableFrom(other.getClass())) {
            return (TItemPrice)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TItemPriceImpl result_ = new TItemPriceImpl();
            result_.setItemName((String)((com.gs.dmn.runtime.Context)other).get("itemName"));
            result_.setPrice((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("price"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTItemPrice(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TItemPrice.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("itemName")
    String getItemName();

    @com.fasterxml.jackson.annotation.JsonGetter("price")
    java.math.BigDecimal getPrice();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("itemName", getItemName());
        context.put("price", getPrice());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TItemPrice other = (TItemPrice) o;
        if (this.getItemName() != null ? !this.getItemName().equals(other.getItemName()) : other.getItemName() != null) return false;
        if (this.getPrice() != null ? !this.getPrice().equals(other.getPrice()) : other.getPrice() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getItemName() != null ? this.getItemName().hashCode() : 0);
        result = 31 * result + (this.getPrice() != null ? this.getPrice().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("itemName=" + getItemName());
        result_.append(", price=" + getPrice());
        result_.append("}");
        return result_.toString();
    }
}
