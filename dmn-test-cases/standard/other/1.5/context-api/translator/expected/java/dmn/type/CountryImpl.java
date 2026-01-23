package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "Country"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class CountryImpl implements Country {
        private String name;

    public CountryImpl() {
    }

    public CountryImpl(String name) {
        this.setName(name);
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
