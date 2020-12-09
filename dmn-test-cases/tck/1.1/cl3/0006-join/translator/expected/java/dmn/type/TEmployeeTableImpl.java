package type;

import java.util.*;

@javax.annotation.Generated(value = {"itemDefinition.ftl", "tEmployeeTable"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class TEmployeeTableImpl implements TEmployeeTable {
        private String id;
        private String name;
        private java.math.BigDecimal deptNum;

    public TEmployeeTableImpl() {
    }

    public TEmployeeTableImpl(java.math.BigDecimal deptNum, String id, String name) {
        this.setDeptNum(deptNum);
        this.setId(id);
        this.setName(name);
    }

    @com.fasterxml.jackson.annotation.JsonGetter("deptNum")
    public java.math.BigDecimal getDeptNum() {
        return this.deptNum;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("deptNum")
    public void setDeptNum(java.math.BigDecimal deptNum) {
        this.deptNum = deptNum;
    }

    @com.fasterxml.jackson.annotation.JsonGetter("id")
    public String getId() {
        return this.id;
    }

    @com.fasterxml.jackson.annotation.JsonSetter("id")
    public void setId(String id) {
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
