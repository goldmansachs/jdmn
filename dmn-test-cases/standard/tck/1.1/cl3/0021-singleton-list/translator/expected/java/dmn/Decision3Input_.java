
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "decision3"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class Decision3Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private List<String> employees;

    public Decision3Input_() {
    }

    public Decision3Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object employees = input_.get("http://www.trisotech.com/definitions/_f52ca843-504b-4c3b-a6bc-4d377bffef7a#Employees");
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
