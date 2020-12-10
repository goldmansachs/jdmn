package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "testPersonType"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TestPersonTypeImpl implements TestPersonType {
        private String name;
        private java.math.BigDecimal age;
        private List<String> properties;

    public TestPersonTypeImpl() {
    }

    public TestPersonTypeImpl(java.math.BigDecimal age, String name, List<String> properties) {
        this.setAge(age);
        this.setName(name);
        this.setProperties(properties);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Age")
    public java.math.BigDecimal getAge() {
        return this.age;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Age")
    public void setAge(java.math.BigDecimal age) {
        this.age = age;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Name")
    public String getName() {
        return this.name;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Name")
    public void setName(String name) {
        this.name = name;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Properties")
    public List<String> getProperties() {
        return this.properties;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("Properties")
    public void setProperties(List<String> properties) {
        this.properties = properties;
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
