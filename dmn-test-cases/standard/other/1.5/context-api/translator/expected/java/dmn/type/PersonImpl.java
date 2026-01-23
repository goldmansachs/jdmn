package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "Person"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class PersonImpl implements Person {
        private String name;
        private List<type.Address> addresses;
        private List<String> aliases;

    public PersonImpl() {
    }

    public PersonImpl(List<type.Address> addresses, List<String> aliases, String name) {
        this.setAddresses(addresses);
        this.setAliases(aliases);
        this.setName(name);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("addresses")
    public List<type.Address> getAddresses() {
        return this.addresses;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("addresses")
    public void setAddresses(List<type.Address> addresses) {
        this.addresses = addresses;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("aliases")
    public List<String> getAliases() {
        return this.aliases;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("aliases")
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
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
