
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "ApplicationRiskScoreModel"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class ApplicationRiskScoreModelInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.lang.Number age;
    private String maritalStatus;
    private String employmentStatus;

    public ApplicationRiskScoreModelInput_() {
    }

    public ApplicationRiskScoreModelInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object age = input_.get("Age");
            setAge((java.lang.Number)age);
            Object maritalStatus = input_.get("MaritalStatus");
            setMaritalStatus((String)maritalStatus);
            Object employmentStatus = input_.get("EmploymentStatus");
            setEmploymentStatus((String)employmentStatus);
        }
    }

    public java.lang.Number getAge() {
        return this.age;
    }

    public void setAge(java.lang.Number age) {
        this.age = age;
    }

    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmploymentStatus() {
        return this.employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

}
