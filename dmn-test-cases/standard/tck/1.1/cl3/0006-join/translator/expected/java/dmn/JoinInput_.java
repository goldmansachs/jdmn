
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Join"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class JoinInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
        private List<type.TDeptTable> deptTable;
        private List<type.TEmployeeTable> employeeTable;
        private String lastName;

    public List<type.TDeptTable> getDeptTable() {
        return this.deptTable;
    }

    public void setDeptTable(List<type.TDeptTable> deptTable) {
        this.deptTable = deptTable;
    }

    public List<type.TEmployeeTable> getEmployeeTable() {
        return this.employeeTable;
    }

    public void setEmployeeTable(List<type.TEmployeeTable> employeeTable) {
        this.employeeTable = employeeTable;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
