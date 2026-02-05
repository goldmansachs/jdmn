
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision4"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision4Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> employees;

    public Decision4Input_() {
    }

    public Decision4Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object employees = input_.get("Employees");
            setEmployees((List<String>)employees);
        }
    }

    public List<String> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }

}
