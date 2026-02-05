
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> employees;

    public Decision2Input_() {
    }

    public Decision2Input_(com.gs.dmn.runtime.Context input_) {
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
