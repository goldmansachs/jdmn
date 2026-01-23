package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "Address"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AddressImpl implements Address {
        private String street;
        private java.lang.Number no;
        private type.Country country;

    public AddressImpl() {
    }

    public AddressImpl(type.Country country, java.lang.Number no, String street) {
        this.setCountry(country);
        this.setNo(no);
        this.setStreet(street);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("country")
    public type.Country getCountry() {
        return this.country;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("country")
    public void setCountry(type.Country country) {
        this.country = country;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("no")
    public java.lang.Number getNo() {
        return this.no;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("no")
    public void setNo(java.lang.Number no) {
        this.no = no;
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
