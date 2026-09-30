package parent.type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "ParentAddress"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ParentAddressImpl implements ParentAddress {
        private java.lang.Number num;
        private String street;

    public ParentAddressImpl() {
    }

    public ParentAddressImpl(java.lang.Number num, String street) {
        this.setNum(num);
        this.setStreet(street);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("num")
    public java.lang.Number getNum() {
        return this.num;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("num")
    public void setNum(java.lang.Number num) {
        this.num = num;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("street")
    public String getStreet() {
        return this.street;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("street")
    public void setStreet(String street) {
        this.street = street;
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
