package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "dependentDecision2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DependentDecision2Impl implements DependentDecision2 {
        private String dD2O1;
        private String dD2O2;

    public DependentDecision2Impl() {
    }

    public DependentDecision2Impl(String dD2O1, String dD2O2) {
        this.setDD2O1(dD2O1);
        this.setDD2O2(dD2O2);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DD2O1")
    public String getDD2O1() {
        return this.dD2O1;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DD2O1")
    public void setDD2O1(String dD2O1) {
        this.dD2O1 = dD2O1;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("DD2O2")
    public String getDD2O2() {
        return this.dD2O2;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("DD2O2")
    public void setDD2O2(String dD2O2) {
        this.dD2O2 = dD2O2;
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
