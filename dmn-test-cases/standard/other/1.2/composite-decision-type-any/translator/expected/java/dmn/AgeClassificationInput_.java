
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "ageClassification"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class AgeClassificationInput_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private type.Student student;

    public AgeClassificationInput_() {
    }

    public AgeClassificationInput_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object student = input_.get("https://kiegroup.org/dmn/_BD539849-95A1-4D71-BA89-8901271CEB07#student");
            setStudent(type.Student.toStudent(student));
        }
    }

    public type.Student getStudent() {
        return this.student;
    }

    public void setStudent(type.Student student) {
        this.student = student;
    }

}
