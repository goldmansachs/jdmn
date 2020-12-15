package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "testPersonType"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TestPersonTypeImpl.class)
public interface TestPersonType extends com.gs.dmn.runtime.DMNType {
    static TestPersonType toTestPersonType(Object other) {
        if (other == null) {
            return null;
        } else if (TestPersonType.class.isAssignableFrom(other.getClass())) {
            return (TestPersonType)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TestPersonTypeImpl result_ = new TestPersonTypeImpl();
            result_.setName((String)((com.gs.dmn.runtime.Context)other).get("name", "Name"));
            result_.setAge((java.math.BigDecimal)((com.gs.dmn.runtime.Context)other).get("age", "Age"));
            result_.setProperties((List<String>)((com.gs.dmn.runtime.Context)other).get("properties", "Properties"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTestPersonType(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TestPersonType.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("Name")
    String getName();

    @com.fasterxml.jackson.annotation.JsonGetter("Age")
    java.math.BigDecimal getAge();

    @com.fasterxml.jackson.annotation.JsonGetter("Properties")
    List<String> getProperties();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("name", getName());
        context.put("age", getAge());
        context.put("properties", getProperties());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestPersonType other = (TestPersonType) o;
        if (this.getAge() != null ? !this.getAge().equals(other.getAge()) : other.getAge() != null) return false;
        if (this.getName() != null ? !this.getName().equals(other.getName()) : other.getName() != null) return false;
        if (this.getProperties() != null ? !this.getProperties().equals(other.getProperties()) : other.getProperties() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getAge() != null ? this.getAge().hashCode() : 0);
        result = 31 * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = 31 * result + (this.getProperties() != null ? this.getProperties().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("Age=" + getAge());
        result_.append(", Name=" + getName());
        result_.append(", Properties=" + getProperties());
        result_.append("}");
        return result_.toString();
    }
}
