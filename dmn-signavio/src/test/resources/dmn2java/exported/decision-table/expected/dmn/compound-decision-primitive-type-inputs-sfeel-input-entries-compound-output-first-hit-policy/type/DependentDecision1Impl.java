package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "dependentDecision1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision1Impl implements DependentDecision1 {
        private String dD1O1;
        private String dD1O2;

    public DependentDecision1Impl() {
    }

    public DependentDecision1Impl(String dD1O1, String dD1O2) {
        this.setDD1O1(dD1O1);
        this.setDD1O2(dD1O2);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DD1O1")
    public String getDD1O1() {
        return this.dD1O1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DD1O1")
    public void setDD1O1(String dD1O1) {
        this.dD1O1 = dD1O1;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DD1O2")
    public String getDD1O2() {
        return this.dD1O2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DD1O2")
    public void setDD1O2(String dD1O2) {
        this.dD1O2 = dD1O2;
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
