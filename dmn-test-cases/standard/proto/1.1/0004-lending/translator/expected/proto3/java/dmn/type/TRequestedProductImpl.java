package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tRequestedProduct"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TRequestedProductImpl implements TRequestedProduct {
        private String productType;
        private java.math.BigDecimal amount;
        private java.math.BigDecimal rate;
        private java.math.BigDecimal term;

    public TRequestedProductImpl() {
    }

    public TRequestedProductImpl(java.math.BigDecimal amount, String productType, java.math.BigDecimal rate, java.math.BigDecimal term) {
        this.setAmount(amount);
        this.setProductType(productType);
        this.setRate(rate);
        this.setTerm(term);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Amount")
    public java.math.BigDecimal getAmount() {
        return this.amount;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Amount")
    public void setAmount(java.math.BigDecimal amount) {
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
    public java.math.BigDecimal getRate() {
        return this.rate;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Rate")
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Term")
    public java.math.BigDecimal getTerm() {
        return this.term;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Term")
    public void setTerm(java.math.BigDecimal term) {
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
