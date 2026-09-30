package child.type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "ChildPerson"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ChildPersonImpl implements ChildPerson {
        private java.lang.Number age;
        private String name;
        private List<child.type.ChildAddress> addresses;

    public ChildPersonImpl() {
    }

    public ChildPersonImpl(List<child.type.ChildAddress> addresses, java.lang.Number age, String name) {
        this.setAddresses(addresses);
        this.setAge(age);
        this.setName(name);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("addresses")
    public List<child.type.ChildAddress> getAddresses() {
        return this.addresses;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("addresses")
    public void setAddresses(List<child.type.ChildAddress> addresses) {
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
