
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dateCompare2"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DateCompare2Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate dateD;
    private java.time.LocalDate dateE;

    public DateCompare2Input_() {
    }

    public DateCompare2Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dateD = input_.get("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#dateD");
            setDateD((java.time.LocalDate)dateD);
            Object dateE = input_.get("http://www.trisotech.com/definitions/_92a0c25f-707e-4fc8-ae2d-2ab51ebe6bb6#dateE");
            setDateE((java.time.LocalDate)dateE);
        }
    }

    public java.time.LocalDate getDateD() {
        return this.dateD;
    }

    public void setDateD(java.time.LocalDate dateD) {
        this.dateD = dateD;
    }

    public java.time.LocalDate getDateE() {
        return this.dateE;
    }

    public void setDateE(java.time.LocalDate dateE) {
        this.dateE = dateE;
    }

}
