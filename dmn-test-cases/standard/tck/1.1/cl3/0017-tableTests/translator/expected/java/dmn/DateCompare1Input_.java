
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dateCompare1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DateCompare1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate dateD;

    public DateCompare1Input_() {
    }

    public DateCompare1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dateD = input_.get("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#dateD");
            setDateD((java.time.LocalDate)dateD);
        }
    }

    public java.time.LocalDate getDateD() {
        return this.dateD;
    }

    public void setDateD(java.time.LocalDate dateD) {
        this.dateD = dateD;
    }

}
