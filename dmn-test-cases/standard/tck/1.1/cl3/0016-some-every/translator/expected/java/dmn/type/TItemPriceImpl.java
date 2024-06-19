package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tItemPrice"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TItemPriceImpl implements TItemPrice {
        private String itemName;
        private java.lang.Number price;

    public TItemPriceImpl() {
    }

    public TItemPriceImpl(String itemName, java.lang.Number price) {
        this.setItemName(itemName);
        this.setPrice(price);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("itemName")
    public String getItemName() {
        return this.itemName;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("itemName")
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("price")
    public java.lang.Number getPrice() {
        return this.price;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("price")
    public void setPrice(java.lang.Number price) {
        this.price = price;
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
