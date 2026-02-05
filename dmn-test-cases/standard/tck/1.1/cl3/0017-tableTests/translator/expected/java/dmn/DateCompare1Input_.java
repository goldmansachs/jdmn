
import java.util.*;

@javax.annotation.Generated(value = {"inputElement.ftl", "dateCompare1"})
@com.fasterxml.jackson.annotation.JsonPropertyOrder(alphabetic = true)
public class DateCompare1Input_ implements com.gs.dmn.runtime.ExecutableDRGElementInput {
    private java.time.LocalDate dateD;

    public DateCompare1Input_() {
    }

    public DateCompare1Input_(com.gs.dmn.runtime.Context input_) {
        if (input_ != null) {
            Object dateD = input_.get("dateD");
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
