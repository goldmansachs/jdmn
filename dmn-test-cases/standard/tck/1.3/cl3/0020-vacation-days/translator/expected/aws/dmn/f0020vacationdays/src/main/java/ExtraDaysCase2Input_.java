
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "Extra days case 2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ExtraDaysCase2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number age;
    private java.lang.Number yearsOfService;

    public ExtraDaysCase2Input_() {
    }

    public ExtraDaysCase2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object age = input_.get("Age");
            setAge((java.lang.Number)age);
            Object yearsOfService = input_.get("Years of Service");
            setYearsOfService((java.lang.Number)yearsOfService);
        }
    }

    public java.lang.Number getAge() {
        return this.age;
    }

    public void setAge(java.lang.Number age) {
        this.age = age;
    }

    public java.lang.Number getYearsOfService() {
        return this.yearsOfService;
    }

    public void setYearsOfService(java.lang.Number yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

}
