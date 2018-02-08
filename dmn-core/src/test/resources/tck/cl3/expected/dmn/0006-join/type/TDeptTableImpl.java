package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tDeptTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TDeptTableImpl implements TDeptTable {
        private java.math.BigDecimal number;
        private String name;
        private String manager;

    public TDeptTableImpl() {
    }

    public TDeptTableImpl(String manager, String name, java.math.BigDecimal number) {
        this.setManager(manager);
        this.setName(name);
        this.setNumber(number);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("manager")
    public String getManager() {
        return this.manager;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("manager")
    public void setManager(String manager) {
        this.manager = manager;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("name")
    public String getName() {
        return this.name;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("number")
    public java.math.BigDecimal getNumber() {
        return this.number;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("number")
    public void setNumber(java.math.BigDecimal number) {
        this.number = number;
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
