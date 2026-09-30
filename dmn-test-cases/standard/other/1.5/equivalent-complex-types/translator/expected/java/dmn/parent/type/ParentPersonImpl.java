package parent.type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "ParentPerson"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ParentPersonImpl implements ParentPerson {
        private String name;
        private java.lang.Number age;
        private List<parent.type.ParentAddress> addresses;

    public ParentPersonImpl() {
    }

    public ParentPersonImpl(List<parent.type.ParentAddress> addresses, java.lang.Number age, String name) {
        this.setAddresses(addresses);
        this.setAge(age);
        this.setName(name);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("addresses")
    public List<parent.type.ParentAddress> getAddresses() {
        return this.addresses;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("addresses")
    public void setAddresses(List<parent.type.ParentAddress> addresses) {
        this.addresses = addresses;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("age")
    public java.lang.Number getAge() {
        return this.age;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("age")
    public void setAge(java.lang.Number age) {
        this.age = age;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    public String getName() {
        return this.name;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("name")
    public void setName(String name) {
        this.name = name;
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
