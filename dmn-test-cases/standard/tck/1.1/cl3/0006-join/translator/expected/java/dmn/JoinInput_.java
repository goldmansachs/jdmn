
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Join"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class JoinInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<type.TDeptTable> deptTable;
    private List<type.TEmployeeTable> employeeTable;
    private String lastName;

    public JoinInput_() {
    }

    public JoinInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object deptTable = input_.get("DeptTable");
            setDeptTable((List<type.TDeptTable>)((java.util.List)deptTable).stream().map(x_ -> type.TDeptTable.toTDeptTable(x_)).collect(java.util.stream.Collectors.toList()));
            Object employeeTable = input_.get("EmployeeTable");
            setEmployeeTable((List<type.TEmployeeTable>)((java.util.List)employeeTable).stream().map(x_ -> type.TEmployeeTable.toTEmployeeTable(x_)).collect(java.util.stream.Collectors.toList()));
            Object lastName = input_.get("LastName");
            setLastName((String)lastName);
        }
    }

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
