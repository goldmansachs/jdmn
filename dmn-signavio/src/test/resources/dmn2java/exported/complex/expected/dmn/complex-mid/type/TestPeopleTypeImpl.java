package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "testPeopleType"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TestPeopleTypeImpl implements TestPeopleType {
        private List<type.TestPersonType> testPersonType;

    public TestPeopleTypeImpl() {
    }

    public TestPeopleTypeImpl(List<type.TestPersonType> testPersonType) {
        this.setTestPersonType(testPersonType);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("TestPersonType")
    public List<type.TestPersonType> getTestPersonType() {
        return this.testPersonType;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("TestPersonType")
    public void setTestPersonType(List<type.TestPersonType> testPersonType) {
        this.testPersonType = testPersonType;
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
