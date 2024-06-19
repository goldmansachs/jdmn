package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tRequestedProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TRequestedProductImpl implements TRequestedProduct {
        private String productType;
        private java.lang.Number amount;
        private java.lang.Number rate;
        private java.lang.Number term;

    public TRequestedProductImpl() {
    }

    public TRequestedProductImpl(java.lang.Number amount, String productType, java.lang.Number rate, java.lang.Number term) {
        this.setAmount(amount);
        this.setProductType(productType);
        this.setRate(rate);
        this.setTerm(term);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Amount")
    public java.lang.Number getAmount() {
        return this.amount;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Amount")
    public void setAmount(java.lang.Number amount) {
        this.amount = amount;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("ProductType")
    public String getProductType() {
        return this.productType;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("ProductType")
    public void setProductType(String productType) {
        this.productType = productType;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Rate")
    public java.lang.Number getRate() {
        return this.rate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Rate")
    public void setRate(java.lang.Number rate) {
        this.rate = rate;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Term")
    public java.lang.Number getTerm() {
        return this.term;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Term")
    public void setTerm(java.lang.Number term) {
        this.term = term;
    }

    @Override
    public boolean equals(Object o) {
        return equalTo(o);
    }

    @Override
    public int hashCode() {
        return hash();
    }

    @Override
    public String toString() {
        return asString();
    }
}
