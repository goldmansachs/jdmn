package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "tRequestedProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TRequestedProductImpl.class)
public interface TRequestedProduct extends com.gs.dmn.runtime.DMNType {
    static TRequestedProduct toTRequestedProduct(Object other) {
        if (other == null) {
            return null;
        } else if (TRequestedProduct.class.isAssignableFrom(other.getClass())) {
            return (TRequestedProduct)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TRequestedProductImpl result_ = new TRequestedProductImpl();
            Object productType = ((com.gs.dmn.runtime.Context)other).get("ProductType");
            result_.setProductType((String)productType);
            Object amount = ((com.gs.dmn.runtime.Context)other).get("Amount");
            result_.setAmount((java.lang.Number)amount);
            Object rate = ((com.gs.dmn.runtime.Context)other).get("Rate");
            result_.setRate((java.lang.Number)rate);
            Object term = ((com.gs.dmn.runtime.Context)other).get("Term");
            result_.setTerm((java.lang.Number)term);
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTRequestedProduct(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TRequestedProduct.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("ProductType")
    String getProductType();

    @com.fasterxml.jackson.annotation.JsonGetter("Amount")
    java.lang.Number getAmount();

    @com.fasterxml.jackson.annotation.JsonGetter("Rate")
    java.lang.Number getRate();

    @com.fasterxml.jackson.annotation.JsonGetter("Term")
    java.lang.Number getTerm();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("productType", getProductType());
        context.put("amount", getAmount());
        context.put("rate", getRate());
        context.put("term", getTerm());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TRequestedProduct other = (TRequestedProduct) o;
        if (this.getAmount() != null ? !this.getAmount().equals(other.getAmount()) : other.getAmount() != null) return false;
        if (this.getProductType() != null ? !this.getProductType().equals(other.getProductType()) : other.getProductType() != null) return false;
        if (this.getRate() != null ? !this.getRate().equals(other.getRate()) : other.getRate() != null) return false;
        if (this.getTerm() != null ? !this.getTerm().equals(other.getTerm()) : other.getTerm() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAmount() != null ? this.getAmount().hashCode() : 0);
        result = 31 * result + (this.getProductType() != null ? this.getProductType().hashCode() : 0);
        result = 31 * result + (this.getRate() != null ? this.getRate().hashCode() : 0);
        result = 31 * result + (this.getTerm() != null ? this.getTerm().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Amount=" + getAmount());
        result_.append(", ProductType=" + getProductType());
        result_.append(", Rate=" + getRate());
        result_.append(", Term=" + getTerm());
        result_.append("}");
        return result_.toString();
    }
}
