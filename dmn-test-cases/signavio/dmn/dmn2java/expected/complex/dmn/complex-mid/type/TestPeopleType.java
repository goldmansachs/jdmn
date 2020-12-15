package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinitionInterface.ftl", "testPeopleType"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(as = type.TestPeopleTypeImpl.class)
public interface TestPeopleType extends com.gs.dmn.runtime.DMNType {
    static TestPeopleType toTestPeopleType(Object other) {
        if (other == null) {
            return null;
        } else if (TestPeopleType.class.isAssignableFrom(other.getClass())) {
            return (TestPeopleType)other;
        } else if (other instanceof com.gs.dmn.runtime.Context) {
            TestPeopleTypeImpl result_ = new TestPeopleTypeImpl();
            result_.setTestPersonType((List<type.TestPersonType>)((com.gs.dmn.runtime.Context)other).get("testPersonType", "TestPersonType"));
            return result_;
        } else if (other instanceof com.gs.dmn.runtime.DMNType) {
            return toTestPeopleType(((com.gs.dmn.runtime.DMNType)other).toContext());
        } else {
            throw new com.gs.dmn.runtime.DMNRuntimeException(String.format("Cannot convert '%s' to '%s'", other.getClass().getSimpleName(), TestPeopleType.class.getSimpleName()));
        }
    }

    @com.fasterxml.jackson.annotation.JsonGetter("TestPersonType")
    List<type.TestPersonType> getTestPersonType();

    default com.gs.dmn.runtime.Context toContext() {
        com.gs.dmn.runtime.Context context = new com.gs.dmn.runtime.Context();
        context.put("testPersonType", getTestPersonType());
        return context;
    }

    default boolean equalTo(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestPeopleType other = (TestPeopleType) o;
        if (this.getTestPersonType() != null ? !this.getTestPersonType().equals(other.getTestPersonType()) : other.getTestPersonType() != null) return false;

        return true;
    }

    default int hash() {
        int result = 0;
        result = 31 * result + (this.getTestPersonType() != null ? this.getTestPersonType().hashCode() : 0);
        return result;
    }

    default String asString() {
        StringBuilder result_ = new StringBuilder("{");
        result_.append("TestPersonType=" + getTestPersonType());
        result_.append("}");
        return result_.toString();
    }
}
