package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tEmployees"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TEmployeesImpl implements TEmployees {
        private java.math.BigDecimal id;
        private java.math.BigDecimal dept;
        private String name;

    public TEmployeesImpl() {
    }

    public TEmployeesImpl(java.math.BigDecimal dept, java.math.BigDecimal id, String name) {
        this.setDept(dept);
        this.setId(id);
        this.setName(name);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("dept")
    public java.math.BigDecimal getDept() {
        return this.dept;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("dept")
    public void setDept(java.math.BigDecimal dept) {
        this.dept = dept;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("id")
    public java.math.BigDecimal getId() {
        return this.id;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("id")
    public void setId(java.math.BigDecimal id) {
        this.id = id;
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
